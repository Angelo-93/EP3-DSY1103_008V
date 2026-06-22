package cl.duoc.ms_jardin_catalogo.service;

import cl.duoc.ms_jardin_catalogo.dto.ProductoRequestDTO;
import cl.duoc.ms_jardin_catalogo.dto.ProductoResponseDTO;
import cl.duoc.ms_jardin_catalogo.model.Categoria;
import cl.duoc.ms_jardin_catalogo.model.Producto;
import cl.duoc.ms_jardin_catalogo.repository.CategoriaRepository;
import cl.duoc.ms_jardin_catalogo.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    // --- 1. NUESTROS ACTORES FALSOS (MOCKS) ---
    @Mock
    private ProductoRepository repository;

    @Mock
    private CategoriaRepository categoriaRepository; // <- NUEVO CÓMPLICE

    // --- 2. NUESTRO CÓDIGO REAL A PROBAR ---
    @InjectMocks
    private ProductoService productoService;

    // --- 3. NUESTROS DATOS DE MENTIRA ---
    private Producto productoFalso;
    private Categoria categoriaFalsa;
    private ProductoRequestDTO requestDTOFalso;

    @BeforeEach
    void setUp() {
        // Preparamos la Categoría de mentira
        categoriaFalsa = new Categoria();
        categoriaFalsa.setId(1L);
        categoriaFalsa.setNombre("Insumos Premium");

        // Preparamos el Producto de mentira y le ponemos la categoría
        productoFalso = new Producto();
        productoFalso.setId(1L);
        productoFalso.setNombre("Saco de Tierra");
        productoFalso.setStock(10);
        productoFalso.setPrecio(5000);
        productoFalso.setCategoria(categoriaFalsa);

        // Preparamos lo que nos mandaría Postman (RequestDTO)
        requestDTOFalso = new ProductoRequestDTO();
        requestDTOFalso.setNombre("Saco de Tierra");
        requestDTOFalso.setStock(10);
        requestDTOFalso.setPrecio(5000);
        requestDTOFalso.setCategoriaId(1L);
    }

    // =================================================================
    // TEST 1: BUSCAR POR ID (El que ya teníamos)
    // =================================================================
    @Test
    void dadoUnIdValido_cuandoBuscarPorId_entoncesRetornaProductoDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(productoFalso));

        ProductoResponseDTO resultado = productoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Saco de Tierra", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    // =================================================================
    // TEST 2: LISTAR TODOS
    // =================================================================
    @Test
    void cuandoListarTodos_entoncesRetornaListaDeProductos() {
        // GIVEN: El mock del repositorio devuelve una lista con 1 producto
        when(repository.findAll()).thenReturn(List.of(productoFalso));

        // WHEN: Llamamos al método real
        List<ProductoResponseDTO> resultado = productoService.listarTodos();

        // THEN: Verificamos que la lista no esté vacía y tenga tamaño 1
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // =================================================================
    // TEST 3: GUARDAR PRODUCTO
    // =================================================================
    @Test
    void dadoUnRequestValido_cuandoGuardar_entoncesRetornaProductoCreado() {
        // GIVEN: Aquí tenemos 2 pasos.
        // 1. Fingir que encontramos la categoría
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaFalsa));
        // 2. Fingir que guardamos cualquier producto (El cheque en blanco)
        when(repository.save(any(Producto.class))).thenReturn(productoFalso);

        // WHEN: Llamamos al método guardar con nuestro RequestDTO falso
        ProductoResponseDTO resultado = productoService.guardar(requestDTOFalso);

        // THEN: Validamos que nos devuelve el producto armado y que llamó a ambos repositorios
        assertNotNull(resultado);
        assertEquals("Saco de Tierra", resultado.getNombre());
        assertEquals("Insumos Premium", resultado.getCategoriaNombre());

        verify(categoriaRepository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Producto.class));
    }
    // =================================================================
    // TEST 4: ACTUALIZAR PRODUCTO
    // =================================================================
    @Test
    void dadoUnIdYRequestValido_cuandoActualizar_entoncesRetornaProductoActualizado() {
        // GIVEN:
        // 1. Fingimos encontrar el producto viejo
        when(repository.findById(1L)).thenReturn(Optional.of(productoFalso));
        // 2. Fingimos encontrar la categoría
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaFalsa));
        // 3. Fingimos que al guardar, devuelve la planta con los datos actualizados
        when(repository.save(any(Producto.class))).thenReturn(productoFalso);

        // WHEN: Llamamos a tu método real
        ProductoResponseDTO resultado = productoService.actualizar(1L, requestDTOFalso);

        // THEN: Verificamos que todo se ejecutó en orden
        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Producto.class));
    }

    // =================================================================
    // TEST 5: ELIMINAR PRODUCTO
    // =================================================================
    @Test
    void dadoUnIdValido_cuandoEliminar_entoncesLlamaAlRepositorio() {
        // GIVEN: El método deleteById no devuelve nada (es 'void'),
        // así que con Mockito usamos "doNothing()"
        doNothing().when(repository).deleteById(1L);

        // WHEN: Llamamos a tu método eliminar
        productoService.eliminar(1L);

        // THEN: Verificamos que tu servicio efectivamente le pidió al repo que lo borrara
        verify(repository, times(1)).deleteById(1L);
    }

    // =================================================================
    // TEST 6: REDUCIR STOCK
    // =================================================================
    @Test
    void dadoIdYCantidadValida_cuandoReducirStock_entoncesDisminuyeStockYGuarda() {
        // GIVEN: Fingimos que el repositorio encuentra nuestra planta falsa (que tiene stock 10)
        when(repository.findById(1L)).thenReturn(Optional.of(productoFalso));

        // WHEN: Ejecutamos tu método real pidiendo que descuente 2 unidades
        productoService.reducirStock(1L, 2);

        // THEN: Afirmamos que la planta quedó con 8 de stock, y verificamos que se guardó
        assertEquals(8, productoFalso.getStock());
        verify(repository, times(1)).save(productoFalso);
    }
}