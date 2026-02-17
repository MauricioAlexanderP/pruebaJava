package mp.code.pruebajava.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTOS")
public class Producto {
  @Id
  @Column(name = "ID_PRODUCTO", nullable = false, length = 11)
  private Long idProducto;

  @Column(name = "NOMBRE_PRODUCTO")
  private String nombreProducto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_FAMILIA")
  private ProductosFamilia familia;

  @Column(name = "PRECIO", precision = 10, scale = 2)
  private BigDecimal precio;

}