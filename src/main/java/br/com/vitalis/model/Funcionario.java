package br.com.vitalis.model;

public class Funcionario {
    private Long id;                 // ID_FUNC
    private Departamento departamento; // ID_DEPTO
    private String nome;             // NM_FUNC
    private String cpf;              // NR_CPF
    private Integer idade;           // NR_IDADE
    private String genero;           // FL_GENDER
    private String cargo;            // DS_JOB_ROLE
    private Integer anosEmpresa;     // NR_YEARS_AT_COMPANY
    private Integer horasTrabalho;   // NR_WORK_HOURS_PER_WEEK
    private String trabalhoRemoto;   // DS_REMOTE_WORK

    // Relacionamentos 1:1 (simplificados para o CRUD)
    private Email email;
    private Telefone telefone;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
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
    public Email getEmail() { return email; }
    public void setEmail(Email email) { this.email = email; }
    public Telefone getTelefone() { return telefone; }
    public void setTelefone(Telefone telefone) { this.telefone = telefone; }
}