package br.com.vitalis.model;

public class TipoContato {
    private Long id;
    private String nome;

    public TipoContato() {}

    public TipoContato(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "TipoContato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
