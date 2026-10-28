package es.iesquevedo.domain.mappers;

import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.domain.dto.MedicoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MedicoDTOMapper
{
  public MedicoDTO toDTO(Medico medico) {
    if  (medico == null)
      return null;
    return MedicoDTO.builder()
            .id(medico.getId())
            .nombre(medico.getNombre())
      .build();
  }

  public Medico toEntity(MedicoDTO medicoDTO) {
    if (medicoDTO == null)
      return null;
    return Medico.builder()
            .id(medicoDTO.getId())
            .nombre(medicoDTO.getNombre())
            .build();
  }


  public List<MedicoDTO> toDTOList(List<Medico> medicos) {
    return medicos.stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

}