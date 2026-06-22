package cl.duoc.ms_jardin_catalogo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- ATRIBUTOS BASE (Obligatorios) ---
    @Column(name = "nombre_producto", nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnore // <--- ESTO EVITA EL BUCLE INFINITO
    private Categoria categoria;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // --- ATRIBUTOS ESPECÍFICOS FLEXIBLES (Opcionales) ---
    @Column(name = "variedad")
    private String variedad;

    @Column(name = "color")
    private String color;

    @Column(name = "material")
    private String material;

    @Column(name = "tamano")
    private String tamano;

    @Column(name = "formato")
    private String formato;
}