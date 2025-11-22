package br.com.vitalis.dao;

import br.com.vitalis.model.RecomendacaoIA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class RecomendacaoIADao {

    @Inject
    private DataSource dataSource;

    public void cadastrar(RecomendacaoIA rec) throws SQLException {
        String sql = "INSERT INTO T_EQUILIBRIUM_REC_IA (" +
                     "ID_RECOMENDACAO, ID_TESTE, DS_TITULO, DS_INTRODUCAO, " +
                     "DS_CONSELHO_1, DS_CONSELHO_2, DS_CONSELHO_3, " +
                     "DS_LEITURA_1, DS_LEITURA_2) " +
                     "VALUES (SQ_T_EQUILIBRIUM_REC_IA.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setLong(1, rec.getIdTeste());
            stmt.setString(2, rec.getTitulo());
            stmt.setString(3, rec.getIntroducao());
            stmt.setString(4, rec.getConselho1());
            stmt.setString(5, rec.getConselho2());
            stmt.setString(6, rec.getConselho3());
            stmt.setString(7, rec.getLeitura1());
            stmt.setString(8, rec.getLeitura2());

            stmt.executeUpdate();
        }
    }

    // --- NOVO MÉTODO: Buscar a última recomendação do funcionário ---
    public RecomendacaoIA buscarUltimaPorFuncionario(long idFuncionario) throws SQLException {
        // Faz JOIN com a tabela de TESTE para filtrar pelo ID_FUNC
        // Ordena pelo ID_RECOMENDACAO (ou ID_TESTE) decrescente para pegar a mais recente
        String sql = "SELECT r.* " +
                     "FROM T_EQUILIBRIUM_REC_IA r " +
                     "INNER JOIN T_EQUILIBRIUM_TESTE_SITUACAO t ON r.ID_TESTE = t.ID_TESTE " +
                     "WHERE t.ID_FUNC = ? " +
                     "ORDER BY r.ID_RECOMENDACAO DESC"; // Pega o último

        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setLong(1, idFuncionario);
            
            // Como ordenamos DESC, o primeiro resultado é o mais recente
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return parseRecomendacao(rs);
            }
        }
        return null; // Retorna null se não tiver recomendação
    }

    private RecomendacaoIA parseRecomendacao(ResultSet rs) throws SQLException {
        RecomendacaoIA rec = new RecomendacaoIA();
        rec.setId(rs.getLong("ID_RECOMENDACAO"));
        rec.setIdTeste(rs.getLong("ID_TESTE"));
        rec.setTitulo(rs.getString("DS_TITULO"));
        rec.setIntroducao(rs.getString("DS_INTRODUCAO"));
        rec.setConselho1(rs.getString("DS_CONSELHO_1"));
        rec.setConselho2(rs.getString("DS_CONSELHO_2"));
        rec.setConselho3(rs.getString("DS_CONSELHO_3"));
        rec.setLeitura1(rs.getString("DS_LEITURA_1"));
        rec.setLeitura2(rs.getString("DS_LEITURA_2"));
        return rec;
    }
}