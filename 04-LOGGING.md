# Logging con Java
El logging es una técnica utilizada en el desarrollo de software para registrar información sobre la ejecución de un programa. Esta información puede ser útil para depurar errores, monitorear el rendimiento y comprender el comportamiento de la aplicación. En Java, existen varias bibliotecas y frameworks que facilitan la implementación de logging en aplicaciones. A continuación, se presentan algunas de las bibliotecas más populares y cómo utilizarlas.

## Bibliotecas de Logging en Java
### SLF4J
SLF4J (Simple Logging Facade for Java) es una biblioteca que proporciona una interfaz simple para diferentes motores de logging. Permite a los desarrolladores escribir código de logging que puede ser reemplazado por diferentes implementaciones sin necesidad de cambiar el código fuente.

### Log4J2
Log4J2 es una biblioteca de logging avanzada que ofrece un alto rendimiento y flexibilidad. Permite configurar diferentes niveles de logging, como DEBUG, INFO, WARN y ERROR, y soporta la configuración a través de archivos XML o propiedades.

### Otras bibliotecas
- **java.util.logging**: Es la biblioteca de logging incluida en el JDK de Java, pero es menos flexible y potente que otras opciones como SLF4J o Log4J2.
- **Logback**: Es otra biblioteca de logging que es compatible con SLF4J y ofrece características avanzadas similares a Log4J2.

## Configuración del combo SLF4J + Log4J2
1. Para utilizar SLF4J con Log4J2, primero debes agregar las dependencias necesarias en tu archivo `pom.xml` si estás utilizando Maven:
2. Después debes crear un archivo de configuración para Log4J2, como `log4j2.xml`, donde puedes definir los niveles de logging, los appenders (destinos de los logs) y los patrones de salida.
3. Finalmente, puedes comenzar a usar el logging en tu código, creando instancias de Logger y llamando a sus métodos para registrar mensajes.

## Ejemplo de uso con SLF4J y Log4J2
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class MyClass {
    private static final Logger log = LoggerFactory.getLogger(MyClass.class);

    public void doSomething() {
        log.info("Doing something...");
        try {
            // Código que puede lanzar una excepción
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }
}
```

## Ejemplo de uso con Lombok

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClass {
    public void doSomething() {
        log.info("Doing something...");
        try {
            // Código que puede lanzar una excepción
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }
}
```