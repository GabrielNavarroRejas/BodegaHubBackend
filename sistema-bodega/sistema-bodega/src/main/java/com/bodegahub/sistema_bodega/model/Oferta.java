package com.bodegahub.sistema_bodega.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_oferta;

    private String imagen_url;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Boolean activo = true;
}
