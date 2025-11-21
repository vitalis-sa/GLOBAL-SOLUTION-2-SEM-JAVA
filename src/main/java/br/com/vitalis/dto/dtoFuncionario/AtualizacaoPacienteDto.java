// package br.com.vitalis.dto.dtoFuncionario;

// import br.com.vitalis.dto.dtocontato.ContatoDto;
// import br.com.vitalis.dto.dtoemail.EmailDto; // <-- IMPORTADO
// import br.com.vitalis.dto.dtotelefone.TelefoneDto; // <-- IMPORTADO
// import jakarta.validation.Valid;
// import jakarta.validation.constraints.*;
// import java.time.LocalDate;
// import br.com.vitalis.model.Deficiencia;

// // Este DTO é usado para ATUALIZAR um paciente existente
// public class AtualizacaoPacienteDto {

//     @NotBlank(message = "Nome é obrigatório")
//     @Size(min = 2, max = 80)
//     private String nome;

//     @NotBlank(message = "CPF é obrigatório")
//     @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
//     private String cpf;

//     @NotNull(message = "Data de nascimento é obrigatória")
//     @Past(message = "Data de nascimento deve estar no passado")
//     private LocalDate dataNascimento;

//     @NotBlank(message = "Sexo biológico é obrigatório")
//     @Pattern(regexp = "[FM]", message = "Sexo biológico deve ser 'F' ou 'M'")
//     private String sexoBiologico;

//     @NotBlank(message = "Escolaridade é obrigatória")
//     @Size(max = 40)
//     private String escolaridade;

//     @NotNull(message = "Deficiência é obrigatória")
//     private Deficiencia deficiencia;

//     @NotBlank(message = "Acompanhante é obrigatório")
//     @Pattern(regexp = "[SN]", message = "Acompanhante deve ser 'S' ou 'N'")
//     private String dsAcompanhante;

//     // --- CAMPOS DE RELACIONAMENTO (OBRIGATÓRIOS) ---

//     @NotNull(message = "Telefone é obrigatório")
//     @Valid
//     private TelefoneDto telefone;

//     @NotNull(message = "Contato de emergência é obrigatório")
//     @Valid
//     private ContatoDto contato;

//     @NotNull(message = "E-mail é obrigatório")
//     @Valid
//     private EmailDto email;

//     // --- CAMPOS NÃO-EDITÁVEIS (Enviados de volta como estão) ---

//     // (O frontend não deve editar, mas deve enviar o valor atual)
//     private Integer nrPorcentagemFalta;
//     private Integer classificacao;


//     // --- Getters e Setters ---

//     public String getNome() { return nome; }
//     public void setNome(String nome) { this.nome = nome; }
//     public String getCpf() { return cpf; }
//     public void setCpf(String cpf) { this.cpf = cpf; }
//     public LocalDate getDataNascimento() { return dataNascimento; }
//     public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
//     public String getSexoBiologico() { return sexoBiologico; }
//     public void setSexoBiologico(String sexoBiologico) { this.sexoBiologico = sexoBiologico; }
//     public String getEscolaridade() { return escolaridade; }
//     public void setEscolaridade(String escolaridade) { this.escolaridade = escolaridade; }
//     public Deficiencia getDeficiencia() { return deficiencia; }
//     public void setDeficiencia(Deficiencia deficiencia) { this.deficiencia = deficiencia; }
//     public String getDsAcompanhante() { return dsAcompanhante; }
//     public void setDsAcompanhante(String dsAcompanhante) { this.dsAcompanhante = dsAcompanhante; }

//     public Integer getNrPorcentagemFalta() { return nrPorcentagemFalta; }
//     public void setNrPorcentagemFalta(Integer nrPorcentagemFalta) { this.nrPorcentagemFalta = nrPorcentagemFalta; }
//     public Integer getClassificacao() { return classificacao; }
//     public void setClassificacao(Integer classificacao) { this.classificacao = classificacao; }

//     public TelefoneDto getTelefone() { return telefone; }
//     public void setTelefone(TelefoneDto telefone) { this.telefone = telefone; }
//     public ContatoDto getContato() { return contato; }
//     public void setContato(ContatoDto contato) { this.contato = contato; }
//     public EmailDto getEmail() { return email; }
//     public void setEmail(EmailDto email) { this.email = email; }
// }