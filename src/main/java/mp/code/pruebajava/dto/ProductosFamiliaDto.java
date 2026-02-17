package mp.code.pruebajava.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductosFamiliaDto {
  private Long idFamilia;
  private String nombreFamilia;

  public ProductosFamiliaDto(){}

  public ProductosFamiliaDto(Long idFamilia, String nombreFamilia) {
    this.idFamilia = idFamilia;
    this.nombreFamilia = nombreFamilia;
  }

}
