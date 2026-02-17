package mp.code.pruebajava.service;

import mp.code.pruebajava.dto.ProductosFamiliaDto;
import mp.code.pruebajava.mapper.ProductosFamiliaMapper;
import mp.code.pruebajava.repository.ProductoFamiliaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoFamiliaService {

  private final ProductoFamiliaRepository data;

  ProductoFamiliaService(ProductoFamiliaRepository data) {
    this.data = data;
  }

  public List<ProductosFamiliaDto> getAll(){
    return data.findAll()
        .stream()
        .map(ProductosFamiliaMapper::toDto)
        .collect(Collectors.toList());
  }

}
