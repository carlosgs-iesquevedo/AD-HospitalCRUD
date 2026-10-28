package es.iesquevedo.domain.mappers;

import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.model.Receta;
import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.dto.HistoriaClinicaDTOAlta;

import java.util.List;
import java.util.stream.Collectors;

public class HistoriaClinicaDTOMapper
{
  public HistoriaClinicaDTO toDTO(HistoriaClinica historia) {
    if  (historia == null)
      return null;
    return HistoriaClinicaDTO.builder()
        .id(historia.getId())
        .diagnostico(historia.getDiagnostico())
        .fechaAdmision(historia.getFechaAdmision())
        .pacienteId(historia.getPaciente().getId())
        .medicoId(historia.getMedico().getId())
        .build();
  }

  public HistoriaClinica toEntity(HistoriaClinicaDTO historiaClinicaDTO) {
    if (historiaClinicaDTO == null)
      return null;
    return HistoriaClinica.builder()
            .id(historiaClinicaDTO.getId())
            .diagnostico(historiaClinicaDTO.getDiagnostico())
            .fechaAdmision(historiaClinicaDTO.getFechaAdmision())
            .paciente(Paciente.builder().id(historiaClinicaDTO.getPacienteId()).build())
            .medico(Medico.builder().id(historiaClinicaDTO.getMedicoId()).build())
            .build();
  }

  public HistoriaClinica toEntity(HistoriaClinicaDTOAlta historiaClinicaDTO) {
    if (historiaClinicaDTO == null)
      return null;
    return HistoriaClinica.builder()
        .diagnostico(historiaClinicaDTO.getDiagnostico())
        .fechaAdmision(historiaClinicaDTO.getFechaAdmision())
        .paciente(Paciente.builder().id(historiaClinicaDTO.getPacienteId()).build())
        .medico(Medico.builder().id(historiaClinicaDTO.getMedicoId()).build())
        .recetas(
            historiaClinicaDTO.getRecetas().stream()
                .map(receta -> Receta.builder().medicamento(receta).build())
                .collect(Collectors.toList()))
        .build();
  }


  public List<HistoriaClinicaDTO> toDTOList(List<HistoriaClinica> historias) {
    return historias.stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

}