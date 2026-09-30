package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.model.Usuario;
import es.iesquevedo.dao.repositories.UsuarioRepository;
import es.iesquevedo.dao.utils.DBConnection;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCUsuarioRepository implements UsuarioRepository {

  private final DBConnection dbConnection;

  @Inject
  public JDBCUsuarioRepository(DBConnection dbConnection) {
    this.dbConnection = dbConnection;
  }

  @Override
  public Optional<Usuario> findByUsername(String username) {

    Usuario usuario = Usuario.builder().username(username).build();
    try (Connection connection= dbConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_USUARIO_BY_USERNAME)) {

      preparedStatement.setString(1, username);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          usuario.setPassword(rs.getString("password"));
          return Optional.of(usuario);
        }
        return Optional.empty();
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
