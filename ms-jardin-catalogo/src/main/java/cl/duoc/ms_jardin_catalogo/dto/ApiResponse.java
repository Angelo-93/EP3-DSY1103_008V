package cl.duoc.ms_jardin_catalogo.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ApiResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private Map<String, String> errors; // Aquí guardaremos los errores específicos (ej: "cantidad": "Debe ser al menos 1")

    // Constructor para errores generales
    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Constructor para errores de validación (@Valid)
    public ApiResponse(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}