package com.bodegahub.sistema_bodega.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bodegas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodega")
    private Long idBodega;

    @NotBlank(message = "El nombre de la bodega es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;

    @Size(max = 100, message = "El nombre del propietario no puede exceder 100 caracteres")
    private String propietario;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",
            message = "Formato de teléfono inválido")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;

    @Email(message = "Debe ser un email válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;

    @Size(max = 255, message = "La URL de ubicación no puede exceder 255 caracteres")
    @Column(name = "ubicacion_maps")
    private String ubicacionMaps;

    @Size(max = 255, message = "La URL de la imagen no puede exceder 255 caracteres")
    @Column(name = "imagen_logo")
    private String imagenLogo;
}

