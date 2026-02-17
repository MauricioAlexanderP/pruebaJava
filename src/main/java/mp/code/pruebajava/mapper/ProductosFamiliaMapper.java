package mp.code.pruebajava.mapper;

import mp.code.pruebajava.dto.ProductosFamiliaDto;
import mp.code.pruebajava.model.ProductosFamilia;

public final class ProductosFamiliaMapper {

  private ProductosFamiliaMapper(){}

  /**
   * Convierte un objeto ProductosFamilia a ProductosFamiliaDto
   * @param familia
   * @return ProductosFamiliaDto o null
   */
  public static ProductosFamiliaDto toDto(ProductosFamilia familia){
    if (familia == null) return null;

    return new ProductosFamiliaDto(familia.getIdFamilia(), familia.getNombreFamilia());
  }

  /**
   * Convierte un objeto ProductosFamiliaDto a ProductosFamilia
   * @param dto
   * @return ProductosFamilia o null
   */
  public static ProductosFamilia toEntity(ProductosFamiliaDto dto){
    if (dto == null) return null;

    ProductosFamilia familia = new ProductosFamilia();

    familia.setIdFamilia(dto.getIdFamilia());
    familia.setNombreFamilia(dto.getNombreFamilia());

    return familia;
  }

}
