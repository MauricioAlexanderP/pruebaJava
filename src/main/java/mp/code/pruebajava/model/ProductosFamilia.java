package mp.code.pruebajava.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTOS_FAMILIAS")
public class ProductosFamilia {
  @Id
  @Column(name = "ID_FAMILIA", nullable = false, length = 11)
  private Long idFamilia; // Integer stores only 10 digits

  @Column(name = "NOMBRE_FAMILIA")
  private String nombreFamilia;

  @OneToMany(mappedBy = "familia")
  private List<Producto> productos = new ArrayList<>();

}