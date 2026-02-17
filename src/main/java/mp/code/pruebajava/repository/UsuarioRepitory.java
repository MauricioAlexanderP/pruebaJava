package mp.code.pruebajava.repository;

import mp.code.pruebajava.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepitory extends JpaRepository<Usuario, String> {

  Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
