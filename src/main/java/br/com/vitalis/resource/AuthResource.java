package br.com.vitalis.resource;

import br.com.vitalis.dao.FuncionarioDao;
import br.com.vitalis.dto.dtoFuncionario.LoginDto;
import br.com.vitalis.dto.dtoFuncionario.DetalhesFuncionarioDto;
import br.com.vitalis.exception.EntidadeNaoEncontradaException;
import br.com.vitalis.model.Funcionario;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private FuncionarioDao funcionarioDao;

    @Inject
    private ModelMapper mapper;

    @POST
    public Response login(@Valid LoginDto dto) {
        try {
            // Busca o funcionário pelo CPF
            Funcionario funcionario = funcionarioDao.buscarPorCpf(dto.getCpf());

            // Retorna os detalhes do funcionário (sem expor dados sensíveis se houver)
            return Response.ok(mapper.map(funcionario, DetalhesFuncionarioDto.class)).build();

        } catch (EntidadeNaoEncontradaException e) {
            // Retorna 401 Unauthorized para login inválido (segurança: não dar dicas se existe ou não)
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"CPF não encontrado ou inválido.\"}")
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro ao processar login.\"}")
                    .build();
        }
    }
}