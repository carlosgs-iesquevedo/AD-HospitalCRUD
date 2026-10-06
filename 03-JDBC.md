# Introducción a JDBC
JDBC (Java Database Connectivity) es una API de Java que permite a los desarrolladores interact
uar con bases de datos relacionales de manera independiente del proveedor de la base de datos. Proporciona un conjunto de clases e interfaces que permiten ejecutar consultas SQL, actualizar datos y recuperar resultados.

## PreparedStatement
`PreparedStatement` es una interfaz de JDBC que representa una consulta SQL precompilada.

## ResultSet
`ResultSet` es una interfaz de JDBC que representa el resultado de una consulta SQL. Permite iterar sobre los resultados y acceder a los datos de cada fila.

El ResultSet habrá que mapearlo a un objeto de tipo Entidad para poder trabajar con él en nuestra aplicación. Esto se puede hacer manualmente o utilizando un mapper.

## Transacciones
JDBC permite gestionar transacciones de manera programática. Una transacción es un conjunto de operaciones
    que se ejecutan como una unidad atómica, lo que significa que todas las operaciones deben completarse correctamente para que los cambios se confirmen en la base de datos. Si alguna operación falla, se puede realizar un rollback para deshacer los cambios realizados hasta ese punto.

Ejemplo de uso de transacciones en JDBC:

```java
Connection connection = null;
try {
    connection = dataSource.getConnection();
    connection.setAutoCommit(false); // Desactivar el auto-commit   
    // Realizar operaciones en la base de datos
    connection.commit(); // Confirmar los cambios
} catch (SQLException e) {
    try {
        connection.rollback(); // Deshacer los cambios
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    e.printStackTrace();
} finally {
    if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```
## Operaciones sobre varias entidades relacionadas
Cuando se trabaja con varias entidades relacionadas, es importante tener en cuenta las relaciones entre ellas y cómo
manipularlas de forma adecuada para mantener la integridad de los datos.

En las capas de presentación/dominio se pueden recoger los datos a través de un solo DTO, pero a nivel de infraestructura habrá que mapearlo a las diferentes entidades y realizar las operaciones correspondientes en la base de datos.

En el caso de Paciente y Usuario, si queremos crear un nuevo Paciente, habrá que crear el Usuario y el Paciente en la misma transacción, ya que en nuestro modelo ambos están relacionados de manera que un usuario está asociado, o bien a un paciente, o bien a un médico. Si no está asociado a ninguna de estas dos entidades entonces se trata de un usuario de perfil administrador. Por lo tanto, el proceso de crear un paciente implica además crear el usuario con el que se relaciona.



## Referencias
- [Introducción a JDBC (Baeldung)](https://www.baeldung.com/java-jdbc)