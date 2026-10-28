package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.HistoriaClinicaRepository;
import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.dto.HistoriaClinicaDTOAlta;
import es.iesquevedo.domain.mappers.HistoriaClinicaDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class HistoriaClinicaService {
  private final HistoriaClinicaRepository historiaRepository;
  private final HistoriaClinicaDTOMapper historiaDTOMapper;

  @Inject
  public HistoriaClinicaService(HistoriaClinicaRepository historiaRepository,
                                HistoriaClinicaDTOMapper historiaDTOMapper) {
    this.historiaRepository =  historiaRepository;
    this.historiaDTOMapper = historiaDTOMapper;
  }

  public List<HistoriaClinicaDTO> getAll(Long pacienteId) {
    return historiaDTOMapper.toDTOList(historiaRepository.findByPacienteId(pacienteId));
  }

  public Long add(HistoriaClinicaDTOAlta historiaClinicaDTO) {
    return historiaRepository.add(historiaDTOMapper.toEntity(historiaClinicaDTO));
  }

  public void update(HistoriaClinicaDTO historiaClinicaDTO) {
    historiaRepository.update(historiaDTOMapper.toEntity(historiaClinicaDTO));
  }

  public void delete(Long historiaId) {
    historiaRepository.delete(historiaId);
  }
}
