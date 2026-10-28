package es.iesquevedo.domain.error;

import es.iesquevedo.common.Constantes;

public class PacienteNoEncontradoError extends DatabaseError {
    public PacienteNoEncontradoError() {
        super(Constantes.PACIENTE_NO_ENCONTRADO_ERROR);
    }
}
