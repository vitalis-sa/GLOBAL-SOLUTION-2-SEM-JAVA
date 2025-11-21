package br.com.vitalis.dto.dtoai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class BurnoutResponseDto {
    private String nome;
    private String cargo;
    
    @JsonProperty("burnout_score")
    private Double burnoutScore;
    
    private RecomendacoesDto recomendacoes; // Objeto aninhado

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public Double getBurnoutScore() { return burnoutScore; }
    public void setBurnoutScore(Double burnoutScore) { this.burnoutScore = burnoutScore; }
    public RecomendacoesDto getRecomendacoes() { return recomendacoes; }
    public void setRecomendacoes(RecomendacoesDto recomendacoes) { this.recomendacoes = recomendacoes; }
}