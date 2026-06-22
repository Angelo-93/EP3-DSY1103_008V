package cl.duoc.ms_jardin_catalogo.config;

import cl.duoc.ms_jardin_catalogo.model.Categoria;
import cl.duoc.ms_jardin_catalogo.model.Producto;
import cl.duoc.ms_jardin_catalogo.repository.CategoriaRepository;
import cl.duoc.ms_jardin_catalogo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        return args -> {
            if (categoriaRepository.count() == 0) {

                // 1. Crear Categorías
                Categoria catFlores = new Categoria();
                catFlores.setNombre("Flores y Rosas");
                categoriaRepository.save(catFlores);

                Categoria catInsumos = new Categoria();
                catInsumos.setNombre("Tierra e Insumos");
                categoriaRepository.save(catInsumos);

                Categoria catAccesorios = new Categoria();
                catAccesorios.setNombre("Maceteros y Accesorios");
                categoriaRepository.save(catAccesorios);

                // 2. Crear Productos (Usando método ayudante abajo)

                // Categoría Flores (catFlores)
                saveProducto(productoRepository, "Rosa Darcey Bussell", 9000, 20, catFlores);
                saveProducto(productoRepository, "Rosa Chrysler Imperial (Roja)", 8500, 25, catFlores);
                saveProducto(productoRepository, "Rosa Iceberg (Blanca)", 7500, 30, catFlores);
                saveProducto(productoRepository, "Rosa Pascali (Blanca)", 8000, 15, catFlores);
                saveProducto(productoRepository, "Rosa Queen Elizabeth (Rosada)", 9000, 10, catFlores);
                saveProducto(productoRepository, "Rosa Double Delight (Bicolor)", 9500, 12, catFlores);
                saveProducto(productoRepository, "Rosa Just Joey (Damasco)", 8500, 18, catFlores);
                saveProducto(productoRepository, "Rosa Mister Lincoln (Roja)", 10000, 8, catFlores);
                saveProducto(productoRepository, "Rosa Peace (Amarilla/Rosado)", 9500, 20, catFlores);
                saveProducto(productoRepository, "Rosa Graham Thomas (Amarilla)", 11000, 5, catFlores);
                saveProducto(productoRepository, "Rosa Blue Moon (Lavanda)", 12000, 7, catFlores);
                saveProducto(productoRepository, "Rosa Lady Banks (Amarilla Pequeña)", 8000, 14, catFlores);
                saveProducto(productoRepository, "Rosa Julio Iglesias (Rayada Roja/Blanca)", 13000, 6, catFlores);

                // Categoría Insumos (catInsumos)
                saveProducto(productoRepository, "Tierra de Hoja 5Kg", 3500, 50, catInsumos);
                saveProducto(productoRepository, "Tierra de Hoja 10Kg", 6500, 40, catInsumos);
                saveProducto(productoRepository, "Humus de Lombriz 2Kg", 4500, 25, catInsumos);
                saveProducto(productoRepository, "Sustrato para Rosas 5Kg", 5500, 20, catInsumos);
                saveProducto(productoRepository, "Abono Orgánico 1Kg", 3000, 50, catInsumos);
                saveProducto(productoRepository, "Perlita para Drenaje 5L", 4000, 15, catInsumos);
                saveProducto(productoRepository, "Turba Rubia 10L", 7000, 12, catInsumos);
                saveProducto(productoRepository, "Fertilizante Floración 1L", 9500, 10, catInsumos);

                // Categoría Accesorios (catAccesorios)
                saveProducto(productoRepository, "Macetero de Greda Grande", 12000, 15, catAccesorios);
                saveProducto(productoRepository, "Macetero de Greda Mediano", 6000, 20, catAccesorios);
                saveProducto(productoRepository, "Macetero de Greda Pequeño", 3500, 35, catAccesorios);
                saveProducto(productoRepository, "Macetero Cerámica Esmaltada Azul", 15000, 5, catAccesorios);
                saveProducto(productoRepository, "Macetero Plástico Café 20cm", 1500, 100, catAccesorios);
                saveProducto(productoRepository, "Macetero Colgante Fibra Coco", 8500, 12, catAccesorios);
                saveProducto(productoRepository, "Tijera de Poda Profesional", 12500, 10, catAccesorios);
                saveProducto(productoRepository, "Regadera Zinc 5L", 18000, 4, catAccesorios);
                saveProducto(productoRepository, "Guantes de Jardinería", 5500, 15, catAccesorios);
                saveProducto(productoRepository, "Set de Herramientas (3 piezas)", 9000, 8, catAccesorios);
            }
        };
    }

    // Método ayudante para no repetir código
    private void saveProducto(ProductoRepository repo, String nombre, int precio, int stock, Categoria cat) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setCategoria(cat);
        repo.save(p);
    }
}