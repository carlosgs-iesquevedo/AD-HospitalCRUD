package es.iesquevedo.dao.common;

public class SQLQueries {
  public static final String FIND_USUARIO_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";

  public static final String FIND_ALL_PACIENTES = "SELECT * FROM pacientes";
}
