package mp.code.pruebajava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
  private String idUsuario;
  private String nombreUsuario;
  private String contrasena;

  public UsuarioDto(){}

  public UsuarioDto(String idUsuario, String nombreUsuario, String contrasena) {
    this.idUsuario = idUsuario;
    this.nombreUsuario = nombreUsuario;
    this.contrasena = contrasena;
  }
}
