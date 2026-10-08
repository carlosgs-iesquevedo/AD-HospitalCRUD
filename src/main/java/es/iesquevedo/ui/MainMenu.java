package es.iesquevedo.ui;

import es.iesquevedo.domain.error.AppError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainMenu {

  private static final int OPCION_LISTAR_PACIENTES = 1;
  private static final int OPCION_AÑADIR_PACIENTE = 2;
  private static final int OPCION_SALIR = 10;

  private final UsuarioUI usuarioUi;
  private final PacienteUI pacienteUi;

  @Inject
  public MainMenu(UsuarioUI usuarioUi, PacienteUI pacienteUi) {
    this.usuarioUi = usuarioUi;
    this.pacienteUi = pacienteUi;
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
          case OPCION_LISTAR_PACIENTES:
            pacienteUi.getAll();
            break;
          case OPCION_AÑADIR_PACIENTE:
            pacienteUi.save();
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
      System.err.println("Fallo grave: " + e.getMessage());
      log.error("Fallo grave {}", e.getMessage());
      System.exit(1);
    }
  }

  private void printMenu() {
    IO.println("\n=== Menú principal ===");
    IO.println(OPCION_LISTAR_PACIENTES + ". Mostrar todos los pacientes");
    IO.println(OPCION_AÑADIR_PACIENTE + ". Añadir paciente");
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
}
