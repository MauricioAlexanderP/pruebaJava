package mp.code.pruebajava.service;

import mp.code.pruebajava.dto.ProductosFamiliaDto;
import mp.code.pruebajava.model.ProductosFamilia;
import mp.code.pruebajava.repository.ProductoFamiliaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ProductoFamiliaService")
class ProductoFamiliaServiceTest {

  @Mock
  private ProductoFamiliaRepository productoFamiliaRepository;

  @InjectMocks
  private ProductoFamiliaService productoFamiliaService;

  private ProductosFamilia familia1;
  private ProductosFamilia familia2;
  private ProductosFamilia familia3;

  @BeforeEach
  void setUp() {
    familia1 = new ProductosFamilia();
    familia1.setIdFamilia(1L);
    familia1.setNombreFamilia("Electrónica");

    familia2 = new ProductosFamilia();
    familia2.setIdFamilia(2L);
    familia2.setNombreFamilia("Ropa");

    familia3 = new ProductosFamilia();
    familia3.setIdFamilia(3L);
    familia3.setNombreFamilia("Alimentos");
  }

  @Test
  @DisplayName("Debe obtener todas las familias de productos")
  void testGetAll_Exitoso() {
    // Given
    when(productoFamiliaRepository.findAll())
        .thenReturn(Arrays.asList(familia1, familia2, familia3));

    // When
    List<ProductosFamiliaDto> resultado = productoFamiliaService.getAll();

    // Then
    assertNotNull(resultado);
    assertEquals(3, resultado.size());
    assertEquals("Electrónica", resultado.get(0).getNombreFamilia());
    assertEquals("Ropa", resultado.get(1).getNombreFamilia());
    assertEquals("Alimentos", resultado.get(2).getNombreFamilia());
    verify(productoFamiliaRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Debe retornar lista vacía cuando no hay familias")
  void testGetAll_ListaVacia() {
    // Given
    when(productoFamiliaRepository.findAll()).thenReturn(Collections.emptyList());

    // When
    List<ProductosFamiliaDto> resultado = productoFamiliaService.getAll();

    // Then
    assertNotNull(resultado);
    assertTrue(resultado.isEmpty());
    assertEquals(0, resultado.size());
    verify(productoFamiliaRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Debe manejar correctamente una sola familia")
  void testGetAll_UnaFamilia() {
    // Given
    when(productoFamiliaRepository.findAll()).thenReturn(Arrays.asList(familia1));

    // When
    List<ProductosFamiliaDto> resultado = productoFamiliaService.getAll();

    // Then
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals(1L, resultado.get(0).getIdFamilia());
    assertEquals("Electrónica", resultado.get(0).getNombreFamilia());
    verify(productoFamiliaRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Debe retornar DTOs con los campos correctos mapeados")
  void testGetAll_MapeoCompleto() {
    // Given
    when(productoFamiliaRepository.findAll()).thenReturn(Arrays.asList(familia1));

    // When
    List<ProductosFamiliaDto> resultado = productoFamiliaService.getAll();

    // Then
    assertNotNull(resultado);
    assertEquals(1, resultado.size());

    ProductosFamiliaDto familiaDto = resultado.get(0);
    assertNotNull(familiaDto.getIdFamilia());
    assertNotNull(familiaDto.getNombreFamilia());
    assertEquals(familia1.getIdFamilia(), familiaDto.getIdFamilia());
    assertEquals(familia1.getNombreFamilia(), familiaDto.getNombreFamilia());

    verify(productoFamiliaRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Debe llamar al repositorio exactamente una vez")
  void testGetAll_VerificarLlamadaRepositorio() {
    // Given
    when(productoFamiliaRepository.findAll()).thenReturn(Arrays.asList(familia1, familia2));

    // When
    productoFamiliaService.getAll();

    // Then
    verify(productoFamiliaRepository, times(1)).findAll();
    verifyNoMoreInteractions(productoFamiliaRepository);
  }

  @Test
  @DisplayName("Debe manejar familias con nombres largos")
  void testGetAll_NombresLargos() {
    // Given
    ProductosFamilia familiaConNombreLargo = new ProductosFamilia();
    familiaConNombreLargo.setIdFamilia(4L);
    familiaConNombreLargo.setNombreFamilia("Electrónica y Tecnología de Consumo Avanzada");

    when(productoFamiliaRepository.findAll()).thenReturn(Arrays.asList(familiaConNombreLargo));

    // When
    List<ProductosFamiliaDto> resultado = productoFamiliaService.getAll();

    // Then
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Electrónica y Tecnología de Consumo Avanzada", resultado.get(0).getNombreFamilia());
    verify(productoFamiliaRepository, times(1)).findAll();
  }
}
