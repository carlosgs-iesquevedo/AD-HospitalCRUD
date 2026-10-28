package es.iesquevedo.ui;

import es.iesquevedo.domain.error.AppError;
import es.iesquevedo.domain.error.DatabaseError;
import es.iesquevedo.domain.services.MedicoService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MedicoUI {
  private final MedicoService medicoService;

  @Inject
  public MedicoUI (MedicoService medicoService) {
    this.medicoService = medicoService;
  }

  public void getAll() {
    try {
      IO.println(medicoService.getAll());
    } catch (DatabaseError e) {
      log.error("Error de BD {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error inesperado");
      throw new AppError("Error crítico");
    }
  }
}
