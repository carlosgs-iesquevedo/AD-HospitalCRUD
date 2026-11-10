package es.iesquevedo.domain.mappers;

import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.model.Receta;
import es.iesquevedo.domain.dto.xml.HistoriaClinicaXml;
import es.iesquevedo.domain.dto.xml.PacienteXml;
import es.iesquevedo.domain.dto.xml.RecetaXml;

import java.util.List;

public class PacienteXmlMapper {

  public PacienteXml toXml(Paciente paciente, List<HistoriaClinica> historias) {
    return PacienteXml.builder()
        .id(paciente.getId())
        .nombre(paciente.getNombre())
        .fechaNacimiento(paciente.getFechaNacimiento())
        .telefono(paciente.getTelefono())
        .historias(historias.stream().map(this::toHistoriaXml).toList())
        .build();
  }

  private HistoriaClinicaXml toHistoriaXml(HistoriaClinica historia) {
    return HistoriaClinicaXml.builder()
        .id(historia.getId())
        .diagnostico(historia.getDiagnostico())
        .fechaAdmision(historia.getFechaAdmision())
        .medicoId(historia.getMedico().getId())
        .recetas(historia.getRecetas().stream().map(this::toRecetaXml).toList())
        .build();
  }

  private RecetaXml toRecetaXml(Receta receta) {
    return RecetaXml.builder()
        .id(receta.getId())
        .medicamento(receta.getMedicamento())
        .dosis(receta.getDosis())
        .build();
  }

  public Paciente toPaciente(PacienteXml xml) {
    return Paciente.builder()
        .id(xml.getId())
        .nombre(xml.getNombre())
        .fechaNacimiento(xml.getFechaNacimiento())
        .telefono(xml.getTelefono())
        .build();
  }

  public List<HistoriaClinica> toHistorias(PacienteXml xml) {
    if (xml.getHistorias() == null) return List.of();
    return xml.getHistorias().stream().map(this::toHistoria).toList();
  }

  private HistoriaClinica toHistoria(HistoriaClinicaXml xml) {
    List<RecetaXml> recetas = xml.getRecetas() == null ? List.of() : xml.getRecetas();
    return HistoriaClinica.builder()
        .id(xml.getId())
        .diagnostico(xml.getDiagnostico())
        .fechaAdmision(xml.getFechaAdmision())
        .medico(Medico.builder().id(xml.getMedicoId()).build())
        .recetas(recetas.stream().map(this::toReceta).toList())
        .build();
  }

  private Receta toReceta(RecetaXml xml) {
    return Receta.builder()
        .id(xml.getId())
        .medicamento(xml.getMedicamento())
        .dosis(xml.getDosis())
        .build();
  }
}
