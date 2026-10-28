package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.Medico;

import java.util.List;

public interface MedicoRepository {
    List<Medico> findAll();
}
