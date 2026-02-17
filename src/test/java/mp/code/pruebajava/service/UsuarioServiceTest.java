package mp.code.pruebajava.service;

import mp.code.pruebajava.dto.LoginDto;
import mp.code.pruebajava.dto.UsuarioDto;
import mp.code.pruebajava.model.Usuario;
import mp.code.pruebajava.repository.UsuarioRepitory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para UsuarioService")
class UsuarioServiceTest {

  @Mock
  private UsuarioRepitory usuarioRepository;

  @InjectMocks
  private UsuarioService usuarioService;

  private Usuario usuario;
  private LoginDto loginDto;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setIdUsuario("1");
    usuario.setNombreUsuario("admin");
    usuario.setContrasena("12345");

    loginDto = new LoginDto();
    loginDto.setNombreUsuario("admin");
    loginDto.setContrasena("12345");
  }

  @Test
  @DisplayName("Debe autenticar usuario correctamente con credenciales válidas")
  void testAutenticar_Exitoso() {
    // Given
    when(usuarioRepository.findByNombreUsuario("admin")).thenReturn(Optional.of(usuario));

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertTrue(resultado.isPresent());
    assertEquals("1", resultado.get().getIdUsuario());
    assertEquals("admin", resultado.get().getNombreUsuario());
    assertNotNull(resultado.get().getContrasena());
    verify(usuarioRepository, times(1)).findByNombreUsuario("admin");
  }

  @Test
  @DisplayName("Debe rechazar autenticación cuando la contraseña es incorrecta")
  void testAutenticar_ContrasenaIncorrecta() {
    // Given
    loginDto.setContrasena("incorrecta");
    when(usuarioRepository.findByNombreUsuario("admin")).thenReturn(Optional.of(usuario));

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, times(1)).findByNombreUsuario("admin");
  }

  @Test
  @DisplayName("Debe rechazar autenticación cuando el usuario no existe")
  void testAutenticar_UsuarioNoExiste() {
    // Given
    loginDto.setNombreUsuario("noexiste");
    when(usuarioRepository.findByNombreUsuario("noexiste")).thenReturn(Optional.empty());

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, times(1)).findByNombreUsuario("noexiste");
  }

  @Test
  @DisplayName("Debe rechazar autenticación con nombre de usuario vacío")
  void testAutenticar_UsuarioVacio() {
    // Given
    loginDto.setNombreUsuario("");

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, never()).findByNombreUsuario(anyString());
  }

  @Test
  @DisplayName("Debe rechazar autenticación con nombre de usuario null")
  void testAutenticar_UsuarioNull() {
    // Given
    loginDto.setNombreUsuario(null);

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, never()).findByNombreUsuario(anyString());
  }

  @Test
  @DisplayName("Debe rechazar autenticación con contraseña vacía")
  void testAutenticar_ContrasenaVacia() {
    // Given
    loginDto.setContrasena("");

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, never()).findByNombreUsuario(anyString());
  }

  @Test
  @DisplayName("Debe rechazar autenticación con contraseña null")
  void testAutenticar_ContrasenaNull() {
    // Given
    loginDto.setContrasena(null);

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, never()).findByNombreUsuario(anyString());
  }

  @Test
  @DisplayName("Debe rechazar autenticación con espacios en blanco en nombre de usuario")
  void testAutenticar_UsuarioConEspacios() {
    // Given
    loginDto.setNombreUsuario("   ");

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, never()).findByNombreUsuario(anyString());
  }

  @Test
  @DisplayName("Debe rechazar autenticación con espacios en blanco en contraseña")
  void testAutenticar_ContrasenaConEspacios() {
    // Given
    loginDto.setContrasena("   ");

    // When
    Optional<UsuarioDto> resultado = usuarioService.autenticar(loginDto);

    // Then
    assertFalse(resultado.isPresent());
    verify(usuarioRepository, never()).findByNombreUsuario(anyString());
  }
}
