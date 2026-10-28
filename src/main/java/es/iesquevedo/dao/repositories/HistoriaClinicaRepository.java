package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.HistoriaClinica;

import java.util.List;

public interface HistoriaClinicaRepository {
    List<HistoriaClinica> findByPacienteId(Long pacienteId);
    boolean existsByPacienteId(Long pacienteId);
    Long add(HistoriaClinica historiaClinica);
    void update(HistoriaClinica historiaClinica);
    void delete(Long id);
}
