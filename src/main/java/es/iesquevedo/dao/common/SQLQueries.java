package es.iesquevedo.dao.common;

public class SQLQueries {
  public static final String FIND_USUARIO_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
  public static final String FIND_ALL_PACIENTES = "SELECT * FROM pacientes";
  public static final String ADD_PACIENTE = "INSERT INTO pacientes (nombre, fecha_nacimiento, telefono) VALUES (?, ?, ?)";
  public static final String ADD_USUARIO = "INSERT INTO usuarios (username, password, paciente_id) VALUES (?, ?, ?)";
}
