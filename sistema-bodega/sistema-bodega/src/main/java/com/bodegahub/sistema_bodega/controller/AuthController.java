package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.dto.LoginRequest;
import com.bodegahub.sistema_bodega.dto.LoginResponse;
import com.bodegahub.sistema_bodega.model.Usuario;
import com.bodegahub.sistema_bodega.security.JwtUtil;
import com.bodegahub.sistema_bodega.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            // Buscar usuario por email
            Usuario usuario = usuarioService.buscarPorEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

            // Verificar contraseña
            if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                throw new BadCredentialsException("Credenciales inválidas");
            }

            // Generar token JWT
            String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());

            // Crear respuesta
            LoginResponse response = new LoginResponse(
                    token,
                    usuario.getEmail(),
                    usuario.getRol(),
                    usuario.getNombre()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            // ✅ Usar nuestro manejo global de excepciones
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Credenciales inválidas",
                    "message", "Email o contraseña incorrectos"
            ));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // En una implementación real, podrías invalidar el token aquí
        // Para JWT stateless, el frontend simplemente elimina el token
        return ResponseEntity.ok().body(Map.of("message", "Logout exitoso"));
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            // ✅ Mejor validación del header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(Map.of("error", "Token requerido"));
            }

            String token = authHeader.substring(7); // Remover "Bearer "
            String email = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            Usuario usuario = usuarioService.buscarPorEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return ResponseEntity.ok().body(Map.of(
                    "email", usuario.getEmail(),
                    "rol", usuario.getRol(),
                    "nombre", usuario.getNombre(),
                    "id", usuario.getId() // ✅ Agregar ID para frontend
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token inválido"));
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.ok().body(Map.of("valid", false));
            }

            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);

            // Verificar si el usuario aún existe y el token es válido
            boolean userExists = usuarioService.buscarPorEmail(email).isPresent();
            boolean tokenValid = jwtUtil.isTokenValid(token, email);

            return ResponseEntity.ok().body(Map.of(
                    "valid", userExists && tokenValid,
                    "email", email
            ));

        } catch (Exception e) {
            return ResponseEntity.ok().body(Map.of("valid", false));
        }
    }
}
