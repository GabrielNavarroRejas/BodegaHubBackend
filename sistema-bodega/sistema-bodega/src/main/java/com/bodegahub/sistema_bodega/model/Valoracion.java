package com.bodegahub.sistema_bodega.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "valoraciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_valoracion;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer calificacion; // entre 1 y 5

    @Column(columnDefinition = "TEXT")
    private String comentario;

    private LocalDateTime fecha = LocalDateTime.now();
}

