package mp.code.pruebajava.repository;

import mp.code.pruebajava.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

  @Query("SELECT MAX(p.idProducto) FROM Producto p")
  Optional<Long> findMaxIdProducto();
}
