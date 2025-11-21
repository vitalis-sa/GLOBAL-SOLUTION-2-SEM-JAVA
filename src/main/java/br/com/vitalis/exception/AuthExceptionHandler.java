package br.com.vitalis.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthExceptionHandler implements ExceptionMapper<EntidadeNaoEncontradaException> {

    @Override
    public Response toResponse(EntidadeNaoEncontradaException exception) {
        // Se a exceção for de login (CPF), retorna 401
        if (exception.getMessage().contains("CPF")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"CPF não encontrado\"}")
                    .build();
        } else {
            // --- CORREÇÃO ADICIONADA ---
            // Para qualquer outra exceção "Não Encontrado" (como no DELETE),
            // retorne um 404 Not Found.
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + exception.getMessage() + "\"}")
                    .build();
        }
    }
}