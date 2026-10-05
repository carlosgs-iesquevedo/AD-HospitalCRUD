package es.iesquevedo.ui;

import es.iesquevedo.domain.error.AppError;
import es.iesquevedo.domain.error.DatabaseError;
import es.iesquevedo.domain.services.PacienteService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PacienteUI {
    private final PacienteService pacienteService;

    @Inject
    public PacienteUI (PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void getAll() {
        try {
            IO.println(pacienteService.getAll());
        } catch (DatabaseError e) {
            log.error("Error de BD {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado");
            throw new AppError("Error crítico");
        }
    }
}
