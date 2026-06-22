package cl.duoc.ms_jardin_catalogo.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private Integer precio;
    private Integer stock;
    private String categoriaNombre;

    // Atributos específicos
    private String variedad;
    private String color;
    private String material;
    private String tamano;
    private String formato;
}