package com.bodegahub.sistema_bodega.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioRegistroDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|BODEGUERO", message = "El rol debe ser ADMIN o BODEGUERO")
    private String rol;
}
