package br.com.vitalis.dto.dtoFuncionario;

import jakarta.validation.constraints.NotBlank;

// DTO para receber o JSON do login
public class LoginDto {

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}