package service.SupportServices.ExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex, HttpServletResponse response) {
        CustomStatus status = ex.getCustomStatus();
        response.setStatus(status.getStatusCode());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(status.getStatusCode()));
    }
}