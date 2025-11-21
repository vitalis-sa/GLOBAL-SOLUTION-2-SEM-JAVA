package br.com.vitalis.resource;

import br.com.vitalis.dao.FuncionarioDao;
import br.com.vitalis.dto.dtoFuncionario.DetalhesFuncionarioDto;
import br.com.vitalis.dto.dtoFuncionario.LoginDto;
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

@Path("/login") // Endpoint de autenticação
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private FuncionarioDao funcionarioDao;

    @Inject
    private ModelMapper mapper;

   // @POST
    //public Response login(@Valid LoginDto dto) throws SQLException, EntidadeNaoEncontradaException {
        // Usa o novo método para buscar o Funcionario pelo CPF
        //Funcionario funcionario = funcionarioDao.buscarPorCpf(dto.getCpf());

        // Se encontrar, retorna 200 OK com os dados do paciente
        // O ExceptionHandler cuidará do caso de "não encontrado" (401)
        //return Response.ok(mapper.map(funcionario, DetalhesFuncionarioDto.class)).build();
    //}
}