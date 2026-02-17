package mp.code.pruebajava.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoDto {
  private Long idProducto;
  private String nombreProducto;
  private Long idFamilia;
  private ProductosFamiliaDto familia;
  private BigDecimal precio;

  public ProductoDto(){}

  public ProductoDto(Long idProducto,
                     String nombreProducto,
                     ProductosFamiliaDto familia ,
                     BigDecimal precio) {
    this.idProducto = idProducto;
    this.nombreProducto = nombreProducto;
    this.familia = familia;
    this.idFamilia = familia != null ? familia.getIdFamilia() : null;
    this.precio = precio;
  }
}
