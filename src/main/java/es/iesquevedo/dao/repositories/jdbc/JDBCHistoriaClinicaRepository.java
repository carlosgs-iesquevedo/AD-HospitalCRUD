package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.common.Constantes;
import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.mappers.HistoriaClinicaRowMapper;
import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.model.Receta;
import es.iesquevedo.dao.repositories.HistoriaClinicaRepository;
import es.iesquevedo.dao.utils.DBConnection;
import es.iesquevedo.domain.error.DatabaseError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCHistoriaClinicaRepository implements HistoriaClinicaRepository {
  private final DBConnection dbConnection;
  private final HistoriaClinicaRowMapper HistoriaClinicaRowMapper;

  @Inject
  public JDBCHistoriaClinicaRepository(DBConnection dbConnection,
                              HistoriaClinicaRowMapper historiaClinicaRowMapper) {
    this.dbConnection = dbConnection;
    this.HistoriaClinicaRowMapper = historiaClinicaRowMapper;
  }

  @Override
  public List<HistoriaClinica> findByPacienteId(Long pacienteId) {
    List<HistoriaClinica> historias = new ArrayList<>();
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQLQueries.FIND_HISTORIAS_BY_PACIENTE)) {
      pstmt.setLong(1, pacienteId);

      try (ResultSet rs = pstmt.executeQuery()) {
        HistoriaClinica historiaActual = null;
        while (rs.next()) {
          Long historiaId = rs.getLong("historia_id");
          // Si hemos cambiado de historia, creamos una nueva
          if (historiaActual == null || !historiaActual.getId().equals(historiaId)) {
            historiaActual = HistoriaClinicaRowMapper.mapRow(rs, rs.getRow());
            historiaActual.setRecetas(new ArrayList<>());
            historias.add(historiaActual);
          }
          //Añade la receta si existe
          Long recetaId = rs.getLong("receta_id");
          if (!rs.wasNull()) {
            //TODO RecetaRowMapper
            Receta receta = Receta.builder()
                .id(recetaId)
                .medicamento(rs.getString("medicamento"))
                .build();
            historiaActual.getRecetas().add(receta);
          }
        }
      } catch (SQLException e) {
        log.error(e.getMessage());
        throw new DatabaseError(e.getMessage());
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new DatabaseError(e.getMessage());
    }
    return historias;
  }

  @Override
  public boolean existsByPacienteId(Long pacienteId) {
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQLQueries.EXISTS_HISTORIA_BY_PACIENTE)) {
      pstmt.setLong(1, pacienteId);
      try (ResultSet rs = pstmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new DatabaseError(Constantes.DATABASE_ERROR);
    }
  }

  @Override
  public Long add(HistoriaClinica historiaClinica) {
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt1 = conn.prepareStatement(SQLQueries.ADD_HISTORIA, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement pstmt2 = conn.prepareStatement(SQLQueries.ADD_RECETA)
    ) {
      try {
        conn.setAutoCommit(false);
        pstmt1.setLong(1, historiaClinica.getPaciente().getId());
        pstmt1.setLong(2, historiaClinica.getMedico().getId());
        pstmt1.setString(3, historiaClinica.getDiagnostico());
        pstmt1.setDate(4, Date.valueOf(historiaClinica.getFechaAdmision()));

        int filasAfectadas = pstmt1.executeUpdate();

        if (filasAfectadas > 0) {
          try (ResultSet idsGenerados = pstmt1.getGeneratedKeys()) {
            if (idsGenerados.next()) {
              Long historiaId = idsGenerados.getLong(1);
              historiaClinica.setId(historiaId);

              for (Receta receta : historiaClinica.getRecetas()) {
                pstmt2.setLong(1, historiaId);
                pstmt2.setString(2, receta.getMedicamento());
                pstmt2.setInt(3, 0);
                pstmt2.executeUpdate();
              }
            }
          }
        }
        conn.commit();
        return historiaClinica.getId();
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
  public void delete(Long id) {
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt1 =
             conn.prepareStatement(SQLQueries.DELETE_RECETAS_BY_HISTORIA);
         PreparedStatement pstmt2 =
             conn.prepareStatement(SQLQueries.DELETE_HISTORIA)
    ) {
      try {
        conn.setAutoCommit(false);

        pstmt1.setLong(1, id);
        pstmt2.setLong(1, id);

        pstmt1.executeUpdate();
        pstmt2.executeUpdate();

        conn.commit();

      } catch (SQLException e) {

        // Si algo falla, deshacemos todos los DELETE anteriores
        conn.rollback();

        log.error(e.getMessage());
        throw new DatabaseError(Constantes.DATABASE_ERROR);

      } finally {
        // Restauramos el estado de la conexión
        conn.setAutoCommit(true);
      }

    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new DatabaseError(Constantes.DATABASE_ERROR);
    }
  }



  @Override
  public void update(HistoriaClinica historia) {
    //TODO actualizar recetas también
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQLQueries.UPDATE_HISTORIA)) {

      pstmt.setLong(1, historia.getPaciente().getId());
      pstmt.setLong(2, historia.getMedico().getId());
      pstmt.setString(3, historia.getDiagnostico());
      pstmt.setDate(4, Date.valueOf(historia.getFechaAdmision()));
      pstmt.setLong(5, historia.getId());

      int filasAfectadas = pstmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new DatabaseError("Error al actualizar historia clínica con Id: " + historia.getId());
      }

    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new DatabaseError(Constantes.DATABASE_ERROR);
    }
  }
}
