package mp.code.pruebajava.repository;

import mp.code.pruebajava.model.ProductosFamilia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoFamiliaRepository extends JpaRepository<ProductosFamilia, Long> {
}
