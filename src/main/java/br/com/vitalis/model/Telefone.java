package br.com.vitalis.model;

public class Telefone {
    private Long id;          // ID_TELEFONE
    private Integer ddi;      // NR_DDI
    private Integer ddd;      // NR_DDD
    private Long numero;      // NR_TELEFONE
    private String status;    // ST_TELEFONE (A/I)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getDdi() { return ddi; }
    public void setDdi(Integer ddi) { this.ddi = ddi; }
    public Integer getDdd() { return ddd; }
    public void setDdd(Integer ddd) { this.ddd = ddd; }
    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}