package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.mappers.MedicoRowMapper;
import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.dao.repositories.MedicoRepository;
import es.iesquevedo.dao.utils.DBConnection;
import es.iesquevedo.domain.error.DatabaseError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCMedicoRepository implements MedicoRepository {

  private final DBConnection dbConnection;
  private final MedicoRowMapper medicoRowMapper;

  @Inject
  public JDBCMedicoRepository(DBConnection dbConnection,
                                MedicoRowMapper medicoRowMapper) {
    this.dbConnection = dbConnection;
    this.medicoRowMapper = medicoRowMapper;
  }

  @Override
  public List<Medico> findAll() {
    List<Medico> medicos = new ArrayList<>();
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQLQueries.FIND_ALL_MEDICOS);
         ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        Medico medico = medicoRowMapper.mapRow(rs, rs.getRow());
        medicos.add(medico);
      }
    } catch (SQLException e) {
      log.error("Se ha producido un error en la BD");
      throw new DatabaseError(e.getMessage());
    }
    return medicos;
  }
}
