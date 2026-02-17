package mp.code.pruebajava.service;

import mp.code.pruebajava.dto.ProductoDto;
import mp.code.pruebajava.dto.ProductosFamiliaDto;
import mp.code.pruebajava.exceptions.ResourceNotFoundException;
import mp.code.pruebajava.exceptions.ValidationException;
import mp.code.pruebajava.model.Producto;
import mp.code.pruebajava.model.ProductosFamilia;
import mp.code.pruebajava.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ProductoService")
class ProductoServiceTest {

  @Mock
  private ProductoRepository productoRepository;

  @InjectMocks
  private ProductoService productoService;

  private Producto producto1;
  private Producto producto2;
  private ProductosFamilia familia;
  private ProductoDto productoDto;

  @BeforeEach
  void setUp() {
    familia = new ProductosFamilia();
    familia.setIdFamilia(1L);
    familia.setNombreFamilia("Electrónica");

    producto1 = new Producto();
    producto1.setIdProducto(1L);
    producto1.setNombreProducto("Laptop");
    producto1.setFamilia(familia);
    producto1.setPrecio(new BigDecimal("1500.00"));

    producto2 = new Producto();
    producto2.setIdProducto(2L);
    producto2.setNombreProducto("Mouse");
    producto2.setFamilia(familia);
    producto2.setPrecio(new BigDecimal("25.00"));

    ProductosFamiliaDto familiaDto = new ProductosFamiliaDto(1L, "Electrónica");
    productoDto = new ProductoDto();
    productoDto.setIdProducto(1L);
    productoDto.setNombreProducto("Laptop");
    productoDto.setIdFamilia(1L);
    productoDto.setFamilia(familiaDto);
    productoDto.setPrecio(new BigDecimal("1500.00"));
  }

