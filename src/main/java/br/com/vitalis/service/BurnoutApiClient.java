package br.com.vitalis.service;

import br.com.vitalis.dto.dtoai.BurnoutResponseDto;
import br.com.vitalis.dto.dtoai.MelhoriaResponseDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://equilibrium-pythonandai-906663117168.us-west1.run.app/")
public interface BurnoutApiClient {

    // Endpoint que calcula o burnout e retorna conselhos para o funcionário
    // GET /api/ml/burnout/{id_func}
    @GET
    @Path("/api/ml/burnout/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    BurnoutResponseDto calcularBurnout(@PathParam("id") Long idFuncionario);

    // Endpoint que analisa melhorias para o RH
    // GET /api/ml/melhoria/{id_func}
    @GET
    @Path("/api/ml/melhoria/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    MelhoriaResponseDto analisarMelhorias(@PathParam("id") Long idFuncionario);
}