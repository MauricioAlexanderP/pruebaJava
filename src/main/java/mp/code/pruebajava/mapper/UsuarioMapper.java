package mp.code.pruebajava.mapper;

import mp.code.pruebajava.dto.UsuarioDto;
import mp.code.pruebajava.model.Usuario;

public final class UsuarioMapper {

  private UsuarioMapper(){}

  /**
   * El metodo toDto convierte un Usuario a un UsuarioDto. Si el usuario es null, devuelve null.
   * @param usuario el Usuario a convertir
   * @return un UsuarioDto con los datos del Usuario, o null si el usuario es null
   * */
  public static UsuarioDto toDto(Usuario usuario) {
    if (usuario == null) return null;

    return new UsuarioDto(
        usuario.getIdUsuario(),
        usuario.getNombreUsuario(),
        usuario.getContrasena()
    );
  }

  /**
   * El metodo toEntity convierte un UsuarioDto a un Usuario. Si el dto es null, devuelve null.
   * @param dto: el UsuarioDto a convertir
   * @return un Usuario con los datos del dto, o null si el dto es null
   */
  public static Usuario toEntity(UsuarioDto dto) {
    if (dto == null) return null;

    Usuario usuario = new Usuario();

    usuario.setIdUsuario(dto.getIdUsuario());
    usuario.setNombreUsuario(dto.getNombreUsuario());
    usuario.setContrasena(dto.getContrasena());

    return usuario;
  }
}
