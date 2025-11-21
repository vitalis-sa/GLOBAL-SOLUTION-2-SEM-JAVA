package br.com.vitalis.dto.dtoai;

import java.util.List;
import java.util.Map;

public class MelhoriaResponseDto {
    private String nome;
    private String cargo;
    private Double burnoutScore;
    private List<Map<String, Object>> topFeatures; // Lista de {nome, importancia}
    private Map<String, Object> recomendacoesRh; // JSON da IA

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Double getBurnoutScore() { return burnoutScore; }
    public void setBurnoutScore(Double burnoutScore) { this.burnoutScore = burnoutScore; }

    public List<Map<String, Object>> getTopFeatures() { return topFeatures; }
    public void setTopFeatures(List<Map<String, Object>> topFeatures) { this.topFeatures = topFeatures; }

    public Map<String, Object> getRecomendacoesRh() { return recomendacoesRh; }
    public void setRecomendacoesRh(Map<String, Object> recomendacoesRh) { this.recomendacoesRh = recomendacoesRh; }
}