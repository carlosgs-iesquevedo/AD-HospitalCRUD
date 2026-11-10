package es.iesquevedo.domain.dto.xml;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriaClinicaXml {
  @XmlAttribute(name = "id")
  private Long id;
  private String diagnostico;
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  private LocalDate fechaAdmision;
  private Long medicoId;

  @XmlElementWrapper(name = "recetas")
  @XmlElement(name = "receta")
  private List<RecetaXml> recetas;
}
