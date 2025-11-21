package br.com.vitalis.dto.dtoFuncionario;

import br.com.vitalis.dto.dtoemail.EmailDto;
import br.com.vitalis.dto.dtotelefone.TelefoneDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class CadastroFuncionarioDto {

    @NotNull(message = "ID do Departamento é obrigatório")
    private Long idDepartamento;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 80)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    @Min(14) @Max(100)
    private Integer idade;

    @NotBlank
    @Pattern(regexp = "Female|Male|Non-binary|Prefer not to say", message = "Gênero inválido")
    private String genero;

    @NotBlank
    private String cargo;

    @NotNull
    @Min(0)
    private Integer anosEmpresa;

    @NotNull
    @Min(1) @Max(168)
    private Integer horasTrabalho;

    @NotBlank
    @Pattern(regexp = "Hybrid|No|Yes", message = "Trabalho remoto deve ser Hybrid, No ou Yes")
    private String trabalhoRemoto;

    @Valid
    private EmailDto email;

    @Valid
    private TelefoneDto telefone;

    // Getters e Setters
    public Long getIdDepartamento() { return idDepartamento; }
    public void setIdDepartamento(Long idDepartamento) { this.idDepartamento = idDepartamento; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public Integer getAnosEmpresa() { return anosEmpresa; }
    public void setAnosEmpresa(Integer anosEmpresa) { this.anosEmpresa = anosEmpresa; }
    public Integer getHorasTrabalho() { return horasTrabalho; }
    public void setHorasTrabalho(Integer horasTrabalho) { this.horasTrabalho = horasTrabalho; }
    public String getTrabalhoRemoto() { return trabalhoRemoto; }
    public void setTrabalhoRemoto(String trabalhoRemoto) { this.trabalhoRemoto = trabalhoRemoto; }
    public EmailDto getEmail() { return email; }
    public void setEmail(EmailDto email) { this.email = email; }
    public TelefoneDto getTelefone() { return telefone; }
    public void setTelefone(TelefoneDto telefone) { this.telefone = telefone; }
}