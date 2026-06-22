package cl.duoc.ms_jardin_ventas.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private String nombreVivero = "El Jardín del Ángel";
    private Long id;
    private Double precioTotal;
    private LocalDateTime fechaVenta;

    // Aquí mostramos la lista de cosas compradas
    private List<DetalleVentaResponseDTO> detalles;
}