package cl.duoc.ms_jardin_ventas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precioTotal;

    private LocalDateTime fechaVenta;

    // --- NUEVO: RELACIÓN MAESTRO-DETALLE ---
    // Una venta tiene muchos detalles (renglones de compra)
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Crucial: Evita bucles infinitos en la consola al hacer logs
    private List<DetalleVenta> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaVenta = LocalDateTime.now();
    }
}