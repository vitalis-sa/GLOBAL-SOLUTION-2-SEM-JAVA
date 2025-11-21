package br.com.vitalis.dao;

import br.com.vitalis.model.RecomendacaoIA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}