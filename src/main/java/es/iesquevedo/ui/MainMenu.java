package es.iesquevedo.ui;

import es.iesquevedo.domain.error.AppError;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainMenu {

  private final UsuarioUI usuarioUi;
  private final PacienteUI pacienteUi;

  @Inject
  public MainMenu(UsuarioUI usuarioUi, PacienteUI pacienteUi) {

    this.usuarioUi = usuarioUi;
    this.pacienteUi = pacienteUi;
  }

  public void run() {
    try {
      IO.println("Hospital App");

      usuarioUi.login();

      int opcion = 0;

      while (opcion != 10) {
        IO.println("1. Mostrar todos los pacientes");
        IO.println("10. Salir");
        IO.println("Introduzca una opción ...");

        String linea = IO.readln();
        if (linea.isEmpty()) continue;
        try {
          opcion = Integer.parseInt(linea);
        } catch (NumberFormatException e) {
          IO.println("Opción no válida");
          continue;
        }

        switch (opcion) {
          case 1:
            pacienteUi.getAll();
            break;
          case 10:
            IO.println("Hasta la vista");
            break;
          default:
            IO.println("Opción no válida");
        }
      }



    } catch (AppError e) { // Solo errores críticos no manejados
      System.err.println("Fallo grave: " + e.getMessage());
      log.error("Fallo grave {}", e.getMessage());
      System.exit(1);
    }




  }

}
