package mp.code.pruebajava.validators;

import mp.code.pruebajava.exceptions.ValidationException;

import java.math.BigDecimal;

public class CommonValidator {

  public static void validateNotNull(Object value, String fieldName) {
    if (value == null) {
      throw new ValidationException(
          fieldName + " no puede ser nulo",
          fieldName,
          null);
    }
  }

  public static void validateNotEmpty(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new ValidationException(
          fieldName + " no puede estar vacío",
          fieldName,
          value);
    }
  }

  public static void validatePositiveNumber(BigDecimal value, String fieldName) {
    validateNotNull(value, fieldName);
    if (value.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidationException(
          fieldName + " debe ser mayor a 0.0",
          fieldName,
          value);
    }
  }
}
