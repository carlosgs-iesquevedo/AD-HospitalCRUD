# Introducción a JDBC
JDBC (Java Database Connectivity) es una API de Java que permite a los desarrolladores interact
uar con bases de datos relacionales de manera independiente del proveedor de la base de datos. Proporciona un conjunto de clases e interfaces que permiten ejecutar consultas SQL, actualizar datos y recuperar resultados.

## PreparedStatement
`PreparedStatement` es una interfaz de JDBC que representa una consulta SQL precompilada.

## ResultSet
`ResultSet` es una interfaz de JDBC que representa el resultado de una consulta SQL. Permite iterar sobre los resultados y acceder a los datos de cada fila.

El ResultSet habrá que mapearlo a un objeto de tipo Entidad para poder trabajar con él en nuestra aplicación. Esto se puede hacer manualmente o utilizando un mapper.

## Referencias
- [Introducción a JDBC (Baeldung)](https://www.baeldung.com/java-jdbc)