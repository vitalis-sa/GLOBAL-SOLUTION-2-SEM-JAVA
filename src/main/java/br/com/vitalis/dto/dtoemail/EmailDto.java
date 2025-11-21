package br.com.vitalis.dto.dtoemail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailDto {

    @NotBlank(message = "Endereço de e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 100)
    private String endereco;

    // Status removido: O backend define como 'A' automaticamente na criação.

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}