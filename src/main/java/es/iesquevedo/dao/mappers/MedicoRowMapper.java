package es.iesquevedo.dao.mappers;

import es.iesquevedo.dao.model.Medico;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicoRowMapper {
    public Medico mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Medico.builder()
                .id(rs.getLong("medico_id"))
                .nombre(rs.getString("nombre"))
                .especialidad(rs.getString("especialidad"))
                .telefono(rs.getString("telefono"))
                .build();
    }
}

