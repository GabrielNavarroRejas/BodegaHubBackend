package com.bodegahub.sistema_bodega.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Column(length = 255)
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "La bodega es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_bodega", nullable = false)
    private Bodega bodega;

    // Código de barras generado automáticamente
    @Column(name = "codigo_barras", nullable = false, unique = true, length = 13)
    private String codigoBarras;

    // Nuevo campo para la imagen
    @Size(max = 500, message = "La URL de la imagen no puede exceder 500 caracteres")
    @Column(name = "url_imagen", length = 500)
    private String urlImagen;

    @PrePersist
    protected void onCreate() {
        // Generar código de barras automáticamente si no se proporciona
        if (this.codigoBarras == null || this.codigoBarras.trim().isEmpty()) {
            this.codigoBarras = generarEAN13();
        }
    }

    private String generarEAN13() {
        // Generar 12 dígitos aleatorios
        StringBuilder base = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            base.append((int) (Math.random() * 10));
        }

        // Calcular dígito de control según algoritmo EAN-13
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(base.charAt(i));
            // Multiplicar por 1 o 3 alternadamente (empezando por 1 para posición 0)
            sum += digit * (i % 2 == 0 ? 1 : 3);
        }

        int checkDigit = (10 - (sum % 10)) % 10;

        return base.toString() + checkDigit;
    }
}