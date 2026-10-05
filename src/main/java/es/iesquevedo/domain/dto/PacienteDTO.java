package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String telefono;
}
