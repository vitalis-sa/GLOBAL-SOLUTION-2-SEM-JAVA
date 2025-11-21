package br.com.vitalis.dao;

import br.com.vitalis.model.TipoContato;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@ApplicationScoped
public class TipoContatoDao {

    @Inject
    private DataSource dataSource;

    /**
     * Lógica principal: Busca um TipoContato pelo nome. Se não encontrar,
     * cadastra um novo e o retorna.
     */
    public TipoContato findOrCreate(String nome) throws SQLException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do Tipo de Contato não pode ser nulo ou vazio");
        }

        Connection conexao = null;
        try {
            conexao = dataSource.getConnection();
            conexao.setAutoCommit(false); // Inicia transação

            // 1. Tenta buscar pelo nome
            PreparedStatement stmtBusca = conexao.prepareStatement(
                    "SELECT * FROM T_ATENDE_MAIS_TP_CTT WHERE NM_TP_CONTATO = ?");
            stmtBusca.setString(1, nome);

            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                // Encontrou. Retorna o que achou.
                TipoContato tipo = parseTipoContato(rs);
                rs.close();
                stmtBusca.close();
                conexao.commit(); // Não fizemos mudanças, mas fechamos a transação
                return tipo;
            }

            // 2. Não encontrou. Vamos cadastrar.
            rs.close();
            stmtBusca.close();

            TipoContato novoTipo = new TipoContato(null, nome);
            PreparedStatement stmtCadastra = conexao.prepareStatement(
                    "insert into T_ATENDE_MAIS_TP_CTT (ID_TP_CONTATO, NM_TP_CONTATO) " +
                            "values (SQ_ATENDE_MAIS_TP_CTT.nextval, ?)",
                    new String[]{"ID_TP_CONTATO"});

            stmtCadastra.setString(1, novoTipo.getNome());
            stmtCadastra.executeUpdate();

            ResultSet rsKeys = stmtCadastra.getGeneratedKeys();
            if (rsKeys.next()) {
                novoTipo.setId(rsKeys.getLong(1));
            }

            rsKeys.close();
            stmtCadastra.close();
            conexao.commit(); // Confirma o novo cadastro

            return novoTipo;

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

    private TipoContato parseTipoContato(ResultSet rs) throws SQLException {
        TipoContato tipo = new TipoContato();
        tipo.setId(rs.getLong("ID_TP_CONTATO"));
        tipo.setNome(rs.getString("NM_TP_CONTATO"));
        return tipo;
    }
}