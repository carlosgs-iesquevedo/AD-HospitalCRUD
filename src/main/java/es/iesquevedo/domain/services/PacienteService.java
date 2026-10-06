package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.dto.PacienteDTOAlta;
import es.iesquevedo.domain.mappers.PacienteDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PacienteDTOMapper pacienteDTOMapper;

    @Inject
    public PacienteService (PacienteRepository pacienteRepository, PacienteDTOMapper pacienteDTOMapper) {
        this.pacienteRepository =  pacienteRepository;
        this.pacienteDTOMapper = pacienteDTOMapper;
    }

    public List<PacienteDTO> getAll() {
        return pacienteDTOMapper.toDTOList(pacienteRepository.findAll());
    }

    public Long add(PacienteDTOAlta pacienteDTO) {
        return pacienteRepository.add(pacienteDTOMapper.toEntity(pacienteDTO));
    }
}
