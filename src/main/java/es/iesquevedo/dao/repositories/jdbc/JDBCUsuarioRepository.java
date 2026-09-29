package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.model.Usuario;
import es.iesquevedo.dao.repositories.UsuarioRepository;

import java.util.Optional;

public class JDBCUsuarioRepository implements UsuarioRepository {

  @Override
  public Optional<Usuario> findByUsername(String username) {
    // TODO: Implementar la lógica para obtener el usuario desde la base de datos usando JDBC
    return Optional.of(Usuario.builder()
        .username("paciente1")
        .password("1234")
        .build());
  }

}
