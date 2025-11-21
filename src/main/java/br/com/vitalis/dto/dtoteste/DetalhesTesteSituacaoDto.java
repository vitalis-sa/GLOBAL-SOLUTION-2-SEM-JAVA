package br.com.vitalis.dto.dtoteste;

public class DetalhesTesteSituacaoDto extends CadastroTesteSituacaoDto {
    private Long id;
    private Double burnoutScore; // Aqui retornamos o valor (ou null se ainda não calculado)
    private String nomeFuncionario; // Útil para exibição

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getBurnoutScore() { return burnoutScore; }
    public void setBurnoutScore(Double burnoutScore) { this.burnoutScore = burnoutScore; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
}