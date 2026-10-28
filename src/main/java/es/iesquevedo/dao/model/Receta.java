package es.iesquevedo.dao.model;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receta {
  private Long id;
  private String medicamento;
  private String dosis;
  private HistoriaClinica historia;
}
