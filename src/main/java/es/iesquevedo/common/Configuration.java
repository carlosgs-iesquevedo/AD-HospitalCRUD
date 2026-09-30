package es.iesquevedo.common;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Singleton
public class Configuration {

  private final Properties p;

  public Configuration() {
    p = new Properties();
    try {
      InputStream propertiesStream =
          getClass().getClassLoader().getResourceAsStream(Constantes.MYSQL_PROPERTIES);
      p.loadFromXML(propertiesStream);
    } catch (IOException e) {
      log.error("Error cargando fichero de properties: {}", e.getMessage());
    }
  }

  public String getProperty(String clave) {
    return p.getProperty(clave);
  }

}