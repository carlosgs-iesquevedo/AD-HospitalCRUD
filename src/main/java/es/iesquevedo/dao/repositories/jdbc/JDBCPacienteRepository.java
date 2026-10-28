package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.common.Constantes;
import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.mappers.PacienteRowMapper;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.dao.utils.DBConnection;
import es.iesquevedo.domain.error.DatabaseError;
import es.iesquevedo.domain.error.ForeignKeyError;
import es.iesquevedo.domain.error.PacienteDuplicadoError;
import es.iesquevedo.domain.error.PacienteNoEncontradoError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JDBCPacienteRepository implements PacienteRepository {

    private final DBConnection dbConnection;
    private final PacienteRowMapper pacienteRowMapper;

    @Inject
    public JDBCPacienteRepository(DBConnection dbConnection,
                                  PacienteRowMapper pacienteRowMapper) {
        this.dbConnection = dbConnection;
        this.pacienteRowMapper = pacienteRowMapper;
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.FIND_ALL_PACIENTES);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = pacienteRowMapper.mapRow(rs, rs.getRow());
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD");
            throw new DatabaseError(e.getMessage());
        }
        return pacientes;
    }

    public Optional<Paciente> findById(Long id) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.FIND_PACIENTE_BY_ID)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = pacienteRowMapper.mapRow(rs, rs.getRow());
                    return Optional.of(paciente);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            log.error("Se ha producido un error en la BD");
            throw new PacienteNoEncontradoError();
        }
    }

    @Override
    public Long add(Paciente paciente) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmtPaciente = conn.prepareStatement(SQLQueries.ADD_PACIENTE, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtUsuario = conn.prepareStatement(SQLQueries.ADD_USUARIO)
        ) {
            try {
                conn.setAutoCommit(false);
                pstmtPaciente.setString(1, paciente.getNombre());
                pstmtPaciente.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
                pstmtPaciente.setString(3, paciente.getTelefono());

                int filasAfectadas = pstmtPaciente.executeUpdate();

                if (filasAfectadas > 0) {
                    ResultSet idsGenerados = pstmtPaciente.getGeneratedKeys();
                    if (idsGenerados.next()) {
                        paciente.setId(idsGenerados.getLong(1));
                        pstmtUsuario.setString(1, paciente.getUsuario().getUsername());
                        pstmtUsuario.setString(2, paciente.getUsuario().getPassword());
                        pstmtUsuario.setLong(3, paciente.getId());
                        pstmtUsuario.executeUpdate();
                        conn.commit();
                    }
                }
                return paciente.getId();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                log.error(e.getMessage());
                throw new PacienteDuplicadoError();
            } catch (SQLException e) {
                conn.rollback();
                log.error(e.getMessage());
                throw new DatabaseError(Constantes.DATABASE_ERROR);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void update(Paciente paciente) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.UPDATE_PACIENTE)) {
            pstmt.setString(1, paciente.getNombre());
            pstmt.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
            pstmt.setString(3, paciente.getTelefono());
            pstmt.setLong(4, paciente.getId());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new DatabaseError("No se encontró el paciente a actualizar");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void delete(Long id, boolean tieneHistorias) {
        List<String> queries = new ArrayList<>();
        if (tieneHistorias) {
            queries.add(SQLQueries.DELETE_RECETAS_BY_PACIENTE);
            queries.add(SQLQueries.DELETE_HISTORIAS_BY_PACIENTE);
        }
        queries.add(SQLQueries.DELETE_CITAS_BY_PACIENTE);
        queries.add(SQLQueries.DELETE_FACTURAS_BY_PACIENTE);
        queries.add(SQLQueries.DELETE_USUARIO_BY_PACIENTE);
        queries.add(SQLQueries.DELETE_PACIENTE);

        try (Connection conn = dbConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                for (String sql : queries) {
                    borrarPorPaciente(conn, sql, id);
                }
                conn.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                log.error(e.getMessage());
                throw new ForeignKeyError();
            } catch (SQLException e) {
                conn.rollback();
                log.error(e.getMessage());
                throw new DatabaseError(Constantes.DATABASE_ERROR);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    private void borrarPorPaciente(Connection conn, String sql, Long id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}
