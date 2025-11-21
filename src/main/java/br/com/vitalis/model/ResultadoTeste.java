package br.com.vitalis.model;

import com.fasterxml.jackson.annotation.JsonValue;

// Enum para o resultado do teste (SUCCESS ou FAILURE)
public enum ResultadoTeste {
    SUCCESS("success"),
    FAILURE("failure");

    private final String descricao;

    ResultadoTeste(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue // Ajuda o Jackson (API) a serializar/desserializar
    public String getDescricao() {
        return descricao;
    }

    // Método para converter a string do banco de dados de volta para Enum
    public static ResultadoTeste fromDescricao(String descricao) {
        if (descricao == null) return null;
        for (ResultadoTeste rt : values()) {
            if (rt.descricao.equalsIgnoreCase(descricao)) {
                return rt;
            }
        }
        // Se o banco salvar algo inesperado, padrão para failure
        return FAILURE;
    }
}