package es.iesquevedo.dao.mappers;

import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.dao.model.Paciente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoriaClinicaRowMapper {
    public HistoriaClinica mapRow(ResultSet rs, int rowNum) throws SQLException {
        return HistoriaClinica.builder()
                .id(rs.getLong("historia_id"))
                .diagnostico(rs.getString("diagnostico"))
                .fechaAdmision(rs.getDate("fecha_admision").toLocalDate())
                .paciente(Paciente.builder()
                    .id(rs.getLong("paciente_id"))
                    .build())
                .medico(Medico.builder()
                    .id(rs.getLong("medico_id"))
                    .build())
                .build();
    }
}

