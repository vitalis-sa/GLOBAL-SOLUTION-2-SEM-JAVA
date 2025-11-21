package br.com.vitalis.model;

public class Email {
    private Long id;        // ID_EMAIL
    private String endereco;// DS_EMAIL
    private String status;  // ST_EMAIL (A/I)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}