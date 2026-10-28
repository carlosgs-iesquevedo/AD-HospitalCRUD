package es.iesquevedo.dao.common;

public class SQLQueries {
  // Usuario queries
  public static final String FIND_USUARIO_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
  public static final String ADD_USUARIO = "INSERT INTO usuarios (username, password, paciente_id) VALUES (?, ?, ?)";
  public static final String DELETE_USUARIO_BY_PACIENTE = "DELETE FROM usuarios WHERE paciente_id = ?";

  // Paciente queries
  public static final String FIND_ALL_PACIENTES = "SELECT * FROM pacientes";
  public static final String FIND_PACIENTE_BY_ID = "SELECT * FROM pacientes WHERE paciente_id = ?";
  public static final String ADD_PACIENTE = "INSERT INTO pacientes (nombre, fecha_nacimiento, telefono) VALUES (?, ?, ?)";
  public static final String UPDATE_PACIENTE = "UPDATE pacientes SET nombre = ?, fecha_nacimiento = ?, telefono = ? WHERE paciente_id = ?";
  public static final String DELETE_PACIENTE = "DELETE FROM pacientes WHERE paciente_id = ?";

  // Historia Clinica queries
  public static final String FIND_HISTORIAS_BY_PACIENTE = """
    SELECT hc.historia_id, hc.paciente_id, hc.medico_id,
           hc.diagnostico, hc.fecha_admision,
           r.receta_id, r.medicamento
    FROM historia_clinica hc
    LEFT JOIN recetas r ON hc.historia_id = r.historia_id
    WHERE hc.paciente_id = ?
    ORDER BY hc.historia_id
    """;
  public static final String ADD_HISTORIA = "INSERT INTO historia_clinica (paciente_id, medico_id, diagnostico, fecha_admision) VALUES (?, ?, ?, ?)";
  public static final String EXISTS_HISTORIA_BY_PACIENTE = "SELECT 1 FROM historia_clinica WHERE paciente_id = ? LIMIT 1";
  public static final String UPDATE_HISTORIA = "UPDATE historia_clinica SET paciente_id = ?, medico_id = ?, diagnostico = ?, fecha_admision = ? WHERE historia_id = ?";
  public static final String DELETE_HISTORIA = "DELETE FROM historia_clinica WHERE historia_id = ?";
  public static final String DELETE_HISTORIAS_BY_PACIENTE = "DELETE FROM historia_clinica WHERE paciente_id = ?";

  // Medico queries
  public static final String FIND_ALL_MEDICOS = "SELECT * FROM medicos";

  // Receta queries
  public static final String ADD_RECETA = "INSERT INTO recetas (historia_id, medicamento, dosis) VALUES (?, ?, ?)";
  public static final String DELETE_RECETAS_BY_PACIENTE = """
    DELETE FROM recetas WHERE historia_id IN
      (SELECT historia_id FROM historia_clinica WHERE paciente_id = ?)
    """;
  public static final String DELETE_RECETAS_BY_HISTORIA = "DELETE FROM recetas WHERE historia_id = ?";

  // Cita queries
  public static final String DELETE_CITAS_BY_PACIENTE = "DELETE FROM citas WHERE paciente_id = ?";

  // Factura queries
  public static final String DELETE_FACTURAS_BY_PACIENTE = "DELETE FROM facturas WHERE paciente_id = ?";

}