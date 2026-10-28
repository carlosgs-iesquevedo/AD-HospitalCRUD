package es.iesquevedo.ui;

import es.iesquevedo.domain.error.AppError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainMenu {

  private static final int OPCION_MOSTRAR_PACIENTES = 1;
  private static final int OPCION_AÑADIR_PACIENTE = 2;
  private static final int OPCION_ACTUALIZAR_PACIENTE = 3;
  private static final int OPCION_ELIMINAR_PACIENTE = 4;
  private static final int OPCION_MOSTRAR_MEDICOS = 5;
  private static final int OPCION_MOSTRAR_HISTORIA = 6;
  private static final int OPCION_AÑADIR_REGISTRO_HISTORIA  = 7;
  private static final int OPCION_ACTUALIZAR_REGISTRO_HISTORIA  = 8;
  private static final int OPCION_BORRAR_REGISTRO_HISTORIA  = 9;
  private static final int OPCION_SALIR = 10;

  private final UsuarioUI usuarioUi;
  private final PacienteUI pacienteUi;
  private final MedicoUI medicoUi;
  private final HistoriaUI historiaUi;

  @Inject
  public MainMenu(UsuarioUI usuarioUi, PacienteUI pacienteUi, MedicoUI medicoUi, HistoriaUI historiaUi) {
    this.usuarioUi = usuarioUi;
    this.pacienteUi = pacienteUi;
    this.medicoUi = medicoUi;
    this.historiaUi = historiaUi;
  }

  public void run() {
    try {
      IO.println("=== Hospital App ===");

      usuarioUi.login();

      int opcion;
      do {
        printMenu();
        opcion = readOption();

        switch (opcion) {
          case OPCION_MOSTRAR_PACIENTES:
            pacienteUi.getAll();
            break;
          case OPCION_AÑADIR_PACIENTE:
            pacienteUi.add();
            break;
          case OPCION_ACTUALIZAR_PACIENTE:
            pacienteUi.update();
            break;
          case OPCION_ELIMINAR_PACIENTE:
            pacienteUi.delete(introPaciente());
            break;
          case OPCION_MOSTRAR_MEDICOS:
            medicoUi.getAll();
            break;
          case OPCION_MOSTRAR_HISTORIA:
            historiaUi.getAll(introPaciente());
            break;
          case OPCION_AÑADIR_REGISTRO_HISTORIA:
            historiaUi.add();
            break;
          case OPCION_ACTUALIZAR_REGISTRO_HISTORIA:
            historiaUi.update();
            break;
          case OPCION_BORRAR_REGISTRO_HISTORIA:
            historiaUi.delete();
            break;
          case OPCION_SALIR:
            IO.println("Hasta la vista.");
            break;
          default:
            if (opcion != -1) {
              IO.println("Opción no válida.");
            }
        }

      } while (opcion != OPCION_SALIR);

    } catch (AppError e) {
      IO.println("Fallo grave: " + e.getMessage());
      log.error("Fallo grave {}", e.getMessage());
      System.exit(1);
    }
  }

  private void printMenu() {
    IO.println("\n=== Menú principal ===");
    IO.println(OPCION_MOSTRAR_PACIENTES + ". Mostrar todos los pacientes");
    IO.println(OPCION_AÑADIR_PACIENTE + ". Añadir paciente");
    IO.println(OPCION_ACTUALIZAR_PACIENTE + ". Actualizar paciente");
    IO.println(OPCION_ELIMINAR_PACIENTE + ". Borrar paciente");
    IO.println(OPCION_MOSTRAR_MEDICOS + ". Mostrar todos los médicos");
    IO.println(OPCION_MOSTRAR_HISTORIA + ". Mostrar la historia de un paciente");
    IO.println(OPCION_AÑADIR_REGISTRO_HISTORIA + ". Añadir un registro a la historia de un paciente");
    IO.println(OPCION_ACTUALIZAR_REGISTRO_HISTORIA + ". Actualizar un registro de la historia de un paciente");
    IO.println(OPCION_BORRAR_REGISTRO_HISTORIA + ". Borrar un registro");
    IO.println(OPCION_SALIR + ". Salir");
    IO.println("Introduzca una opción:");
  }

  private int readOption() {
    String linea = IO.readln();

    if (linea == null || linea.trim().isEmpty()) {
      IO.println("Debe introducir una opción.");
      return -1;
    }

    try {
      return Integer.parseInt(linea.trim());
    } catch (NumberFormatException e) {
      IO.println("Opción no válida. Debe introducir un número.");
      return -1;
    }
  }

  private Long introPaciente() {
    // obtener un id de paciente
    IO.println("Introduzca el ID del paciente: ");
    String linea = IO.readln();
    if (linea == null || linea.trim().isEmpty()) {
      IO.println("Debe introducir un id de paciente.");
      return null;
    }

    try {
      return Long.parseLong(linea.trim());
    } catch (NumberFormatException e) {
      IO.println("ID no válido. Debe introducir un número.");
      return null;
    }
  }
}
