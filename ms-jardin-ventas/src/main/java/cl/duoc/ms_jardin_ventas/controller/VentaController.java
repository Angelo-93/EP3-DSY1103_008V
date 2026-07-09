package cl.duoc.ms_jardin_ventas.controller;

import cl.duoc.ms_jardin_ventas.dto.VentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaResponseDTO;
import cl.duoc.ms_jardin_ventas.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Gestión de ventas y su comunicación con el catálogo")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Operation(summary = "Listar todas las ventas", description = "Retorna el listado completo de ventas registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @Operation(summary = "Buscar venta por ID", description = "Retorna una venta específica según su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(@Parameter(description = "ID de la venta") @PathVariable Long id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    @Operation(summary = "Registrar venta", description = "Crea una nueva venta, valida stock y descuenta unidades en el microservicio de Catálogo vía Feign")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente o datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(@Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.guardarVenta(request));
    }

    @Operation(summary = "Actualizar venta", description = "No permitido por regla de negocio: las ventas emitidas no se modifican")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Operación no permitida", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @Parameter(description = "ID de la venta") @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.ok(ventaService.actualizarVenta(id, request));
    }

    @Operation(summary = "Eliminar venta", description = "Elimina una venta y todos sus detalles asociados (cascade)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la venta") @PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}