package mp.code.pruebajava.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mp.code.pruebajava.dto.ProductoDto;
import mp.code.pruebajava.service.ProductoFamiliaService;
import mp.code.pruebajava.service.ProductoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

  private final ProductoService service;
  private final ProductoFamiliaService familiaService;

  @GetMapping("")
  public String getAll(
      @RequestParam(defaultValue = "0") int page,
      Model model,
      HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/?error=true";
    }

    Page<ProductoDto> productoPage = service.getAllPaginated(page, 10);

    model.addAttribute("productos", productoPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", productoPage.getTotalPages());
    model.addAttribute("totalItems", productoPage.getTotalElements());
    model.addAttribute("familias", familiaService.getAll());
    model.addAttribute("productoDto", new ProductoDto());
    return "producto/index";
  }

  @PostMapping("/save")
  public String save(
      @ModelAttribute("productoDto") ProductoDto producto,
      BindingResult result,
      RedirectAttributes redirect,
      HttpSession session) {

    if (session.getAttribute("usuario") == null) {
      return "redirect:/?error=true";
    }

    try {
      if (result.hasErrors()) {
        redirect.addFlashAttribute("errorMessage", "Verifique los datos ingresados");
        redirect.addFlashAttribute("errorType", "warning");
        return "redirect:/productos";
      }
      service.save(producto);
      redirect.addFlashAttribute("successMessage", "Producto guardado correctamente");
      redirect.addFlashAttribute("messageType", "success");
    } catch (Exception e) {
      redirect.addFlashAttribute("errorMessage", "Error al guardar: " + e.getMessage());
      redirect.addFlashAttribute("errorType", "error");
    }
    return "redirect:/productos";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes redirect, HttpSession session) {
    if (session.getAttribute("usuario") == null) {
      return "redirect:/?error=true";
    }

    try {
      service.delete(id);
      redirect.addFlashAttribute("successMessage", "Producto eliminado correctamente");
      redirect.addFlashAttribute("messageType", "success");
    } catch (Exception e) {
      redirect.addFlashAttribute("errorMessage", "Error al eliminar: " + e.getMessage());
      redirect.addFlashAttribute("errorType", "error");
    }
    return "redirect:/productos";
  }

}
