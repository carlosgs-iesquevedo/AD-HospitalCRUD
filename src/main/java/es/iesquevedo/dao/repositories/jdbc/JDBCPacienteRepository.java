package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.dao.utils.DBConnection;
import es.iesquevedo.domain.error.DatabaseError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCPacienteRepository implements PacienteRepository {

    private final DBConnection dbConnection;

    @Inject
    public JDBCPacienteRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection connection= dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_PACIENTES);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = Paciente.builder()
                        .id(rs.getLong("paciente_id"))
                        .nombre(rs.getString("nombre"))
                        .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                        .telefono(rs.getString("telefono"))
                        .build();
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD");
            throw new DatabaseError(e.getMessage());
        }
        return pacientes;
    }
}
