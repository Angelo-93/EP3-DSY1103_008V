package cl.duoc.ms_jardin_ventas.repository;

import cl.duoc.ms_jardin_ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}