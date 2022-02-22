package pbl.magazine.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pbl.magazine.model.ErrorMessage;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleApiRequestException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
