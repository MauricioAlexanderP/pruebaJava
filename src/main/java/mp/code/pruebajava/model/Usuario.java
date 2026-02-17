package mp.code.pruebajava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USUARIOS")
public class Usuario {
  @Id
  @Column(name = "ID_USUARIO", nullable = false, length = 30)
  private String idUsuario;

  @Column(name = "NOMBRE_USUARIO")
  private String nombreUsuario;

  @Column(name = "CONTRASENA", length = 30)
  private String contrasena;


}