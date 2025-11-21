package br.com.vitalis.dto.dtotelefone;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TelefoneDto {

    @NotNull(message = "DDI é obrigatório")
    @Min(1) @Max(999)
    private Integer ddi;

    @NotNull(message = "DDD é obrigatório")
    @Min(11) @Max(99)
    private Integer ddd;

    @NotNull(message = "Número é obrigatório")
    private Long numero;

    // Tipo removido: Tabela T_EQUILIBRIUM_TEL não tem coluna de tipo.
    // Status removido: O backend define como 'A' automaticamente.

    public Integer getDdi() { return ddi; }
    public void setDdi(Integer ddi) { this.ddi = ddi; }
    public Integer getDdd() { return ddd; }
    public void setDdd(Integer ddd) { this.ddd = ddd; }
    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }
}