package br.com.vitalis.dao;

import br.com.vitalis.model.Departamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DepartamentoDao {

    @Inject
    private DataSource dataSource;

    public List<Departamento> listar() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        
        // Assumindo que a tabela é T_EQUILIBRIUM_DEPTO conforme seu FuncionarioDao
        String sql = "SELECT ID_DEPTO, NM_DEPTO FROM T_EQUILIBRIUM_DEPTO ORDER BY NM_DEPTO";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Departamento depto = new Departamento();
                depto.setId(rs.getLong("ID_DEPTO"));
                depto.setNome(rs.getString("NM_DEPTO"));
                departamentos.add(depto);
            }
        }
        return departamentos;
    }
} 
