package com.bodegahub.sistema_bodega.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "descuentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_descuento;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    private Double porcentaje; // Ejemplo: 10.00 = 10%
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Boolean activo = true;
}
