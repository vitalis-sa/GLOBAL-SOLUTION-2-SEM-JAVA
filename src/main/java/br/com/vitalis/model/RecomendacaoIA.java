package br.com.vitalis.model;

public class RecomendacaoIA {
    private Long id;
    private Long idTeste; // Relacionamento 1:1 (FK)
    
    private String titulo;
    private String introducao;
    
    private String conselho1;
    private String conselho2;
    private String conselho3;
    
    private String leitura1;
    private String leitura2;

    // Construtor vazio
    public RecomendacaoIA() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdTeste() { return idTeste; }
    public void setIdTeste(Long idTeste) { this.idTeste = idTeste; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIntroducao() { return introducao; }
    public void setIntroducao(String introducao) { this.introducao = introducao; }
    public String getConselho1() { return conselho1; }
    public void setConselho1(String conselho1) { this.conselho1 = conselho1; }
    public String getConselho2() { return conselho2; }
    public void setConselho2(String conselho2) { this.conselho2 = conselho2; }
    public String getConselho3() { return conselho3; }
    public void setConselho3(String conselho3) { this.conselho3 = conselho3; }
    public String getLeitura1() { return leitura1; }
    public void setLeitura1(String leitura1) { this.leitura1 = leitura1; }
    public String getLeitura2() { return leitura2; }
    public void setLeitura2(String leitura2) { this.leitura2 = leitura2; }
}