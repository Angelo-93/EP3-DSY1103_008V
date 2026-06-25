package cl.duoc.ms_jardin_ventas.service;

import cl.duoc.ms_jardin_ventas.client.CatalogoClient;
import cl.duoc.ms_jardin_ventas.dto.DetalleVentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaResponseDTO;
import cl.duoc.ms_jardin_ventas.model.Venta;
import cl.duoc.ms_jardin_ventas.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    // 1. LOS MOCKS (Doble de Acción)
    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private CatalogoClient catalogoClient; // Fingimos ser el Catálogo

    // 2. LA CLASE REAL A PROBAR
    @InjectMocks
    private VentaService ventaService;

    // 3. DATOS FALSOS
    private Venta ventaFalsa;
    private VentaRequestDTO requestFalso;

    @BeforeEach
    void setUp() {
        // Preparamos una boleta falsa en la BD
        ventaFalsa = new Venta();
        ventaFalsa.setId(1L);
        ventaFalsa.setPrecioTotal(18000.0);

        // Preparamos lo que enviaría el usuario en el Postman
        DetalleVentaRequestDTO detalle = new DetalleVentaRequestDTO();
        detalle.setProductoId(1L);
        detalle.setCantidad(2); // Quiere comprar 2 unidades

        requestFalso = new VentaRequestDTO();
        requestFalso.setDetalles(List.of(detalle));
    }

    // =================================================================
    // TEST 1: BUSCAR POR ID
    // =================================================================
    @Test
    void dadoUnId_cuandoBuscarPorId_entoncesRetornaVenta() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaFalsa));

        VentaResponseDTO resultado = ventaService.buscarPorId(1L);

        assertNotNull(resultado);
        verify(ventaRepository, times(1)).findById(1L);
    }

    // =================================================================
    // TEST 2: GUARDAR VENTA (EL CARRITO CON FEIGN)
    // =================================================================
    @Test
    void dadoRequestValido_cuandoGuardarVenta_entoncesProcesaCarritoYComunicaConCatalogo() {
        // GIVEN:
        // 1. Fingimos la respuesta del catálogo cuando nos preguntan por el producto 1
        Map<String, Object> productoFalsoMap = new HashMap<>();
        productoFalsoMap.put("stock", 10);
        productoFalsoMap.put("precio", 9000.0);
        when(catalogoClient.getProductoById(1L)).thenReturn(productoFalsoMap);

        // 2. Fingimos la llamada a reducirStock (como no devuelve nada, usamos doNothing)
        doNothing().when(catalogoClient).reducirStock(1L, 2);

        // 3. Fingimos que la BD guarda la venta
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaFalsa);

        // WHEN: Ejecutamos el método real
        VentaResponseDTO resultado = ventaService.guardarVenta(requestFalso);

        // THEN: Verificamos que se ejecutó todo en orden
        assertNotNull(resultado);
        assertEquals(18000.0, resultado.getPrecioTotal());

        // Verificamos que el teléfono (Feign) sí se usó
        verify(catalogoClient, times(1)).getProductoById(1L);
        verify(catalogoClient, times(1)).reducirStock(1L, 2);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }
    // =================================================================
    // TEST 3: ELIMINAR VENTA
    // =================================================================
    @Test
    void dadoUnId_cuandoEliminarVenta_entoncesEliminaDelRepositorio() {
        // Given
        Long id = 1L;
        when(ventaRepository.findById(id)).thenReturn(Optional.of(ventaFalsa));

        // When
        ventaService.eliminarVenta(id);

        // Then
        verify(ventaRepository, times(1)).delete(ventaFalsa);
    }
}