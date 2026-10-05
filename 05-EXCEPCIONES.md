# Manejo de Excepciones en Arquitectura de Capas

## Arquitectura de Capas
Ya hemos comentado en clase las ventajas de usar una arquitectura de capas, donde cada capa tiene responsabilidades específicas y se comunica con las demás a través de interfaces bien definidas. 

En nuestro ejemplo hemos empezado a implementar las capas siguientes:
- Capa de presentación (interacción con el usuario)
- Capa de dominio (lógica de negocio/servicios)
- Capa de infraestructura (acceso a datos)

En este contexto, el manejo de excepciones es crucial para garantizar que los errores se gestionen de manera adecuada y no se propaguen de forma descontrolada.

Los errores pueden suceder en cualquier capa, y es importante capturarlos, procesarlos, loguearlos y evitar que el usuario reciba mensajes de error crípticos o información sensible. 

Si, por ejemplo, una SQLExeption llega directamente al usuario, estaremos mostrando excesiva información sobre el modelo de datos y la base de datos, lo cual es un riesgo de seguridad. Por ello, debemos capturar las excepciones y transformarlas en mensajes amigables para el usuario.

El flujo debe ser el siguiente:
1. La capa de infraestructura captura la excepción específica (por ejemplo, SQLException) y la transforma en una excepción más general (DatabaseError) o específica para la capa de dominio.
2. La capa de dominio captura la excepción lanzada por la capa de infraestructura y la transforma en una excepción más general (AppError) o específica para la capa de presentación.
3. La capa de presentación captura la excepción lanzada por la capa de dominio y muestra un mensaje amigable al usuario, sin exponer detalles internos del sistema.