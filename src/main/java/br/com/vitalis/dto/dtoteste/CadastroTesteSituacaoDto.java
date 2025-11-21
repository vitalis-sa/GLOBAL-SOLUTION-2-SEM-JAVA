package br.com.vitalis.dto.dtoteste;

import jakarta.validation.constraints.*;

public class CadastroTesteSituacaoDto {

    @NotNull(message = "ID do funcionário é obrigatório")
    private Long idFuncionario;

    @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
    private Double jobSatisfaction;

    @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
    private Double stressLevel;

    @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
    private Double productivityScore;

    @NotNull @Min(0) @Max(24)
    private Integer sleepHours;

    @NotNull @Min(0)
    private Integer physicalActivity;

    @NotBlank @Pattern(regexp = "Yes|No", message = "Mental Support deve ser Yes ou No")
    private String mentalSupport;

    @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
    private Double managerSupportScore;

    @NotBlank @Pattern(regexp = "Yes|No", message = "Therapy Access deve ser Yes ou No")
    private String therapyAccess;

    @NotNull @Min(0)
    private Integer mentalHealthDaysOff;

    @NotBlank
    private String salaryRange;

    @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
    private Double workLifeScore;

    @NotNull @Min(1)
    private Integer teamSize;

    @NotNull @DecimalMin("0.0") @DecimalMax("10.0")
    private Double careerGrowthScore;

    // Getters e Setters (Omitidos para brevidade, mas são necessários)
    public Long getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Long idFuncionario) { this.idFuncionario = idFuncionario; }
    public Double getJobSatisfaction() { return jobSatisfaction; }
    public void setJobSatisfaction(Double jobSatisfaction) { this.jobSatisfaction = jobSatisfaction; }
    public Double getStressLevel() { return stressLevel; }
    public void setStressLevel(Double stressLevel) { this.stressLevel = stressLevel; }
    public Double getProductivityScore() { return productivityScore; }
    public void setProductivityScore(Double productivityScore) { this.productivityScore = productivityScore; }
    public Integer getSleepHours() { return sleepHours; }
    public void setSleepHours(Integer sleepHours) { this.sleepHours = sleepHours; }
    public Integer getPhysicalActivity() { return physicalActivity; }
    public void setPhysicalActivity(Integer physicalActivity) { this.physicalActivity = physicalActivity; }
    public String getMentalSupport() { return mentalSupport; }
    public void setMentalSupport(String mentalSupport) { this.mentalSupport = mentalSupport; }
    public Double getManagerSupportScore() { return managerSupportScore; }
    public void setManagerSupportScore(Double managerSupportScore) { this.managerSupportScore = managerSupportScore; }
    public String getTherapyAccess() { return therapyAccess; }
    public void setTherapyAccess(String therapyAccess) { this.therapyAccess = therapyAccess; }
    public Integer getMentalHealthDaysOff() { return mentalHealthDaysOff; }
    public void setMentalHealthDaysOff(Integer mentalHealthDaysOff) { this.mentalHealthDaysOff = mentalHealthDaysOff; }
    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }
    public Double getWorkLifeScore() { return workLifeScore; }
    public void setWorkLifeScore(Double workLifeScore) { this.workLifeScore = workLifeScore; }
    public Integer getTeamSize() { return teamSize; }
    public void setTeamSize(Integer teamSize) { this.teamSize = teamSize; }
    public Double getCareerGrowthScore() { return careerGrowthScore; }
    public void setCareerGrowthScore(Double careerGrowthScore) { this.careerGrowthScore = careerGrowthScore; }
}