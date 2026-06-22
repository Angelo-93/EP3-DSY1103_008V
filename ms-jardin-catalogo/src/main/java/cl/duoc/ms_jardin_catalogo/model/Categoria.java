package cl.duoc.ms_jardin_catalogo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List; // 1. IMPORTANTE: Importamos la lista de Java

@Data
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mantenemos tus excelentes validaciones de consistencia
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    // NUEVO: La relación bidireccional que cierra la estructura relacional
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnore // 2. CRÍTICO: Evita que Postman y Swagger entren en un bucle infinito
    private List<Producto> productos;
}