package cl.duoc.ms_jardin_ventas.service;

import cl.duoc.ms_jardin_ventas.client.CatalogoClient;
import cl.duoc.ms_jardin_ventas.dto.DetalleVentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.DetalleVentaResponseDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaResponseDTO;
import cl.duoc.ms_jardin_ventas.model.DetalleVenta;
import cl.duoc.ms_jardin_ventas.model.Venta;
import cl.duoc.ms_jardin_ventas.repository.VentaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaService.class);

    private final VentaRepository ventaRepository;
    private final CatalogoClient catalogoClient;

    public VentaService(VentaRepository ventaRepository, CatalogoClient catalogoClient) {
        this.ventaRepository = ventaRepository;
        this.catalogoClient = catalogoClient;
    }

    public List<VentaResponseDTO> listarTodas() {
        return ventaRepository.findAll().stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    public VentaResponseDTO buscarPorId(Long id) {
        Venta venta = ventaRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Venta no encontrada"));
        return convertirAResponse(venta);
    }

    // --- NUEVA LÓGICA DE CARRITO DE COMPRAS ---
    public VentaResponseDTO guardarVenta(VentaRequestDTO request) {
        log.info("Iniciando proceso de venta con múltiples productos");

        Venta venta = new Venta();
        double totalVenta = 0.0;

        // Recorremos cada producto del carrito
        for (DetalleVentaRequestDTO detalleReq : request.getDetalles()) {
            try {
                // 1. Consultamos el catálogo
                Map<String, Object> producto = catalogoClient.getProductoById(detalleReq.getProductoId());
                if (producto == null) throw new RuntimeException("Producto nulo.");

                // 2. Validamos el stock
                int stockDisponible = ((Number) producto.get("stock")).intValue();
                if (stockDisponible < detalleReq.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto ID " + detalleReq.getProductoId() + ". Quedan: " + stockDisponible);
                }

                // 3. Descontamos el stock en catálogo
                catalogoClient.reducirStock(detalleReq.getProductoId(), detalleReq.getCantidad());

                // 4. Calculamos subtotales
                Double precioUnitario = ((Number) producto.get("precio")).doubleValue();
                Double subTotal = precioUnitario * detalleReq.getCantidad();

                // 5. Armamos el renglón de la boleta
                DetalleVenta detalle = new DetalleVenta();
                detalle.setProductoId(detalleReq.getProductoId());
                detalle.setCantidad(detalleReq.getCantidad());
                detalle.setPrecioUnitario(precioUnitario);
                detalle.setSubTotal(subTotal);
                detalle.setVenta(venta); // ¡Relación Bidireccional! El renglón conoce a su boleta

                // Agregamos el renglón a la boleta y sumamos al total
                venta.getDetalles().add(detalle);
                totalVenta += subTotal;

            } catch (FeignException.NotFound e) {
                throw new RuntimeException("Error: Producto ID " + detalleReq.getProductoId() + " no existe.");
            }
        }

        venta.setPrecioTotal(totalVenta);
        Venta ventaGuardada = ventaRepository.save(venta);
        log.info("Venta de carrito exitosa. ID: {}", ventaGuardada.getId());
        return convertirAResponse(ventaGuardada);
    }

    public void eliminarVenta(Long id) {
        Venta venta = ventaRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Venta no encontrada"));
        // Gracias al CascadeType.ALL, al borrar la venta, se borran todos sus detalles automáticamente
        ventaRepository.delete(venta);
    }

    public VentaResponseDTO actualizarVenta(Long id, VentaRequestDTO request) {
        // En un sistema real, actualizar un carrito implica reversar stock y recalcular.
        // Por simplicidad del MVP, mantenemos la firma pero solicitamos crear una venta nueva.
        throw new RuntimeException("Las ventas emitidas no se pueden modificar. Anule la venta (DELETE) y genere una nueva.");
    }

    // --- MAPEO DE RESPUESTA CON LISTAS ---
    private VentaResponseDTO convertirAResponse(Venta venta) {
        VentaResponseDTO response = new VentaResponseDTO();
        response.setId(venta.getId());
        response.setPrecioTotal(venta.getPrecioTotal());
        response.setFechaVenta(venta.getFechaVenta());

        List<DetalleVentaResponseDTO> detallesDTO = venta.getDetalles().stream().map(d -> {
            DetalleVentaResponseDTO dto = new DetalleVentaResponseDTO();
            dto.setProductoId(d.getProductoId());
            dto.setCantidad(d.getCantidad());
            dto.setPrecioUnitario(d.getPrecioUnitario());
            dto.setSubTotal(d.getSubTotal());
            return dto;
        }).collect(Collectors.toList());

        response.setDetalles(detallesDTO);
        return response;
    }
}