package es.iesquevedo.domain.services;

import es.iesquevedo.common.Constantes;
import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.repositories.HistoriaClinicaRepository;
import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.dto.PacienteDTOAlta;
import es.iesquevedo.domain.dto.xml.PacienteXml;
import es.iesquevedo.domain.error.PacienteDuplicadoError;
import es.iesquevedo.domain.error.PacienteNoEncontradoError;
import es.iesquevedo.domain.error.XmlError;
import es.iesquevedo.domain.mappers.PacienteDTOMapper;
import es.iesquevedo.domain.mappers.PacienteXmlMapper;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PacienteDTOMapper pacienteDTOMapper;
    private final HistoriaClinicaRepository historiaRepository;
    private final PacienteXmlMapper pacienteXmlMapper;

    @Inject
    public PacienteService (PacienteRepository pacienteRepository,
                            PacienteDTOMapper pacienteDTOMapper,
                            HistoriaClinicaRepository historiaRepository,
                            PacienteXmlMapper pacienteXmlMapper) {
        this.pacienteRepository =  pacienteRepository;
        this.pacienteDTOMapper = pacienteDTOMapper;
        this.historiaRepository = historiaRepository;
        this.pacienteXmlMapper = pacienteXmlMapper;
    }

    public List<PacienteDTO> getAll() {
        return pacienteDTOMapper.toDTOList(pacienteRepository.findAll());
    }

    public Long add(PacienteDTOAlta pacienteDTO) {
        return pacienteRepository.add(pacienteDTOMapper.toEntity(pacienteDTO));
    }

    public void update(PacienteDTO pacienteDTO) {
        pacienteRepository.update(pacienteDTOMapper.toEntity(pacienteDTO));
    }

    public enum ResultadoBorrado {
        ELIMINADO,
        CONFIRMACION_REQUERIDA
    }

    public ResultadoBorrado delete(Long pacienteId, boolean confirmarHistorias) {
        // Buscar el paciente por su ID: si no lo encuentra, lanzamos un error
        Paciente paciente = pacienteRepository.findById(pacienteId)
            .orElseThrow(PacienteNoEncontradoError::new);
        // Si existe, comprobamos que tiene historias clínicas asociadas
        List<HistoriaClinica> historias =
            historiaRepository.findByPacienteId(pacienteId);
        boolean tieneHistorias = !historias.isEmpty();
            // Si tiene historias necesitamos confirmación para borrarlas
        if (tieneHistorias && !confirmarHistorias) {
            return ResultadoBorrado.CONFIRMACION_REQUERIDA;
        }

        exportarPacienteXml(paciente, historias);
        pacienteRepository.delete(pacienteId, tieneHistorias);
        return ResultadoBorrado.ELIMINADO;
    }

    private void exportarPacienteXml(Paciente paciente, List<HistoriaClinica> historias) {
        PacienteXml xml = pacienteXmlMapper.toXml(paciente, historias);

        Path directorio = Path.of(Constantes.RUTA_BACKUP_PACIENTES);
        String fecha = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        Path archivo = directorio.resolve(
            Constantes.PREFIJO_BACKUP_PACIENTE + paciente.getId() + "-"
                + fecha + Constantes.EXTENSION_BACKUP_PACIENTE);
        try {
            Files.createDirectories(directorio);

            JAXBContext context = JAXBContext.newInstance(PacienteXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            try (OutputStream salida = Files.newOutputStream(
                archivo, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                marshaller.marshal(xml, salida);
            }
        } catch (JAXBException | IOException e) {
            log.error("Error exportando paciente a XML: {}", e.getMessage());
            throw new XmlError("No se pudo exportar el paciente a XML");
        }
    }

    public void restore(Long pacienteId) {
        // Primero comprobamos duplicados, antes de tocar ficheros
        if (pacienteRepository.findById(pacienteId).isPresent()) {
            throw new PacienteDuplicadoError();
        }
        PacienteXml xml = importarPacienteXml(buscarBackup(pacienteId));
        if (!pacienteId.equals(xml.getId()) || xml.getNombre() == null
            || xml.getFechaNacimiento() == null) {
            throw new XmlError("El fichero XML no es válido para el paciente " + pacienteId);
        }
        pacienteRepository.restore(pacienteXmlMapper.toPaciente(xml),
            pacienteXmlMapper.toHistorias(xml));
    }

    private PacienteXml importarPacienteXml(Path archivo) {
        if (archivo == null || !Files.isRegularFile(archivo)) {
            throw new XmlError("No existe el fichero XML indicado");
        }
        try (InputStream entrada = Files.newInputStream(archivo)) {
            Unmarshaller unmarshaller = JAXBContext.newInstance(PacienteXml.class).createUnmarshaller();
            return (PacienteXml) unmarshaller.unmarshal(entrada);
        } catch (JAXBException | IOException e) {
            log.error("Error leyendo el XML: {}", e.getMessage());
            throw new XmlError("No se pudo leer el paciente del fichero XML");
        }
    }

    private Path buscarBackup(Long pacienteId) {
        Path directorio = Path.of(Constantes.RUTA_BACKUP_PACIENTES);
        String prefijo = Constantes.PREFIJO_BACKUP_PACIENTE + pacienteId + "-";
        try (Stream<Path> ficheros = Files.list(directorio)) {
            // La fecha ISO básica ordena bien por nombre: nos quedamos con la copia más reciente
            return ficheros
                .filter(f -> {
                    String nombre = f.getFileName().toString();
                    return nombre.startsWith(prefijo) && nombre.endsWith(Constantes.EXTENSION_BACKUP_PACIENTE);
                })
                .max(Comparator.comparing(f -> f.getFileName().toString()))
                .orElseThrow(() -> new XmlError(
                    "No hay copia de seguridad del paciente " + pacienteId));
        } catch (IOException e) {
            log.error("Error leyendo {}: {}", directorio, e.getMessage());
            throw new XmlError("No se pudo acceder al directorio de copias");
        }
    }

}
