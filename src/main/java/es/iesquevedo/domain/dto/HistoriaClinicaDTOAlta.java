package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class HistoriaClinicaDTOAlta {
  // En el proceso de alta, no necesitamos el ID de la historia clínica,
  // ya que se generará automáticamente al crear el registro en la base de datos.
  //private Long id;
  private String diagnostico;
  private LocalDate fechaAdmision;
  private Long pacienteId;
  private Long medicoId;
  private List<String> recetas;
}
