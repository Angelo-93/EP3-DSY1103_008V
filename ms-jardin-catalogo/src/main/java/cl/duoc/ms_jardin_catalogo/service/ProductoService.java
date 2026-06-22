package cl.duoc.ms_jardin_catalogo.service;

import cl.duoc.ms_jardin_catalogo.dto.ProductoRequestDTO;
import cl.duoc.ms_jardin_catalogo.dto.ProductoResponseDTO;
import cl.duoc.ms_jardin_catalogo.model.Categoria;
import cl.duoc.ms_jardin_catalogo.model.Producto;
import cl.duoc.ms_jardin_catalogo.repository.CategoriaRepository;
import cl.duoc.ms_jardin_catalogo.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    public ProductoResponseDTO buscarPorId(Long id) {
        Producto producto = repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirAResponse(producto);
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        mapearDatosComunes(producto, request);
        producto.setCategoria(categoria);

        return convertirAResponse(repository.save(producto));
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request) {
        Producto producto = repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        mapearDatosComunes(producto, request);
        producto.setCategoria(categoria);

        return convertirAResponse(repository.save(producto));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public void reducirStock(Long id, Integer cantidadVendida) {
        log.info("Descontando {} unidades del producto ID: {}", cantidadVendida, id);
        Producto producto = repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidadVendida) {
            throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - cantidadVendida);
        repository.save(producto);
    }

    // Método auxiliar para no repetir código (Clean Code)
    private void mapearDatosComunes(Producto producto, ProductoRequestDTO request) {
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setVariedad(request.getVariedad());
        producto.setColor(request.getColor());
        producto.setMaterial(request.getMaterial());
        producto.setTamano(request.getTamano());
        producto.setFormato(request.getFormato());
    }

    private ProductoResponseDTO convertirAResponse(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setVariedad(producto.getVariedad());
        dto.setColor(producto.getColor());
        dto.setMaterial(producto.getMaterial());
        dto.setTamano(producto.getTamano());
        dto.setFormato(producto.getFormato());
        if (producto.getCategoria() != null) {
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }
        return dto;
    }
}