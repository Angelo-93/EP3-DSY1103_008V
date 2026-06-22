package cl.duoc.ms_jardin_ventas.dto;

import lombok.Data;

@Data
public class DetalleVentaResponseDTO {
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;
}