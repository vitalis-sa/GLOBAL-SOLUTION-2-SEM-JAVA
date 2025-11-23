package br.com.vitalis.dao;

import br.com.vitalis.exception.EntidadeNaoEncontradaException;
import br.com.vitalis.model.Departamento;
import br.com.vitalis.model.Email;
import br.com.vitalis.model.Funcionario;
import br.com.vitalis.model.Telefone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FuncionarioDao {

    @Inject
    private DataSource dataSource;

    private static final String SELECT_FUNCIONARIO_SQL =
            "SELECT f.*, " +
            "d.NM_DEPTO, " +
            "e.ID_EMAIL, e.DS_EMAIL, e.ST_EMAIL, " +
            "t.ID_TELEFONE, t.NR_DDI, t.NR_DDD, t.NR_TELEFONE, t.ST_TELEFONE " +
            "FROM T_EQUILIBRIUM_FUNCIONARIO f " +
            "INNER JOIN T_EQUILIBRIUM_DEPTO d ON f.ID_DEPTO = d.ID_DEPTO " +
            "LEFT JOIN T_EQUILIBRIUM_EMAIL e ON f.ID_FUNC = e.ID_FUNC " +
            "LEFT JOIN T_EQUILIBRIUM_TEL t ON f.ID_FUNC = t.ID_FUNC ";

    public void cadastrar(Funcionario funcionario) throws SQLException {
        Connection conexao = null;
        try {
            conexao = dataSource.getConnection();
            conexao.setAutoCommit(false);

            // 1. Insert Funcionario
            String sqlFunc = "INSERT INTO T_EQUILIBRIUM_FUNCIONARIO (" +
                    "ID_FUNC, ID_DEPTO, NM_FUNC, NR_CPF, NR_IDADE, FL_GENDER, " +
                    "DS_JOB_ROLE, NR_YEARS_AT_COMPANY, NR_WORK_HOURS_PER_WEEK, DS_REMOTE_WORK) " +
                    "VALUES (SQ_T_EQUILIBRIUM_FUNCIONARIO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Retornar chave gerada
            PreparedStatement stmtFunc = conexao.prepareStatement(sqlFunc, new String[]{"ID_FUNC"});
            
            if (funcionario.getDepartamento() != null) {
                stmtFunc.setLong(1, funcionario.getDepartamento().getId());
            } else {
                throw new SQLException("Departamento é obrigatório");
            }

            stmtFunc.setString(2, funcionario.getNome());
            stmtFunc.setString(3, funcionario.getCpf());
            stmtFunc.setInt(4, funcionario.getIdade());
            stmtFunc.setString(5, funcionario.getGenero());
            stmtFunc.setString(6, funcionario.getCargo());
            stmtFunc.setInt(7, funcionario.getAnosEmpresa());
            stmtFunc.setInt(8, funcionario.getHorasTrabalho());
            stmtFunc.setString(9, funcionario.getTrabalhoRemoto());

            stmtFunc.executeUpdate();
            
            ResultSet rs = stmtFunc.getGeneratedKeys();
            if (rs.next()) {
                funcionario.setId(rs.getLong(1));
            }
            stmtFunc.close();

            // 2. Insert Email (Default Status 'A')
            if (funcionario.getEmail() != null) {
                String sqlEmail = "INSERT INTO T_EQUILIBRIUM_EMAIL (ID_EMAIL, ID_FUNC, DS_EMAIL, ST_EMAIL) " +
                                  "VALUES (SQ_T_EQUILIBRIUM_EMAIL.nextval, ?, ?, 'A')";
                PreparedStatement stmtEmail = conexao.prepareStatement(sqlEmail);
                stmtEmail.setLong(1, funcionario.getId());
                stmtEmail.setString(2, funcionario.getEmail().getEndereco());
                stmtEmail.executeUpdate();
                stmtEmail.close();
            }

            // 3. Insert Telefone (Default Status 'A')
            if (funcionario.getTelefone() != null) {
                Telefone tel = funcionario.getTelefone();
                String sqlTel = "INSERT INTO T_EQUILIBRIUM_TEL (ID_TELEFONE, ID_FUNC, NR_DDI, NR_DDD, NR_TELEFONE, ST_TELEFONE) " +
                                "VALUES (SQ_T_EQUILIBRIUM_TEL.nextval, ?, ?, ?, ?, 'A')";
                PreparedStatement stmtTel = conexao.prepareStatement(sqlTel);
                stmtTel.setLong(1, funcionario.getId());
                stmtTel.setInt(2, tel.getDdi());
                stmtTel.setInt(3, tel.getDdd());
                stmtTel.setLong(4, tel.getNumero());
                stmtTel.executeUpdate();
                stmtTel.close();
            }

            conexao.commit();
        } catch (SQLException e) {
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        }
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            // Reutiliza a query com JOINs e filtra pelo CPF
            PreparedStatement stmt = conexao.prepareStatement(SELECT_FUNCIONARIO_SQL + "WHERE f.NR_CPF = ?");
            stmt.setString(1, cpf);
            
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado com o CPF informado.");
            }
            
            return parseFuncionarioCompleto(rs);
        }
    }


    public Funcionario buscar(long id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement(SELECT_FUNCIONARIO_SQL + "WHERE f.ID_FUNC = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) throw new EntidadeNaoEncontradaException("Funcionário não encontrado");
            return parseFuncionarioCompleto(rs);
        }
    }

    public List<Funcionario> listar() throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement(SELECT_FUNCIONARIO_SQL + " ORDER BY f.NM_FUNC");
            ResultSet rs = stmt.executeQuery();
            List<Funcionario> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseFuncionarioCompleto(rs));
            }
            return lista;
        }
    }

    public void atualizar(Funcionario funcionario) throws SQLException, EntidadeNaoEncontradaException {
        Connection conexao = null;
        try {
            conexao = dataSource.getConnection();
            conexao.setAutoCommit(false);

            // 1. Update Funcionario
            String sqlUpdate = "UPDATE T_EQUILIBRIUM_FUNCIONARIO SET " +
                    "ID_DEPTO = ?, NM_FUNC = ?, NR_CPF = ?, NR_IDADE = ?, FL_GENDER = ?, " +
                    "DS_JOB_ROLE = ?, NR_YEARS_AT_COMPANY = ?, NR_WORK_HOURS_PER_WEEK = ?, DS_REMOTE_WORK = ? " +
                    "WHERE ID_FUNC = ?";
            
            PreparedStatement stmtFunc = conexao.prepareStatement(sqlUpdate);
            stmtFunc.setLong(1, funcionario.getDepartamento().getId());
            stmtFunc.setString(2, funcionario.getNome());
            stmtFunc.setString(3, funcionario.getCpf());
            stmtFunc.setInt(4, funcionario.getIdade());
            stmtFunc.setString(5, funcionario.getGenero());
            stmtFunc.setString(6, funcionario.getCargo());
            stmtFunc.setInt(7, funcionario.getAnosEmpresa());
            stmtFunc.setInt(8, funcionario.getHorasTrabalho());
            stmtFunc.setString(9, funcionario.getTrabalhoRemoto());
            stmtFunc.setLong(10, funcionario.getId());

            int linhas = stmtFunc.executeUpdate();
            if (linhas == 0) throw new EntidadeNaoEncontradaException("Funcionário não encontrado");
            stmtFunc.close();

            // 2. Merge Email
            if (funcionario.getEmail() != null) {
                String sqlMerge = "MERGE INTO T_EQUILIBRIUM_EMAIL e " +
                        "USING (SELECT ? AS ID_FUNC FROM dual) f ON (e.ID_FUNC = f.ID_FUNC) " +
                        "WHEN MATCHED THEN UPDATE SET DS_EMAIL = ? " +
                        "WHEN NOT MATCHED THEN INSERT (ID_EMAIL, ID_FUNC, DS_EMAIL, ST_EMAIL) " +
                        "VALUES (SQ_T_EQUILIBRIUM_EMAIL.nextval, ?, ?, 'A')";
                PreparedStatement stmt = conexao.prepareStatement(sqlMerge);
                stmt.setLong(1, funcionario.getId());
                stmt.setString(2, funcionario.getEmail().getEndereco());
                stmt.setLong(3, funcionario.getId());
                stmt.setString(4, funcionario.getEmail().getEndereco());
                stmt.executeUpdate();
                stmt.close();
            }

            // 3. Merge Telefone
            if (funcionario.getTelefone() != null) {
                Telefone t = funcionario.getTelefone();
                String sqlMerge = "MERGE INTO T_EQUILIBRIUM_TEL x " +
                        "USING (SELECT ? AS ID_FUNC FROM dual) f ON (x.ID_FUNC = f.ID_FUNC) " +
                        "WHEN MATCHED THEN UPDATE SET NR_DDI = ?, NR_DDD = ?, NR_TELEFONE = ? " +
                        "WHEN NOT MATCHED THEN INSERT (ID_TELEFONE, ID_FUNC, NR_DDI, NR_DDD, NR_TELEFONE, ST_TELEFONE) " +
                        "VALUES (SQ_T_EQUILIBRIUM_TEL.nextval, ?, ?, ?, ?, 'A')";
                PreparedStatement stmt = conexao.prepareStatement(sqlMerge);
                stmt.setLong(1, funcionario.getId()); // USING
                stmt.setInt(2, t.getDdi());
                stmt.setInt(3, t.getDdd());
                stmt.setLong(4, t.getNumero()); // UPDATE
                stmt.setLong(5, funcionario.getId()); // INSERT FK
                stmt.setInt(6, t.getDdi());
                stmt.setInt(7, t.getDdd());
                stmt.setLong(8, t.getNumero()); // INSERT VALS
                stmt.executeUpdate();
                stmt.close();
            }

            conexao.commit();
        } catch (SQLException e) {
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        }
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        Connection conexao = null;
        try {
            conexao = dataSource.getConnection();
            conexao.setAutoCommit(false);

            // 1. Remover dependências (Email e Telefone)
            PreparedStatement stmtEmail = conexao.prepareStatement("DELETE FROM T_EQUILIBRIUM_EMAIL WHERE ID_FUNC = ?");
            stmtEmail.setLong(1, id);
            stmtEmail.executeUpdate();
            stmtEmail.close();

            PreparedStatement stmtTel = conexao.prepareStatement("DELETE FROM T_EQUILIBRIUM_TEL WHERE ID_FUNC = ?");
            stmtTel.setLong(1, id);
            stmtTel.executeUpdate();
            stmtTel.close();

            // 2. NOVO: Remover Recomendações da IA (Filha de Teste Situação)
            // Como a recomendação está ligada ao teste e não direto ao funcionário, precisamos de um subselect
            String deleteRecIA = "DELETE FROM T_EQUILIBRIUM_REC_IA WHERE ID_TESTE IN (SELECT ID_TESTE FROM T_EQUILIBRIUM_TESTE_SITUACAO WHERE ID_FUNC = ?)";
            PreparedStatement stmtRecIA = conexao.prepareStatement(deleteRecIA);
            stmtRecIA.setLong(1, id);
            stmtRecIA.executeUpdate();
            stmtRecIA.close();

            // 3. Remover Testes de Situação
            PreparedStatement stmtTest = conexao.prepareStatement("DELETE FROM T_EQUILIBRIUM_TESTE_SITUACAO WHERE ID_FUNC = ?");
            stmtTest.setLong(1, id);
            stmtTest.executeUpdate();
            stmtTest.close();

            // 4. Remover Funcionario
            PreparedStatement stmtFunc = conexao.prepareStatement("DELETE FROM T_EQUILIBRIUM_FUNCIONARIO WHERE ID_FUNC = ?");
            stmtFunc.setLong(1, id);
            int linhas = stmtFunc.executeUpdate();
            if (linhas == 0) throw new EntidadeNaoEncontradaException("Funcionário não encontrado");
            stmtFunc.close();

            conexao.commit();
        } catch (SQLException e) {
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        }
    }

    private Funcionario parseFuncionarioCompleto(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setId(rs.getLong("ID_FUNC"));
        f.setNome(rs.getString("NM_FUNC"));
        f.setCpf(rs.getString("NR_CPF"));
        f.setIdade(rs.getInt("NR_IDADE"));
        f.setGenero(rs.getString("FL_GENDER"));
        f.setCargo(rs.getString("DS_JOB_ROLE"));
        f.setAnosEmpresa(rs.getInt("NR_YEARS_AT_COMPANY"));
        f.setHorasTrabalho(rs.getInt("NR_WORK_HOURS_PER_WEEK"));
        f.setTrabalhoRemoto(rs.getString("DS_REMOTE_WORK"));

        Departamento d = new Departamento();
        d.setId(rs.getLong("ID_DEPTO"));
        d.setNome(rs.getString("NM_DEPTO"));
        f.setDepartamento(d);

        long idEmail = rs.getLong("ID_EMAIL");
        if (!rs.wasNull()) {
            Email e = new Email();
            e.setId(idEmail);
            e.setEndereco(rs.getString("DS_EMAIL"));
            e.setStatus(rs.getString("ST_EMAIL"));
            f.setEmail(e);
        }

        long idTel = rs.getLong("ID_TELEFONE");
        if (!rs.wasNull()) {
            Telefone t = new Telefone();
            t.setId(idTel);
            t.setDdi(rs.getInt("NR_DDI"));
            t.setDdd(rs.getInt("NR_DDD"));
            t.setNumero(rs.getLong("NR_TELEFONE"));
            t.setStatus(rs.getString("ST_TELEFONE"));
            f.setTelefone(t);
        }
        return f;
    }
}