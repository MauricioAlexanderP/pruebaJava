package mp.code.pruebajava.mapper;

import mp.code.pruebajava.dto.ProductoDto;
import mp.code.pruebajava.model.Producto;
import mp.code.pruebajava.model.ProductosFamilia;

public final class ProductoMapper {

  private ProductoMapper() {
  }

  /**
   * Convierte un objeto Producto a ProductoDto
   * 
   * @param producto
   * @return ProductoDto o null
   */
  public static ProductoDto toDto(Producto producto) {
    if (producto == null)
      return null;

    return new ProductoDto(
        producto.getIdProducto(),
        producto.getNombreProducto(),
        ProductosFamiliaMapper.toDto(producto.getFamilia()),
        producto.getPrecio());
  }

  /**
   * Convierte un objeto ProductoDto a Producto
   * 
   * @param dto
   * @return Producto o null
   */
  public static Producto toEntity(ProductoDto dto) {
    if (dto == null)
      return null;

    Producto producto = new Producto();
    ProductosFamilia familia = new ProductosFamilia();

    if (dto.getIdProducto() != null) {
      producto.setIdProducto(dto.getIdProducto());
    }

    producto.setNombreProducto(dto.getNombreProducto());
    familia.setIdFamilia(dto.getIdFamilia());
    producto.setFamilia(familia);
    producto.setPrecio(dto.getPrecio());

    return producto;
  }
}
