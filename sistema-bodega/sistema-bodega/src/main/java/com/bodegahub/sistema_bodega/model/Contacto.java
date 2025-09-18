package com.bodegahub.sistema_bodega.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_contacto;

    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String nombre_bodega;
    private String ciudad;
    private String direccion;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fecha = LocalDateTime.now();
}

