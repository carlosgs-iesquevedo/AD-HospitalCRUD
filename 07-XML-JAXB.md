# Tratamiento de ficheros XML con JAXB

## Añadir dependencias de JAXB al proyecto

## Proceso de borrado con copia antes de borrar de la BD
* Se hará una copia de los datos a borrar (todo lo relacionado con un paciente) en un fichero XML una vez se haya confirmado que se quiere borrar el paciente de la BD junto con sus historias clínicas.
* Si falla la copia del fichero XML, no se borrará el paciente de la BD.
* El flujo parte de `PacienteService.delete()`.
* Hay que recuperar de la BD el paciente a borrar y sus historias clínicas.
* Será necesario crear DTOs específicos para este proceso con los datos del paciente, las historias y recetas. Se excluyen los datos de usuario.
* En estos DTOs se anotarán las clases con JAXB para que se pueda generar el fichero XML.  Los `id` los trataremos como atributos xml
* Las fechas habrá que tratarlas o bien como String porque el JAXB no hace conversión automática, o bien programar un XmlAdapter que haga la conversión.
* El *marshaller* escribirá el fichero XML en la ruta `backups/pacientes/` incluyendo en el nombre del fichero, el id del paciente a borrar y la fecha de la copia.

## Proceso de restauración de un paciente borrado
* Se hará una restauración de un paciente borrado a partir de un fichero XML que contenga los datos del paciente y sus historias clínicas.
* El flujo parte de `PacienteService.restore()`, un nuevo método.
* Se leerá el fichero XML y se generarán los DTOs correspondientes.
* Se insertarán los datos en la BD, incluyendo el paciente y sus historias clínicas.
* Se añadirá una opción en la vista de pacientes para restaurar un paciente a partir de un fichero XML.
* Hay que comprobar que el paciente a restaurar no exista ya en la BD, para evitar duplicidades. En caso de existir, se mostrará un mensaje de error y no se realizará la restauración.
