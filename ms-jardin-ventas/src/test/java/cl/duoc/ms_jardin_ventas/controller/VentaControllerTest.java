package cl.duoc.ms_jardin_ventas.controller;

import cl.duoc.ms_jardin_ventas.dto.DetalleVentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaRequestDTO;
import cl.duoc.ms_jardin_ventas.dto.VentaResponseDTO;
import cl.duoc.ms_jardin_ventas.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private VentaService ventaService;

    @Test
    void dadoVentasExistentes_cuandoListarTodas_entoncesRetorna200() throws Exception {
        VentaResponseDTO venta = new VentaResponseDTO();
        venta.setId(1L);
        venta.setPrecioTotal(18000.0);
        when(ventaService.listarTodas()).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void dadoIdExistente_cuandoBuscarPorId_entoncesRetorna200() throws Exception {
        VentaResponseDTO venta = new VentaResponseDTO();
        venta.setId(1L);
        venta.setPrecioTotal(18000.0);
        when(ventaService.buscarPorId(1L)).thenReturn(venta);

        mockMvc.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precioTotal").value(18000.0));
    }

    @Test
    void dadoIdInexistente_cuandoBuscarPorId_entoncesRetorna404() throws Exception {
        when(ventaService.buscarPorId(99L)).thenThrow(new RuntimeException("Error: Venta no encontrada"));

        mockMvc.perform(get("/api/v1/ventas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void dadoRequestValido_cuandoGuardar_entoncesRetorna201() throws Exception {
        DetalleVentaRequestDTO detalle = new DetalleVentaRequestDTO();
        detalle.setProductoId(1L);
        detalle.setCantidad(2);
        VentaRequestDTO request = new VentaRequestDTO();
        request.setDetalles(List.of(detalle));

        VentaResponseDTO response = new VentaResponseDTO();
        response.setId(1L);
        response.setPrecioTotal(18000.0);
        when(ventaService.guardarVenta(any(VentaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/ventas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void dadoIdYRequest_cuandoActualizar_entoncesRetorna400PorRegladeNegocio() throws Exception {
        DetalleVentaRequestDTO detalle = new DetalleVentaRequestDTO();
        detalle.setProductoId(1L);
        detalle.setCantidad(2);
        VentaRequestDTO request = new VentaRequestDTO();
        request.setDetalles(List.of(detalle));

        when(ventaService.actualizarVenta(eq(1L), any(VentaRequestDTO.class)))
                .thenThrow(new RuntimeException("Las ventas emitidas no se pueden modificar. Anule la venta (DELETE) y genere una nueva."));

        mockMvc.perform(put("/api/v1/ventas/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dadoIdExistente_cuandoEliminar_entoncesRetorna204() throws Exception {
        doNothing().when(ventaService).eliminarVenta(1L);

        mockMvc.perform(delete("/api/v1/ventas/1"))
                .andExpect(status().isNoContent());

        verify(ventaService, times(1)).eliminarVenta(1L);
    }
    @Test
    void dadoRequestSinDetalles_cuandoGuardar_entoncesRetorna400PorValidacion() throws Exception {
        VentaRequestDTO request = new VentaRequestDTO();
        request.setDetalles(List.of()); // Lista vacía -> viola @NotEmpty

        mockMvc.perform(post("/api/v1/ventas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}