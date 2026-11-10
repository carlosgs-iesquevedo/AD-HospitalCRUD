package es.iesquevedo.domain.dto.xml;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteXml {
  @XmlAttribute(name = "id")
  private Long id;
  private String nombre;
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  private LocalDate fechaNacimiento;
  private String telefono;

  @XmlElementWrapper(name = "historiasClinicas")
  @XmlElement(name = "historia")
  private List<HistoriaClinicaXml> historias;

}
