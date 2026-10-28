# El patrón de diseño DTO (Data Transfer Object)    

## Qué es un DTO
* Los dtos son objetos que se utilizan para transferir datos entre diferentes capas de una aplicación.  
* Se utilizan para encapsular los datos y evitar la exposición directa de los objetos de dominio.
* Ejemplos y ampliación en [este enlace](https://github.com/joseluisgs/DesarrolloWebEntornosServidor-02-2025-2026/blob/master/springboot/05-Servicios.md#53-patr%C3%B3n-dto)

## Mapeadores *(Mappers)*
* Los *mappers* son clases que se utilizan para mapear los datos entre objetos.

## El patrón Builder
* El patrón Builder es un patrón de diseño creacional que se utiliza para construir objetos complejos paso a paso.  
* Permite separar la construcción de un objeto de su representación.
* En clases con muchos atributos resulta muy útil para evitar constructores con muchos parámetros y mejorar la legibilidad del código.
* Facilita el mantenimiento del código de los mappers

## Dilema: un DTO para cada entidad o un DTO para cada proceso
* Un DTO para cada entidad:  
  * Ventajas:  
    * Simplicidad: cada entidad tiene su propio DTO, lo que facilita la comprensión del código.  
    * Mantenimiento: si se cambia la entidad, solo hay que cambiar el DTO correspondiente.  
  * Desventajas:  
    * Redundancia: si varias entidades tienen atributos similares, se pueden crear DTOs redundantes.  
    * Complejidad: si hay muchas entidades, puede haber muchos DTOs, lo que puede dificultar la gestión del código.
* Un DTO para cada proceso:  
  * Ventajas:  
    * Flexibilidad: se pueden crear DTOs específicos para cada proceso, lo que permite adaptarse mejor a los requisitos de la aplicación.  
    * Reutilización: se pueden reutilizar DTOs en diferentes procesos, lo que reduce la redundancia.  
  * Desventajas:  
    * Complejidad: si hay muchos procesos, puede haber muchos DTOs, lo que puede dificultar la gestión del código.  
    * Mantenimiento: si se cambia un proceso, puede ser necesario cambiar varios DTOs, lo que puede dificultar el mantenimiento del código.
* Solución intermedia:  
  * Crear DTOs específicos para cada proceso, pero reutilizar DTOs existentes cuando sea posible.  
  * Pensar en la necesidad de ocultar información sensible o irrelevante para el proceso, y no exponer toda la entidad.
  * Por ejemplo, en el caso de un paciente, podemos tener un DTO para el alta de un paciente, otro para la modificación de un paciente y otro para la visualización de un paciente.
  * Por otro lado, puede no ser necesario crear DTOs para entidades que no vayan a tener comunicación en otras capas.