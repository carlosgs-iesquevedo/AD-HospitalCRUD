package es.iesquevedo.dao.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoriaClinica {
  private Long id;
  private String diagnostico;
  private LocalDate fechaAdmision;
  private Paciente paciente;
  private Medico medico;
  private List<Receta> recetas;
}
