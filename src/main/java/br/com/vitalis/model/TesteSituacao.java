package br.com.vitalis.model;

public class TesteSituacao {

    private Long id; // ID_TESTE
    private Funcionario funcionario; // ID_FUNC (FK)

    // Inputs do questionário
    private Double jobSatisfaction;
    private Double stressLevel;
    private Double productivityScore;
    private Integer sleepHours;
    private Integer physicalActivity;
    private String mentalSupport; // "Yes" or "No"
    private Double managerSupportScore;
    private String therapyAccess; // "Yes" or "No"
    private Integer mentalHealthDaysOff;
    private String salaryRange;
    private Double workLifeScore;
    private Integer teamSize;
    private Double careerGrowthScore;

    // Output (Calculado depois, inicia nulo)
    private Double burnoutScore;

    public TesteSituacao() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
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
    public Double getBurnoutScore() { return burnoutScore; }
    public void setBurnoutScore(Double burnoutScore) { this.burnoutScore = burnoutScore; }
}