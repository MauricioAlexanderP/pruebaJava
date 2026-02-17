package mp.code.pruebajava.service;

import mp.code.pruebajava.dto.ProductoDto;
import mp.code.pruebajava.exceptions.ResourceNotFoundException;
import mp.code.pruebajava.mapper.ProductoMapper;
import mp.code.pruebajava.repository.ProductoRepository;
import mp.code.pruebajava.validators.CommonValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductoService {

  private final ProductoRepository data;

  public ProductoService(ProductoRepository data) {
    this.data = data;
  }

  public List<ProductoDto> getAll() {
    return data.findAll()
        .stream()
        .map(ProductoMapper::toDto)
        .collect(Collectors.toList());
  }

  public Page<ProductoDto> getAllPaginated(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("nombreProducto").ascending());
    return data.findAll(pageable)
        .map(ProductoMapper::toDto);
  }

  @Transactional
  public void save(ProductoDto dto) {
    CommonValidator.validateNotEmpty(dto.getNombreProducto(), "Nombre de producto");
    CommonValidator.validateNotNull(dto.getIdFamilia(), "Familia del producto");
    CommonValidator.validateNotNull(dto.getPrecio(), "Precio del producto");

    // Si no tiene ID, generar uno automáticamente
    if (dto.getIdProducto() == null) {
      Long nextId = generateNextId();
      dto.setIdProducto(nextId);
    }

    data.save(ProductoMapper.toEntity(dto));
  }

  /**
   * Genera el siguiente ID disponible
   * El ID puede tener hasta 11 dígitos (99999999999)
   * 
   * @return siguiente ID disponible
   */
  private Long generateNextId() {
    Long maxId = data.findMaxIdProducto().orElse(0L);
    Long nextId = maxId + 1;

    // Validar que no supere 11 dígitos
    if (nextId > 99999999999L) {
      throw new RuntimeException("Se ha alcanzado el límite máximo de productos (11 dígitos)");
    }

    return nextId;
  }

  @Transactional
  public void delete(Long id) {
    CommonValidator.validateNotNull(id, "Id del producto");

    if (!data.existsById(id)) {
      throw new ResourceNotFoundException("Producto", "id", id);
    }

    data.deleteById(id);
  }

}
