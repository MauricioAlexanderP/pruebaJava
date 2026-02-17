package mp.code.pruebajava.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mp.code.pruebajava.dto.LoginDto;
import mp.code.pruebajava.dto.UsuarioDto;
import mp.code.pruebajava.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

  private final UsuarioService usuarioService;

  @GetMapping()
  public String login(
      @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      HttpSession session,
      Model model) {

    if (session.getAttribute("usuario") != null) {
      return "redirect:/productos";
    }

    if (error != null) {
      model.addAttribute("showError", true);
      model.addAttribute("errorMessage", "Usuario o contraseña incorrectos");
    }

    if (logout != null) {
      model.addAttribute("showLogout", true);
      model.addAttribute("logoutMessage", "Has cerrado sesión exitosamente");
    }

    return "login/login";
  }

  @PostMapping("/login")
  public String procesarLogin(
      @RequestParam("nombreUsuario") String nombreUsuario,
      @RequestParam("contrasena") String contrasena,
      HttpSession session,
      Model model) {

    LoginDto loginDto = new LoginDto();
    loginDto.setNombreUsuario(nombreUsuario);
    loginDto.setContrasena(contrasena);

    Optional<UsuarioDto> usuarioDto = usuarioService.autenticar(loginDto);

    if (usuarioDto.isPresent()) {
      session.setAttribute("usuario", usuarioDto.get());
      session.setAttribute("nombreUsuario", usuarioDto.get().getNombreUsuario());
      return "redirect:/productos";
    }

    return "redirect:/?error=true";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/?logout=true";
  }
}
