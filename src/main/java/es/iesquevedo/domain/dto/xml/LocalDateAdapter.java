package es.iesquevedo.domain.dto.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

  @Override
  public LocalDate unmarshal(String fecha) {
    return fecha == null ? null : LocalDate.parse(fecha);
  }

  @Override
  public String marshal(LocalDate fecha) {
    return fecha == null ? null : fecha.toString();
  }
}
