package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
    List<Paciente> findAll();
    Optional<Paciente> findById(Long id);
    Long add (Paciente paciente);
    void update (Paciente paciente);
    void delete (Long id, boolean tieneHistorias);
}
