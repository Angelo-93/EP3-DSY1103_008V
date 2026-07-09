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
                catFlores.setNombre("Rosas");
                categoriaRepository.save(catFlores);

                Categoria catInsumos = new Categoria();
                catInsumos.setNombre("Tierra e Insumos");
                categoriaRepository.save(catInsumos);

                Categoria catAccesorios = new Categoria();
                catAccesorios.setNombre("Maceteros y Accesorios");
                categoriaRepository.save(catAccesorios);

                // 2. Crear Productos

                // Categoría Rosas: se usa el atributo "color" (viene indicado en el nombre)
                saveProducto(productoRepository, "Rosa Darcey Bussell", 9000, 20, catFlores);
                saveProductoConColor(productoRepository, "Rosa Chrysler Imperial (Roja)", 8500, 25, catFlores, "Roja");
                saveProductoConColor(productoRepository, "Rosa Iceberg (Blanca)", 7500, 30, catFlores, "Blanca");
                saveProductoConColor(productoRepository, "Rosa Pascali (Blanca)", 8000, 15, catFlores, "Blanca");
                saveProductoConColor(productoRepository, "Rosa Queen Elizabeth (Rosada)", 9000, 10, catFlores, "Rosada");
                saveProductoConColor(productoRepository, "Rosa Double Delight (Bicolor)", 9500, 12, catFlores, "Bicolor");
                saveProductoConColor(productoRepository, "Rosa Just Joey (Damasco)", 8500, 18, catFlores, "Damasco");
                saveProductoConColor(productoRepository, "Rosa Mister Lincoln (Roja)", 10000, 8, catFlores, "Roja");
                saveProductoConColor(productoRepository, "Rosa Peace (Amarilla/Rosado)", 9500, 20, catFlores, "Amarilla/Rosado");
                saveProductoConColor(productoRepository, "Rosa Graham Thomas (Amarilla)", 11000, 5, catFlores, "Amarilla");
                saveProductoConColor(productoRepository, "Rosa Blue Moon (Lavanda)", 12000, 7, catFlores, "Lavanda");
                saveProducto(productoRepository, "Rosa Lady Banks (Amarilla Pequeña)", 8000, 14, catFlores, "Amarilla", null, null, "Pequeña", null);
                saveProductoConColor(productoRepository, "Rosa Julio Iglesias (Rayada Roja/Blanca)", 13000, 6, catFlores, "Rayada Roja/Blanca");

                // Categoría Insumos: son productos a granel, no aplican atributos específicos
                saveProducto(productoRepository, "Tierra de Hoja 5Kg", 3500, 50, catInsumos);
                saveProducto(productoRepository, "Tierra de Hoja 10Kg", 6500, 40, catInsumos);
                saveProducto(productoRepository, "Humus de Lombriz 2Kg", 4500, 25, catInsumos);
                saveProducto(productoRepository, "Sustrato para Rosas 5Kg", 5500, 20, catInsumos);
                saveProducto(productoRepository, "Abono Orgánico 1Kg", 3000, 50, catInsumos);
                saveProducto(productoRepository, "Perlita para Drenaje 5L", 4000, 15, catInsumos);
                saveProducto(productoRepository, "Turba Rubia 10L", 7000, 12, catInsumos);
                saveProducto(productoRepository, "Fertilizante Floración 1L", 9500, 10, catInsumos);

                // Categoría Accesorios: se usan "material" y "tamano" cuando corresponde
                saveProducto(productoRepository, "Macetero de Greda Grande", 12000, 15, catAccesorios, null, null, "Greda", "Grande", null);
                saveProducto(productoRepository, "Macetero de Greda Mediano", 6000, 20, catAccesorios, null, null, "Greda", "Mediano", null);
                saveProducto(productoRepository, "Macetero de Greda Pequeño", 3500, 35, catAccesorios, null, null, "Greda", "Pequeño", null);
                saveProducto(productoRepository, "Macetero Cerámica Esmaltada Azul", 15000, 5, catAccesorios, "Azul", null, "Cerámica", null, null);
                saveProducto(productoRepository, "Macetero Plástico Café 20cm", 1500, 100, catAccesorios, "Café", null, "Plástico", "20cm", null);
                saveProducto(productoRepository, "Macetero Colgante Fibra Coco", 8500, 12, catAccesorios, null, "Colgante", "Fibra de Coco", null, null);
                saveProducto(productoRepository, "Tijera de Poda Profesional", 12500, 10, catAccesorios);
                saveProducto(productoRepository, "Regadera Zinc 5L", 18000, 4, catAccesorios, null, null, "Zinc", "5L", null);
                saveProducto(productoRepository, "Guantes de Jardinería", 5500, 15, catAccesorios);
                saveProducto(productoRepository, "Set de Herramientas (3 piezas)", 9000, 8, catAccesorios);
            }
        };
    }

    // Método ayudante base (sin atributos específicos opcionales)
    private void saveProducto(ProductoRepository repo, String nombre, int precio, int stock, Categoria cat) {
        saveProducto(repo, nombre, precio, stock, cat, null, null, null, null, null);
    }

    // Método ayudante con color únicamente (usado en rosas)
    private void saveProductoConColor(ProductoRepository repo, String nombre, int precio, int stock, Categoria cat, String color) {
        saveProducto(repo, nombre, precio, stock, cat, color, null, null, null, null);
    }

    // Método ayudante completo: permite fijar los atributos específicos flexibles cuando corresponden
    private void saveProducto(ProductoRepository repo, String nombre, int precio, int stock, Categoria cat,
                              String color, String formato, String material, String tamano, String variedad) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setCategoria(cat);
        p.setColor(color);
        p.setFormato(formato);
        p.setMaterial(material);
        p.setTamano(tamano);
        p.setVariedad(variedad);
        repo.save(p);
    }
}