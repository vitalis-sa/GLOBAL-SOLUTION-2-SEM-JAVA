package br.com.vitalis.resource;

import br.com.vitalis.dao.FuncionarioDao;
import br.com.vitalis.dao.RecomendacaoIADao;
import br.com.vitalis.dao.TesteSituacaoDao;
import br.com.vitalis.dto.dtoFuncionario.CadastroFuncionarioDto;
import br.com.vitalis.dto.dtoFuncionario.DetalhesFuncionarioDto;
import br.com.vitalis.dto.dtoai.DetalhesRecomendacaoDto;
import br.com.vitalis.dto.dtoteste.DetalhesTesteSituacaoDto;
import br.com.vitalis.exception.EntidadeNaoEncontradaException;
import br.com.vitalis.model.Departamento;
import br.com.vitalis.model.Funcionario;
import br.com.vitalis.model.RecomendacaoIA;
import br.com.vitalis.model.TesteSituacao;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/funcionarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    private FuncionarioDao funcionarioDao;

    @Inject
    private ModelMapper mapper;

    @Inject
    private RecomendacaoIADao recomendacaoDao;

    @Inject
    private TesteSituacaoDao testeSituacaoDao;

@POST
    public Response create(@Valid CadastroFuncionarioDto dto, @Context UriInfo uriInfo) throws SQLException, EntidadeNaoEncontradaException {
        // 1. Prepara o objeto para salvar
        Funcionario funcionario = mapper.map(dto, Funcionario.class);
        funcionario.setDepartamento(new Departamento(dto.getIdDepartamento()));

        // 2. Salva no banco (aqui o objeto ganha o ID, ex: 2)
        funcionarioDao.cadastrar(funcionario);
        
        Funcionario funcionarioCompleto = funcionarioDao.buscar(funcionario.getId());
        // ----------------------------

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(funcionario.getId()));
        
        // 4. Retornamos o 'funcionarioCompleto' em vez do 'funcionario' parcial
        return Response.created(uriBuilder.build())
                .entity(mapper.map(funcionarioCompleto, DetalhesFuncionarioDto.class))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long id, @Valid CadastroFuncionarioDto dto) throws SQLException, EntidadeNaoEncontradaException {
        Funcionario funcionario = mapper.map(dto, Funcionario.class);
        funcionario.setId(id);
        funcionario.setDepartamento(new Departamento(dto.getIdDepartamento()));
        
        funcionarioDao.atualizar(funcionario);
        
        // Busca o atualizado para retornar completo (com nomes de depto, etc)
        Funcionario atualizado = funcionarioDao.buscar(id);
        return Response.ok(mapper.map(atualizado, DetalhesFuncionarioDto.class)).build();
    }

    @GET
    public List<DetalhesFuncionarioDto> listar() throws SQLException {
        return funcionarioDao.listar().stream()
                .map(f -> mapper.map(f, DetalhesFuncionarioDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}/recomendacao-atual")
    public Response getRecomendacaoAtual(@PathParam("id") long id) throws SQLException {
        try {
            funcionarioDao.buscar(id);
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        RecomendacaoIA rec = recomendacaoDao.buscarUltimaPorFuncionario(id);
        
        if (rec != null) {
            return Response.ok(mapper.map(rec, DetalhesRecomendacaoDto.class)).build();
        } else {
            // 204 No Content se o funcionário existe mas ainda não tem recomendações
            return Response.noContent().build();
        }
    }


    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") long id) throws SQLException {
        try {
            Funcionario f = funcionarioDao.buscar(id);
            return Response.ok(mapper.map(f, DetalhesFuncionarioDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{id}/testes-situacao")
    public Response getTestesDoFuncionario(@PathParam("id") long id) throws SQLException {
        // 1. Verifica se o funcionário existe (opcional, mas boa prática)
        try {
            funcionarioDao.buscar(id);
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        // 2. Busca a lista
        List<TesteSituacao> testes = testeSituacaoDao.listarPorFuncionario(id);

        return Response.ok(testes.stream().map(t -> mapper.map(t, DetalhesTesteSituacaoDto.class)).collect(Collectors.toList())).build();
    }

    @DELETE
    @Path("{id}")
    public Response remover(@PathParam("id") long id) throws SQLException {
        try {
            funcionarioDao.remover(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}