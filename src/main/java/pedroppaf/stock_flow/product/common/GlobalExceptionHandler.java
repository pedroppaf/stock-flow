package pedroppaf.stock_flow.product.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pedroppaf.stock_flow.product.exception.ProductAlreadyExistsException;
import pedroppaf.stock_flow.product.exception.ProductNotFoundException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerProductNotFound(ProductNotFoundException ex, HttpServletRequest req){
        ErrorResponse body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "Not Found", ex.getMessage(), req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerProductAlreadyExists(ProductAlreadyExistsException ex, HttpServletRequest req){
        ErrorResponse body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.CONFLICT.value(),
                "Conflict", ex.getMessage(), req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handlerDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest req){
        ErrorResponse body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.CONFLICT.value(),
                "Data Integrity Violation", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage(), req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        ErrorResponse body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Validation Failed", "Request validation failed", req.getRequestURI(), fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(OffsetDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", ex.getMessage(), req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
