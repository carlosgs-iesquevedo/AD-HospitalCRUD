package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.Paciente;

import java.util.List;

public interface PacienteRepository {
    List<Paciente> findAll();
}
