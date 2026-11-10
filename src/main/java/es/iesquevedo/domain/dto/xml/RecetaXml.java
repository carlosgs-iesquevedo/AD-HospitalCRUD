package es.iesquevedo.domain.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaXml {
  @XmlAttribute(name = "id")
  private Long id;
  private String medicamento;
  private String dosis;
}
