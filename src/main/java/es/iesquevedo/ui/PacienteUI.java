package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.dto.PacienteDTOAlta;
import es.iesquevedo.domain.error.AppError;
import es.iesquevedo.domain.error.DatabaseError;
import es.iesquevedo.domain.error.PacienteNoEncontradoError;
import es.iesquevedo.domain.services.PacienteService;
import es.iesquevedo.domain.services.PacienteService.ResultadoBorrado;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class PacienteUI {
    private final PacienteService pacienteService;

    // Datos de prueba
    private static final PacienteDTOAlta TEST_PACIENTE_DTO_ALTA = PacienteDTOAlta.builder()
            .nombre("Marios")
            .fechaNacimiento(LocalDate.of(2003, 10, 23))
            .telefono("690 555 777")
            .username("marios")
            .password("1234")
            .build();
    private static final PacienteDTO TEST_PACIENTE_DTO_UPDATE = PacienteDTO.builder()
        .id(17L)
        .nombre("Juan")
        .fechaNacimiento(LocalDate.of(2003, 10, 23))
        .telefono("690 555 778")
        .build();

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

    public void add() {
        try {
            IO.println(pacienteService.add(TEST_PACIENTE_DTO_ALTA));
        } catch (DatabaseError e) {
            log.error("Error de base de datos en PatientService.add()");
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new AppError("Error crítico en PacienteUI.add()");
        }
    }

    public void update() {
        try {
            pacienteService.update(TEST_PACIENTE_DTO_UPDATE);
        } catch (DatabaseError e) {
            log.error("Error de base de datos en PacienteService.update()");
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new AppError("Error crítico en PacienteUI.update()");
        }
    }

    public void delete(Long pacienteId) {
        if (pacienteId == null)
            return;
        try {
            ResultadoBorrado resultado = pacienteService.delete(pacienteId, false);

            if (resultado == ResultadoBorrado.CONFIRMACION_REQUERIDA) {
                if (confirmar("El paciente tiene historias clínicas asociadas. ¿Está seguro de que quiere eliminarlo? (s/n)")) {
                    pacienteService.delete(pacienteId, true);
                    IO.println("Paciente eliminado");
                } else {
                    IO.println("El paciente no se ha eliminado");
                }
            } else {
                IO.println("Paciente eliminado");
            }
        } catch (PacienteNoEncontradoError e) {
            IO.println("No existe un paciente con ese ID");
        } catch (DatabaseError e) {
            log.error("Error de base de datos en PacienteService.delete()");
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new AppError("Error crítico en PacienteUI.delete()");
        }
    }

    private boolean confirmar(String mensaje) {
        IO.println(mensaje);
        String respuesta = IO.readln();
        return respuesta != null && respuesta.trim().equalsIgnoreCase("s");
    }



}
