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

                // Categorías Normalizadas
                Categoria catFlores = new Categoria();
                catFlores.setNombre("Rosas"); // Cambiado a "Rosas"
                categoriaRepository.save(catFlores);

                Categoria catInsumos = new Categoria();
                catInsumos.setNombre("Tierra e Insumos");
                categoriaRepository.save(catInsumos);

                Categoria catAccesorios = new Categoria();
                catAccesorios.setNombre("Maceteros y Accesorios");
                categoriaRepository.save(catAccesorios);

                // Productos con Atributos Flexibles
                Producto p1 = new Producto();
                p1.setNombre("Rosa Darcey Bussell");
                p1.setPrecio(9000);
                p1.setStock(20);
                p1.setCategoria(catFlores);
                p1.setVariedad("Inglesa"); // Atributo nuevo
                p1.setTamano("Mediana");   // Atributo nuevo
                productoRepository.save(p1);

                Producto p2 = new Producto();
                p2.setNombre("Tierra de Hoja 5Kg");
                p2.setPrecio(3500);
                p2.setStock(50);
                p2.setCategoria(catInsumos);
                p2.setFormato("5Kg"); // Atributo nuevo
                productoRepository.save(p2);

                Producto p3 = new Producto();
                p3.setNombre("Macetero de Greda Grande");
                p3.setPrecio(12000);
                p3.setStock(15);
                p3.setCategoria(catAccesorios);
                p3.setMaterial("Greda"); // Atributo nuevo
                p3.setTamano("Grande");  // Atributo nuevo
                productoRepository.save(p3);
            }
        };
    }
}