package br.com.vitalis.dto.dtocontato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO para o JSON aninhado do Contato de Emergência
public class ContatoDto {

    @NotBlank(message = "O nome do Tipo de Contato é obrigatório (ex: Mãe, Pai)")
    private String nomeTipoContato;

    // DDI e DDD são opcionais (conforme seu DDL)
    private Integer ddi;
    private Integer ddd;

    @NotNull(message = "O número do contato é obrigatório")
    private Long numeroTelefone; // (Corresponde ao seu Contato.java)

    // Getters e Setters
    public String getNomeTipoContato() { return nomeTipoContato; }
    public void setNomeTipoContato(String nomeTipoContato) { this.nomeTipoContato = nomeTipoContato; }
    public Integer getDdi() { return ddi; }
    public void setDdi(Integer ddi) { this.ddi = ddi; }
    public Integer getDdd() { return ddd; }
    public void setDdd(Integer ddd) { this.ddd = ddd; }
    public Long getNumeroTelefone() { return numeroTelefone; }
    public void setNumeroTelefone(Long numeroTelefone) { this.numeroTelefone = numeroTelefone; }
}