  @Test
  @DisplayName("Debe obtener todos los productos")
  void testGetAll_Exitoso() {
    // Given
    when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

    // When
    List<ProductoDto> resultado = productoService.getAll();

    // Then
    assertNotNull(resultado);
    assertEquals(2, resultado.size());
    assertEquals("Laptop", resultado.get(0).getNombreProducto());
    assertEquals("Mouse", resultado.get(1).getNombreProducto());
    verify(productoRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Debe retornar lista vacía cuando no hay productos")
  void testGetAll_ListaVacia() {
    // Given
    when(productoRepository.findAll()).thenReturn(Arrays.asList());

    // When
    List<ProductoDto> resultado = productoService.getAll();

    // Then
    assertNotNull(resultado);
    assertTrue(resultado.isEmpty());
    verify(productoRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Debe guardar un nuevo producto con ID generado automáticamente")
  void testSave_NuevoProducto() {
    // Given
    ProductoDto nuevoProducto = new ProductoDto();
    nuevoProducto.setNombreProducto("Teclado");
    nuevoProducto.setIdFamilia(1L);
    nuevoProducto.setPrecio(new BigDecimal("50.00"));

    when(productoRepository.findMaxIdProducto()).thenReturn(Optional.of(2L));
    when(productoRepository.save(any(Producto.class))).thenReturn(producto1);

    // When
    productoService.save(nuevoProducto);

    // Then
    assertEquals(3L, nuevoProducto.getIdProducto());
    verify(productoRepository, times(1)).findMaxIdProducto();
    verify(productoRepository, times(1)).save(any(Producto.class));
  }

  @Test
  @DisplayName("Debe guardar producto con ID existente")
  void testSave_ProductoExistente() {
    // Given
    when(productoRepository.save(any(Producto.class))).thenReturn(producto1);

    // When
    productoService.save(productoDto);

    // Then
    verify(productoRepository, times(1)).save(any(Producto.class));
    verify(productoRepository, never()).findMaxIdProducto();
  }

  @Test
  @DisplayName("Debe lanzar excepción al guardar con nombre vacío")
  void testSave_NombreVacio() {
    // Given
    ProductoDto productoInvalido = new ProductoDto();
    productoInvalido.setNombreProducto("");
    productoInvalido.setIdFamilia(1L);
    productoInvalido.setPrecio(new BigDecimal("50.00"));

    // When & Then
    assertThrows(ValidationException.class, () -> {
      productoService.save(productoInvalido);
    });
    verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  @DisplayName("Debe lanzar excepción al guardar sin familia")
  void testSave_FamiliaNull() {
    // Given
    ProductoDto productoInvalido = new ProductoDto();
    productoInvalido.setNombreProducto("Producto");
    productoInvalido.setIdFamilia(null);
    productoInvalido.setPrecio(new BigDecimal("50.00"));

    // When & Then
    assertThrows(ValidationException.class, () -> {
      productoService.save(productoInvalido);
    });
    verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  @DisplayName("Debe lanzar excepción al guardar sin precio")
  void testSave_PrecioNull() {
    // Given
    ProductoDto productoInvalido = new ProductoDto();
    productoInvalido.setNombreProducto("Producto");
    productoInvalido.setIdFamilia(1L);
    productoInvalido.setPrecio(null);

    // When & Then
    assertThrows(ValidationException.class, () -> {
      productoService.save(productoInvalido);
    });
    verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  @DisplayName("Debe generar ID cuando es el primer producto")
  void testSave_PrimerProducto() {
    // Given
    ProductoDto nuevoProducto = new ProductoDto();
    nuevoProducto.setNombreProducto("Teclado");
    nuevoProducto.setIdFamilia(1L);
    nuevoProducto.setPrecio(new BigDecimal("50.00"));

    when(productoRepository.findMaxIdProducto()).thenReturn(Optional.empty());
    when(productoRepository.save(any(Producto.class))).thenReturn(producto1);

    // When
    productoService.save(nuevoProducto);

    // Then
    assertEquals(1L, nuevoProducto.getIdProducto());
    verify(productoRepository, times(1)).findMaxIdProducto();
    verify(productoRepository, times(1)).save(any(Producto.class));
  }

  @Test
  @DisplayName("Debe eliminar producto existente")
  void testDelete_Exitoso() {
    // Given
    when(productoRepository.existsById(1L)).thenReturn(true);
    doNothing().when(productoRepository).deleteById(1L);

    // When
    productoService.delete(1L);

    // Then
    verify(productoRepository, times(1)).existsById(1L);
    verify(productoRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Debe lanzar excepción al eliminar producto inexistente")
  void testDelete_ProductoNoExiste() {
    // Given
    when(productoRepository.existsById(999L)).thenReturn(false);

    // When & Then
    assertThrows(ResourceNotFoundException.class, () -> {
      productoService.delete(999L);
    });
    verify(productoRepository, times(1)).existsById(999L);
    verify(productoRepository, never()).deleteById(999L);
  }

  @Test
  @DisplayName("Debe lanzar excepción al eliminar con ID nulo")
  void testDelete_IdNull() {
    // When & Then
    assertThrows(ValidationException.class, () -> {
      productoService.delete(null);
    });
    verify(productoRepository, never()).existsById(any());
    verify(productoRepository, never()).deleteById(any());
  }

  @Test
  @DisplayName("Debe lanzar excepción cuando se alcanza el límite máximo de IDs")
  void testSave_LimiteMaximoAlcanzado() {
    // Given
    ProductoDto nuevoProducto = new ProductoDto();
    nuevoProducto.setNombreProducto("Producto");
    nuevoProducto.setIdFamilia(1L);
    nuevoProducto.setPrecio(new BigDecimal("50.00"));

    when(productoRepository.findMaxIdProducto()).thenReturn(Optional.of(99999999999L));

    // When & Then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productoService.save(nuevoProducto);
    });
    assertTrue(exception.getMessage().contains("límite máximo"));
    verify(productoRepository, times(1)).findMaxIdProducto();
    verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  @DisplayName("Debe obtener productos paginados correctamente")
  void testGetAllPaginated_Exitoso() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    Page<Producto> productosPage = new PageImpl<>(Arrays.asList(producto1, producto2), pageable, 2);

    when(productoRepository.findAll(any(Pageable.class))).thenReturn(productosPage);

    // When
    Page<ProductoDto> resultado = productoService.getAllPaginated(0, 10);

    // Then
    assertNotNull(resultado);
    assertEquals(2, resultado.getContent().size());
    assertEquals(2, resultado.getTotalElements());
    assertEquals(1, resultado.getTotalPages());
    assertEquals("Laptop", resultado.getContent().get(0).getNombreProducto());
    assertEquals("Mouse", resultado.getContent().get(1).getNombreProducto());
    verify(productoRepository, times(1)).findAll(any(Pageable.class));
  }

  @Test
  @DisplayName("Debe obtener página vacía cuando no hay productos")
  void testGetAllPaginated_PaginaVacia() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    Page<Producto> productosPage = new PageImpl<>(Arrays.asList(), pageable, 0);

    when(productoRepository.findAll(any(Pageable.class))).thenReturn(productosPage);

    // When
    Page<ProductoDto> resultado = productoService.getAllPaginated(0, 10);

    // Then
    assertNotNull(resultado);
    assertTrue(resultado.getContent().isEmpty());
    assertEquals(0, resultado.getTotalElements());
    assertEquals(0, resultado.getTotalPages());
    verify(productoRepository, times(1)).findAll(any(Pageable.class));
  }

  @Test
  @DisplayName("Debe manejar correctamente múltiples páginas")
  void testGetAllPaginated_MultiplesPaginas() {
    // Given
    Pageable pageable = PageRequest.of(1, 1);
    Page<Producto> productosPage = new PageImpl<>(Arrays.asList(producto2), pageable, 2);

    when(productoRepository.findAll(any(Pageable.class))).thenReturn(productosPage);

    // When
    Page<ProductoDto> resultado = productoService.getAllPaginated(1, 1);

    // Then
    assertNotNull(resultado);
    assertEquals(1, resultado.getContent().size());
    assertEquals(2, resultado.getTotalElements());
    assertEquals(2, resultado.getTotalPages());
    assertEquals("Mouse", resultado.getContent().get(0).getNombreProducto());
    verify(productoRepository, times(1)).findAll(any(Pageable.class));
  }

  @Test
  @DisplayName("Debe aplicar ordenamiento por nombre de producto")
  void testGetAllPaginated_ConOrdenamiento() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    Page<Producto> productosPage = new PageImpl<>(Arrays.asList(producto1, producto2), pageable, 2);

    when(productoRepository.findAll(any(Pageable.class))).thenReturn(productosPage);

    // When
    Page<ProductoDto> resultado = productoService.getAllPaginated(0, 10);

    // Then
    assertNotNull(resultado);
    verify(productoRepository, times(1)).findAll(any(Pageable.class));
  }
}
