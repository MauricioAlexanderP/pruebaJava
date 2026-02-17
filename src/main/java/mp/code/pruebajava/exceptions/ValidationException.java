package mp.code.pruebajava.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{

  private String fieldName;
  private Object fieldValue;


  public ValidationException(String message){
    super(message);
  }
  public ValidationException(String message, Throwable cause){
    super(message, cause);
  }

  public ValidationException(String massage, String fieldName, Object rejectedValue){
    super(massage);
    this.fieldName = fieldName;
    this.fieldValue = rejectedValue;
  }
}
