package br.com.vitalis.resource;

import br.com.vitalis.dao.DepartamentoDao;
import br.com.vitalis.model.Departamento;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;

@Path("/departamentos")
@Produces(MediaType.APPLICATION_JSON)
public class DepartamentoResource {

    @Inject
    private DepartamentoDao departamentoDao;

    @GET
    public List<Departamento> listar() throws SQLException {
        return departamentoDao.listar();
    }
}   