package es.iesquevedo.domain.error;

public class XmlError extends RuntimeException {
    public XmlError(String message) {
        super(message);
    }
}
