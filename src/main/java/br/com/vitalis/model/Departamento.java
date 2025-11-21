package br.com.vitalis.model;

public class Departamento {
    private Long id;
    private String nome;

    public Departamento() {}
    
    public Departamento(Long id) { this.id = id; } // Construtor útil para DTOs

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}