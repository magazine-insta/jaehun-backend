package pbl.magazine.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pbl.magazine.model.ErrorMessage;

@ControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleApiRequestException(Exception ex) {
        ex.printStackTrace();
        log.info(ex.toString());
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
