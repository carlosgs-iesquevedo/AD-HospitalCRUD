package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.dto.HistoriaClinicaDTOAlta;
import es.iesquevedo.domain.error.AppError;
import es.iesquevedo.domain.error.DatabaseError;
import es.iesquevedo.domain.services.HistoriaClinicaService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class HistoriaUI {
  private final HistoriaClinicaService historiaService;

  // Datos de prueba
  private static final HistoriaClinicaDTOAlta TEST_HISTORIA_DTO_ALTA = HistoriaClinicaDTOAlta.builder()
      .diagnostico("Nauseas")
      .fechaAdmision(LocalDate.of(2026, 10, 26))
      .pacienteId(1L)
      .medicoId(2L)
      .recetas(List.of("Kytril", "Paracetamol") )
      .build();

  private static final HistoriaClinicaDTO TEST_HISTORIA_DTO_UPDATE = HistoriaClinicaDTO.builder()
      .id(11L)
      .diagnostico("Nauseassss")
      .fechaAdmision(LocalDate.of(2026, 10, 26))
      .pacienteId(12L)
      .medicoId(2L)
      .build();

  private static final Long TEST_HISTORIA_DELETE_ID = 5L;

  @Inject
  public HistoriaUI(HistoriaClinicaService historiaService) {
    this.historiaService = historiaService;
  }

  public void getAll(Long pacienteId) {
    if (pacienteId == null)
      return;
    try {
      IO.println(historiaService.getAll(pacienteId));
    } catch (DatabaseError e) {
      log.error("Error de BD {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error inesperado");
      throw new AppError("Error crítico");
    }
  }

  public void add() {
    try {
      IO.println(historiaService.add(TEST_HISTORIA_DTO_ALTA));
    } catch (DatabaseError e) {
      log.error("Error de BD {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error inesperado");
      throw new AppError("Error crítico");
    }
  }

  public void update() {
    try {
      historiaService.update(TEST_HISTORIA_DTO_UPDATE);
    } catch (DatabaseError e) {
      log.error("Error de BD {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error inesperado");
      throw new AppError("Error crítico");
    }
  }

  public void delete() {
    try {
      historiaService.delete(TEST_HISTORIA_DELETE_ID);
    } catch (DatabaseError e) {
      log.error("Error de BD {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error inesperado");
      throw new AppError("Error crítico");
    }
  }


}
