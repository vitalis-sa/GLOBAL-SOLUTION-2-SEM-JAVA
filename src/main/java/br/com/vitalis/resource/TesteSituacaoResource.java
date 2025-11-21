package br.com.vitalis.resource;

import br.com.vitalis.dao.RecomendacaoIADao;
import br.com.vitalis.dao.TesteSituacaoDao;
import br.com.vitalis.dto.dtoteste.CadastroTesteSituacaoDto;
import br.com.vitalis.dto.dtoteste.DetalhesTesteSituacaoDto;
import br.com.vitalis.dto.dtoai.BurnoutResponseDto;
import br.com.vitalis.dto.dtoai.RecomendacoesDto;
import br.com.vitalis.exception.EntidadeNaoEncontradaException;
import br.com.vitalis.model.Funcionario;
import br.com.vitalis.model.RecomendacaoIA;
import br.com.vitalis.model.TesteSituacao;
import br.com.vitalis.service.BurnoutApiClient;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/testes-situacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TesteSituacaoResource {

    @Inject
    private TesteSituacaoDao testeDao;
    
    @Inject
    private RecomendacaoIADao recomendacaoDao;

    @Inject
    private ModelMapper mapper;

    @Inject
    @RestClient
    private BurnoutApiClient burnoutApiClient;

    @POST
    public Response create(@Valid CadastroTesteSituacaoDto dto, @Context UriInfo uriInfo) throws SQLException, EntidadeNaoEncontradaException {
        // 1. Salvar Teste no Banco
        TesteSituacao teste = mapper.map(dto, TesteSituacao.class);
        Funcionario f = new Funcionario();
        f.setId(dto.getIdFuncionario());
        teste.setFuncionario(f);

        // O método cadastrar agora usa REQUIRES_NEW, então ele commita no banco imediatamente.
        testeDao.cadastrar(teste);

        // 2. Chamar API Python
        try {
            BurnoutResponseDto respostaIA = burnoutApiClient.calcularBurnout(dto.getIdFuncionario());
            
            if (respostaIA != null) {
                // A. Atualizar Score no Java (para garantir consistência)
                if (respostaIA.getBurnoutScore() != null) {
                    teste.setBurnoutScore(respostaIA.getBurnoutScore());
                    testeDao.atualizarScore(teste.getId(), respostaIA.getBurnoutScore());
                }

                // B. Salvar as recomendações
                if (respostaIA.getRecomendacoes() != null) {
                    salvarRecomendacoesNoBanco(teste.getId(), respostaIA.getRecomendacoes());
                }
            }
            
        } catch (WebApplicationException e) {
            // Diagnóstico: Lê o JSON de erro do Python se houver
            String corpoErro = "Sem detalhes";
            if (e.getResponse() != null && e.getResponse().hasEntity()) {
                corpoErro = e.getResponse().readEntity(String.class);
            }
            System.err.println("ALERTA: API Python retornou erro " + e.getResponse().getStatus());
            System.err.println("DETALHE DO PYTHON: " + corpoErro);
            
        } catch (Exception e) {
            System.err.println("ERRO DE CONEXÃO COM IA: " + e.getMessage());
            e.printStackTrace();
        }

        // 3. Buscar objeto atualizado para retorno
        TesteSituacao testeSalvo = testeDao.buscar(teste.getId());

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(testeSalvo.getId()));
        return Response.created(uriBuilder.build())
                .entity(mapper.map(testeSalvo, DetalhesTesteSituacaoDto.class))
                .build();
    }

    // --- Helpers ---

    private void salvarRecomendacoesNoBanco(Long idTeste, RecomendacoesDto dto) {
        try {
            RecomendacaoIA rec = new RecomendacaoIA();
            rec.setIdTeste(idTeste);
            rec.setTitulo(dto.getTitulo());
            rec.setIntroducao(limitString(dto.getIntroducao(), 500));

            List<String> conselhos = dto.getConselhosPraticos();
            if (conselhos != null) {
                if (conselhos.size() > 0) rec.setConselho1(limitString(conselhos.get(0), 300));
                if (conselhos.size() > 1) rec.setConselho2(limitString(conselhos.get(1), 300));
                if (conselhos.size() > 2) rec.setConselho3(limitString(conselhos.get(2), 300));
            }

            List<Map<String, String>> leituras = dto.getLeiturasSugeridas();
            if (leituras != null) {
                if (leituras.size() > 0) rec.setLeitura1(formatarLeitura(leituras.get(0)));
                if (leituras.size() > 1) rec.setLeitura2(formatarLeitura(leituras.get(1)));
            }

            recomendacaoDao.cadastrar(rec);
        } catch (SQLException e) {
            System.err.println("Erro ao salvar recomendação: " + e.getMessage());
        }
    }
    
    private String formatarLeitura(Map<String, String> leitura) {
        if (leitura == null) return null;
        String titulo = leitura.getOrDefault("titulo", "");
        String url = leitura.getOrDefault("url", "");
        return limitString(titulo + " - " + url, 500);
    }

    private String limitString(String value, int max) {
        if (value == null) return null;
        return value.length() > max ? value.substring(0, max) : value;
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") long id) throws SQLException {
        try {
            TesteSituacao t = testeDao.buscar(id);
            return Response.ok(mapper.map(t, DetalhesTesteSituacaoDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/funcionario/{idFunc}")
    public List<DetalhesTesteSituacaoDto> listarPorFuncionario(@PathParam("idFunc") long idFunc) throws SQLException {
        return testeDao.listarPorFuncionario(idFunc).stream()
                .map(t -> mapper.map(t, DetalhesTesteSituacaoDto.class))
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") long id) throws SQLException {
        try {
            testeDao.remover(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}