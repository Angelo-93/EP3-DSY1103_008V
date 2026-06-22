package cl.duoc.ms_jardin_catalogo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    // --- OBLIGATORIOS ---
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    // --- OPCIONALES (Sin validación estricta) ---
    private String variedad;
    private String color;
    private String material;
    private String tamano;
    private String formato;
}