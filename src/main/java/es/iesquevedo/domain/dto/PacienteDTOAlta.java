package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
public class PacienteDTOAlta {
    // En el proceso de alta, no necesitamos el ID del paciente,
    // ya que se generará automáticamente al crear el registro en la base de datos.
    //private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String username;
    private String password;
}
