package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.HistoriaClinicaRepository;
import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.dto.PacienteDTOAlta;
import es.iesquevedo.domain.error.PacienteNoEncontradoError;
import es.iesquevedo.domain.mappers.PacienteDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PacienteDTOMapper pacienteDTOMapper;
    private final HistoriaClinicaRepository historiaRepository;

    @Inject
    public PacienteService (PacienteRepository pacienteRepository,
                            PacienteDTOMapper pacienteDTOMapper,
                            HistoriaClinicaRepository historiaRepository) {
        this.pacienteRepository =  pacienteRepository;
        this.pacienteDTOMapper = pacienteDTOMapper;
        this.historiaRepository = historiaRepository;
    }

    public List<PacienteDTO> getAll() {
        return pacienteDTOMapper.toDTOList(pacienteRepository.findAll());
    }

    public Long add(PacienteDTOAlta pacienteDTO) {
        return pacienteRepository.add(pacienteDTOMapper.toEntity(pacienteDTO));
    }

    public void update(PacienteDTO pacienteDTO) {
        pacienteRepository.update(pacienteDTOMapper.toEntity(pacienteDTO));
    }

    public enum ResultadoBorrado {
        ELIMINADO,
        CONFIRMACION_REQUERIDA
    }

    public ResultadoBorrado delete(Long pacienteId, boolean confirmarHistorias) {
        // Buscar el paciente por su ID: si no lo encuentra, lanzamos un error
        pacienteRepository.findById(pacienteId).orElseThrow(PacienteNoEncontradoError::new);
        // Si existe, comprobamos que tiene historias clínicas asociadas
        boolean tieneHistorias = historiaRepository.existsByPacienteId(pacienteId);
        // Si tiene historias necesitamos confirmación para borrarlas
        if (tieneHistorias && !confirmarHistorias) {
            return ResultadoBorrado.CONFIRMACION_REQUERIDA;
        }

        pacienteRepository.delete(pacienteId, tieneHistorias);
        return ResultadoBorrado.ELIMINADO;
    }
}
