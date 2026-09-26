package es.iesquevedo.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {
  private String username;
  private String password;
}
