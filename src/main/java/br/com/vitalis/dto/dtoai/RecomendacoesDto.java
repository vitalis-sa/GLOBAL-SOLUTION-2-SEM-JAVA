package br.com.vitalis.dto.dtoai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class RecomendacoesDto {
    private String titulo;
    private String introducao;
    
    @JsonProperty("conselhos_praticos")
    private List<String> conselhosPraticos;
    
    @JsonProperty("leituras_sugeridas")
    private List<Map<String, String>> leiturasSugeridas; // Lista de Maps com "titulo" e "url"

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIntroducao() { return introducao; }
    public void setIntroducao(String introducao) { this.introducao = introducao; }
    public List<String> getConselhosPraticos() { return conselhosPraticos; }
    public void setConselhosPraticos(List<String> conselhosPraticos) { this.conselhosPraticos = conselhosPraticos; }
    public List<Map<String, String>> getLeiturasSugeridas() { return leiturasSugeridas; }
    public void setLeiturasSugeridas(List<Map<String, String>> leiturasSugeridas) { this.leiturasSugeridas = leiturasSugeridas; }
}