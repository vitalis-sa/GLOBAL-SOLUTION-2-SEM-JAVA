package br.com.vitalis.dto.dtoFuncionario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Ignora campos extras que a API Python possa mandar (como 'nome', 'id_paciente')
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrevisaoDto {

    // Mapeia o JSON "porcentagem_falta" para a variável "porcentagemFalta"
    @JsonProperty("porcentagem_falta")
    private Double porcentagemFalta;

    public Double getPorcentagemFalta() {
        return porcentagemFalta;
    }

    public void setPorcentagemFalta(Double porcentagemFalta) {
        this.porcentagemFalta = porcentagemFalta;
    }
}