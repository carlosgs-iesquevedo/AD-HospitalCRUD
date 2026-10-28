package es.iesquevedo.domain.error;

import es.iesquevedo.common.Constantes;

public class ForeignKeyError extends DatabaseError {
    public ForeignKeyError() {
        super(Constantes.FOREIGN_KEY_ERROR);
    }
}
