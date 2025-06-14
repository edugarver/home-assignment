package com.example.products.http;

import com.example.products.http.model.ErrorTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<ErrorTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("Caught {}, violations found: {}", ex, ex.getConstraintViolations());
        return ResponseEntity.badRequest().body(ErrorTO.from(ex));
    }

}
