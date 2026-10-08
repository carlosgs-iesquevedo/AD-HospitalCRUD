package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.UsuarioDTO;
import es.iesquevedo.domain.services.UsuarioService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsuarioUI {
  private final UsuarioService usuarioService;

  @Inject
  public UsuarioUI(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  public void login() {
    final int MAX_INTENTOS = 3;
    int intentos = 0;

    IO.println("Por favor, introduzca sus credenciales");

    while (intentos < MAX_INTENTOS) {
      IO.println("Usuario (0 para salir): ");
      String username = IO.readln().trim();
      if ("0".equals(username)) return;

      IO.println("Contraseña: ");
      String password = IO.readln();

      if (username.isEmpty() || password.isEmpty()) {
        IO.println("Usuario y contraseña son obligatorios.");
        continue;
      }

      if (usuarioService.login(new UsuarioDTO(username, password))) {
        IO.println("Bienvenido al sistema.");
        return;
      }

      intentos++;
      IO.println("Credenciales incorrectas. Intentos restantes: " + (MAX_INTENTOS - intentos));
    }

    IO.println("Has superado el número máximo de intentos.");
  }
}