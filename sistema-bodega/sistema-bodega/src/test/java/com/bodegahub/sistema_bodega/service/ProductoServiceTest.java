package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Bodega;
import com.bodegahub.sistema_bodega.model.Producto;
import com.bodegahub.sistema_bodega.repository.BodegaRepository;
import com.bodegahub.sistema_bodega.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testCrearProducto() {
        // Arrange
        Bodega bodega = new Bodega();
        bodega.setIdBodega(1L);
        bodega.setNombre("Bodega Test");

        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setStock(10);

        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act
        Producto resultado = productoService.crearProducto(1L, producto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombre());
        assertEquals(bodega, resultado.getBodega());
        verify(bodegaRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testObtenerProductosPorBodega() {
        // Arrange
        Bodega bodega = new Bodega();
        bodega.setIdBodega(1L);

        Producto producto1 = new Producto();
        producto1.setNombre("Producto 1");

        Producto producto2 = new Producto();
        producto2.setNombre("Producto 2");

        List<Producto> productos = Arrays.asList(producto1, producto2);

        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
        when(productoRepository.findByBodega(bodega)).thenReturn(productos);

        // Act
        List<Producto> resultado = productoService.obtenerProductosPorBodega(1L);

        // Assert
        assertEquals(2, resultado.size());
        verify(bodegaRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findByBodega(bodega);
    }

    @Test
    void testObtenerProducto_Existente() {
        // Arrange
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Act
        Optional<Producto> resultado = productoService.obtenerProducto(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Producto Test", resultado.get().getNombre());
        verify(productoRepository, times(1)).findById(1L);
    }
}
