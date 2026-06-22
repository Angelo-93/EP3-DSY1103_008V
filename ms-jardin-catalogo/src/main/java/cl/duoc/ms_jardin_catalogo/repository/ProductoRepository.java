package cl.duoc.ms_jardin_catalogo.repository;

import cl.duoc.ms_jardin_catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}