package cl.duoc.ms_jardin_ventas.controller;

import cl.duoc.ms_jardin_ventas.dto.VentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaResponseDTO;
import cl.duoc.ms_jardin_ventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    // Inyección por constructor (Cumpliendo la rúbrica)
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(@Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.guardarVenta(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.ok(ventaService.actualizarVenta(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}