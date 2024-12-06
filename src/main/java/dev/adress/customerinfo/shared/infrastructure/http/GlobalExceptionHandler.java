package dev.adress.customerinfo.shared.infrastructure.http;

import dev.adress.customerinfo.customer.domain.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<?>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomApiResponse.error("An unexpected error occurred: " + ex.getMessage(), null, null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.error(ex.getMessage(), null, null));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<CustomApiResponse<?>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomApiResponse.error(ex.errorMessage(), ex.errorCode(), null));
    }
}
