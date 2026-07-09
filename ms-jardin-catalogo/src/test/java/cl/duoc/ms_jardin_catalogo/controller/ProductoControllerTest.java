package cl.duoc.ms_jardin_catalogo.controller;

import cl.duoc.ms_jardin_catalogo.dto.ProductoRequestDTO;
import cl.duoc.ms_jardin_catalogo.dto.ProductoResponseDTO;
import cl.duoc.ms_jardin_catalogo.service.ProductoService;
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

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductoService service;

    @Test
    void dadoProductosExistentes_cuandoListar_entoncesRetorna200() throws Exception {
        ProductoResponseDTO producto = new ProductoResponseDTO();
        producto.setId(1L);
        producto.setNombre("Rosa");
        when(service.listarTodos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Rosa"));
    }

    @Test
    void dadoIdExistente_cuandoBuscar_entoncesRetorna200() throws Exception {
        ProductoResponseDTO producto = new ProductoResponseDTO();
        producto.setId(1L);
        producto.setNombre("Rosa");
        when(service.buscarPorId(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rosa"));
    }

    @Test
    void dadoIdInexistente_cuandoBuscar_entoncesRetorna404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new RuntimeException("Producto no encontrado"));

        mockMvc.perform(get("/api/v1/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void dadoProductoValido_cuandoCrear_entoncesRetorna201() throws Exception {
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Rosa");
        request.setCategoriaId(1L);
        request.setPrecio(5000);
        request.setStock(10);

        ProductoResponseDTO response = new ProductoResponseDTO();
        response.setId(1L);
        response.setNombre("Rosa");
        when(service.guardar(any(ProductoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/productos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void dadoIdExistente_cuandoActualizar_entoncesRetorna200() throws Exception {
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Rosa actualizada");
        request.setCategoriaId(1L);
        request.setPrecio(6000);
        request.setStock(5);

        ProductoResponseDTO response = new ProductoResponseDTO();
        response.setId(1L);
        response.setNombre("Rosa actualizada");
        when(service.actualizar(eq(1L), any(ProductoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/productos/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rosa actualizada"));
    }

    @Test
    void dadoIdExistente_cuandoEliminar_entoncesRetorna204() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    void dadoIdYCantidad_cuandoReducirStock_entoncesRetorna200() throws Exception {
        doNothing().when(service).reducirStock(1L, 2);

        mockMvc.perform(put("/api/v1/productos/1/stock").param("cantidad", "2"))
                .andExpect(status().isOk());

        verify(service, times(1)).reducirStock(1L, 2);
    }
}