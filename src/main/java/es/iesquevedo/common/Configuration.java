package es.iesquevedo.common;

import es.iesquevedo.domain.error.AppError;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Singleton
public class Configuration {

  private final Properties p = new Properties();

  public Configuration() {
    try (InputStream propertiesStream =
          getClass().getClassLoader().getResourceAsStream(Constantes.MYSQL_PROPERTIES)) {

      if (propertiesStream == null) {
        throw new AppError("No se pudo encontrar el archivo de propiedades: " + Constantes.MYSQL_PROPERTIES);
      }
      p.loadFromXML(propertiesStream);
    } catch (IOException e) {
      log.error("Error cargando fichero de properties: {}", e.getMessage());
    }
  }

  public String getProperty(String clave) {
    return p.getProperty(clave);
  }

}