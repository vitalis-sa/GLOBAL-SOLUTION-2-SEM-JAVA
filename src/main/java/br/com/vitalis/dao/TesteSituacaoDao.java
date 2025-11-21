package br.com.vitalis.dao;

import br.com.vitalis.exception.EntidadeNaoEncontradaException;
import br.com.vitalis.model.Funcionario;
import br.com.vitalis.model.TesteSituacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional; // IMPORTANTE

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TesteSituacaoDao {

    @Inject
    private DataSource dataSource;

    private static final String SELECT_BASE =
            "SELECT t.*, f.NM_FUNC " +
            "FROM T_EQUILIBRIUM_TESTE_SITUACAO t " +
            "INNER JOIN T_EQUILIBRIUM_FUNCIONARIO f ON t.ID_FUNC = f.ID_FUNC ";

    // --- A CORREÇÃO ESTÁ AQUI ---
    // O REQUIRES_NEW cria uma transação nova e isolada.
    // Assim que esse método termina, o Quarkus faz o COMMIT no banco.
    // Isso garante que o Python vai encontrar o registro quando for chamado logo em seguida.
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void cadastrar(TesteSituacao teste) throws SQLException {
        // Com @Transactional, não precisamos gerenciar conexao.setAutoCommit
        try (Connection conexao = dataSource.getConnection()) {
            
            String sql = "INSERT INTO T_EQUILIBRIUM_TESTE_SITUACAO (" +
                    "ID_TESTE, ID_FUNC, NR_JOB_SATISFACTION, NR_STRESS_LEVEL, NR_PRODUCTIVITY_SCORE, " +
                    "NR_SLEEP_HOURS, NR_PHYSICAL_ACTIVITY, DS_MENTAL_SUPPORT, NR_MANAGER_SUPPORT_SCORE, " +
                    "DS_THERAPY_ACCESS, NR_MENTAL_HEALTH_DAYS_OFF, NR_SALARY_RANGE, NR_WORK_LIFE_SCORE, " +
                    "NR_TEAM_SIZE, NR_CAREER_GROWTH_SCORE, NR_BURNOUT_SCORE) " +
                    "VALUES (SQ_T_EQUILIBRIUM_TESTE_SITUACAO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";
            
            // Pedimos para retornar o ID gerado
            PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_TESTE"});
            
            stmt.setLong(1, teste.getFuncionario().getId());
            stmt.setDouble(2, teste.getJobSatisfaction());
            stmt.setDouble(3, teste.getStressLevel());
            stmt.setDouble(4, teste.getProductivityScore());
            stmt.setInt(5, teste.getSleepHours());
            stmt.setInt(6, teste.getPhysicalActivity());
            stmt.setString(7, teste.getMentalSupport());
            stmt.setDouble(8, teste.getManagerSupportScore());
            stmt.setString(9, teste.getTherapyAccess());
            stmt.setInt(10, teste.getMentalHealthDaysOff());
            stmt.setString(11, teste.getSalaryRange());
            stmt.setDouble(12, teste.getWorkLifeScore());
            stmt.setInt(13, teste.getTeamSize());
            stmt.setDouble(14, teste.getCareerGrowthScore());

            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                teste.setId(rs.getLong(1));
            }
        }
    }

    // Método para salvar o score que volta da IA (caso o Python não tenha conseguido salvar)
    public void atualizarScore(long idTeste, double score) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            String sql = "UPDATE T_EQUILIBRIUM_TESTE_SITUACAO SET NR_BURNOUT_SCORE = ? WHERE ID_TESTE = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, score);
            stmt.setLong(2, idTeste);
            stmt.executeUpdate();
        }
    }

    public TesteSituacao buscar(long id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement(SELECT_BASE + "WHERE t.ID_TESTE = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) throw new EntidadeNaoEncontradaException("Teste de situação não encontrado");
            return parseTeste(rs);
        }
    }

    public List<TesteSituacao> listarPorFuncionario(long idFuncionario) throws SQLException {
        List<TesteSituacao> lista = new ArrayList<>();
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement(SELECT_BASE + "WHERE t.ID_FUNC = ? ORDER BY t.ID_TESTE DESC");
            stmt.setLong(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(parseTeste(rs));
            }
        }
        return lista;
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_EQUILIBRIUM_TESTE_SITUACAO WHERE ID_TESTE = ?");
            stmt.setLong(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) throw new EntidadeNaoEncontradaException("Teste não encontrado para remoção");
        }
    }

    private TesteSituacao parseTeste(ResultSet rs) throws SQLException {
        TesteSituacao t = new TesteSituacao();
        t.setId(rs.getLong("ID_TESTE"));
        
        Funcionario f = new Funcionario();
        f.setId(rs.getLong("ID_FUNC"));
        f.setNome(rs.getString("NM_FUNC")); 
        t.setFuncionario(f);

        t.setJobSatisfaction(rs.getDouble("NR_JOB_SATISFACTION"));
        t.setStressLevel(rs.getDouble("NR_STRESS_LEVEL"));
        t.setProductivityScore(rs.getDouble("NR_PRODUCTIVITY_SCORE"));
        t.setSleepHours(rs.getInt("NR_SLEEP_HOURS"));
        t.setPhysicalActivity(rs.getInt("NR_PHYSICAL_ACTIVITY"));
        t.setMentalSupport(rs.getString("DS_MENTAL_SUPPORT"));
        t.setManagerSupportScore(rs.getDouble("NR_MANAGER_SUPPORT_SCORE"));
        t.setTherapyAccess(rs.getString("DS_THERAPY_ACCESS"));
        t.setMentalHealthDaysOff(rs.getInt("NR_MENTAL_HEALTH_DAYS_OFF"));
        t.setSalaryRange(rs.getString("NR_SALARY_RANGE"));
        t.setWorkLifeScore(rs.getDouble("NR_WORK_LIFE_SCORE"));
        t.setTeamSize(rs.getInt("NR_TEAM_SIZE"));
        t.setCareerGrowthScore(rs.getDouble("NR_CAREER_GROWTH_SCORE"));
        
        double burnout = rs.getDouble("NR_BURNOUT_SCORE");
        if (!rs.wasNull()) {
            t.setBurnoutScore(burnout);
        } else {
            t.setBurnoutScore(null);
        }
        
        return t;
    }
}