package cl.duoc.ms_jardin_ventas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class VentaRequestDTO {

    // Validamos que la lista no venga vacía y que valide cada elemento interno
    @NotEmpty(message = "La venta debe tener al menos un producto en el carrito")
    @Valid
    private List<DetalleVentaRequestDTO> detalles;
}