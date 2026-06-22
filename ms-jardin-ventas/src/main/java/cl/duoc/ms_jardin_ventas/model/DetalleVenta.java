package cl.duoc.ms_jardin_ventas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El ID del producto que reside en el microservicio de Catálogo
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario; // Buenas prácticas: Guardar el precio histórico al momento de vender

    @Column(name = "sub_total", nullable = false)
    private Double subTotal;

    // --- RELACIÓN INVERSA ---
    // Muchos detalles pertenecen a una única venta matriz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @ToString.Exclude // Crucial: Evita bucles infinitos en la consola
    private Venta venta;
}