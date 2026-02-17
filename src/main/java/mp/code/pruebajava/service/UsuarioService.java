package mp.code.pruebajava.service;

import lombok.RequiredArgsConstructor;
import mp.code.pruebajava.dto.LoginDto;
import mp.code.pruebajava.dto.UsuarioDto;
import mp.code.pruebajava.model.Usuario;
import mp.code.pruebajava.repository.UsuarioRepitory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepitory data;

  /**
   * Autentica un usuario con sus credenciales
   * @param loginDto DTO con las credenciales del usuario
   * @return Optional con UsuarioDto si las credenciales son válidas
   */
  public Optional<UsuarioDto> autenticar(LoginDto loginDto) {
    if (loginDto.getNombreUsuario() == null || loginDto.getNombreUsuario().trim().isEmpty()) {
      return Optional.empty();
    }
    if (loginDto.getContrasena() == null || loginDto.getContrasena().trim().isEmpty()) {
      return Optional.empty();
    }

    Optional<Usuario> usuario = data.findByNombreUsuario(loginDto.getNombreUsuario());
    
    if (usuario.isPresent() && usuario.get().getContrasena().equals(loginDto.getContrasena())) {
      return Optional.of(convertirADto(usuario.get()));
    }
    
    return Optional.empty();
  }

  /**
   * Convierte una entidad Usuario a UsuarioDto
   * @param usuario Entidad Usuario
   * @return UsuarioDto sin exponer la contraseña
   */
  private UsuarioDto convertirADto(Usuario usuario) {
    UsuarioDto dto = new UsuarioDto();
    dto.setIdUsuario(usuario.getIdUsuario());
    dto.setNombreUsuario(usuario.getNombreUsuario());
    dto.setContrasena(usuario.getContrasena());
  
    return dto;
  }
}
