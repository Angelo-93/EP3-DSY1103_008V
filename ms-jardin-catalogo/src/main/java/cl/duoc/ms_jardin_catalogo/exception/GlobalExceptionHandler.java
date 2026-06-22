package cl.duoc.ms_jardin_catalogo.exception;

import cl.duoc.ms_jardin_catalogo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa los errores de validación de los DTOs (@NotNull, @Min, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Error en los datos enviados", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 2. Atrapa los errores de negocio (Ej: Stock insuficiente, Producto no encontrado)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        // Si el mensaje habla de "no encontrado", devolvemos 404, si no, 400 Bad Request
        HttpStatus status = ex.getMessage().toLowerCase().contains("no encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        ApiResponse response = new ApiResponse(status.value(), ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    // 3. Atrapa cualquier otro error crítico del servidor (El "Exception" general)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}