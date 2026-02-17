package mp.code.pruebajava.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public String handleGenericException(
      Exception ex,
      RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("errorMessage",
        "Ha ocurrido un error inesperado. Por favor, intente nuevamente");
    redirectAttributes.addFlashAttribute("errorType", "error");
    return "redirect:/productos";
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public String handleResourceNotFoundException(
      ResourceNotFoundException ex,
      RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
    redirectAttributes.addFlashAttribute("errorType", "error");
    return "redirect:/productos";
  }

}
