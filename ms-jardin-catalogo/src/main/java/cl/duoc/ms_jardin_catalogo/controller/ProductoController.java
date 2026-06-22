package cl.duoc.ms_jardin_catalogo.controller;

import cl.duoc.ms_jardin_catalogo.dto.ProductoRequestDTO;
import cl.duoc.ms_jardin_catalogo.dto.ProductoResponseDTO;
import cl.duoc.ms_jardin_catalogo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO request) {
        return new ResponseEntity<>(service.guardar(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --- NUEVO: ENDPOINT EXCLUSIVO PARA QUE LO CONSUMA VENTAS ---
    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        service.reducirStock(id, cantidad);
        return ResponseEntity.ok().build();
    }
}