package com.bodegahub.sistema_bodega.dto;

public class LoginResponse {
    private String token;
    private String email;
    private String rol;
    private String nombre;

    public LoginResponse(String token, String email, String rol, String nombre) {
        this.token = token;
        this.email = email;
        this.rol = rol;
        this.nombre = nombre;
    }

    // Getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public String getNombre() { return nombre; }
}
