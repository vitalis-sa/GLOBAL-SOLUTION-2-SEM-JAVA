package br.com.vitalis.dto.dtoFuncionario;
// Imagine uma classe similar ao Cadastro, mas com "String nomeDepartamento" e "Long id"
// Para brevidade, usaremos o ModelMapper para converter.
public class DetalhesFuncionarioDto extends CadastroFuncionarioDto {
    private Long id;
    private String nomeDepartamento;
    // getters setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeDepartamento() { return nomeDepartamento; }
    public void setNomeDepartamento(String nomeDepartamento) { this.nomeDepartamento = nomeDepartamento; }
}