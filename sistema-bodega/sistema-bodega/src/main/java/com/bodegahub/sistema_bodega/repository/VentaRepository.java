package com.bodegahub.sistema_bodega.repository;

import com.bodegahub.sistema_bodega.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Buscar ventas por bodega
    @Query("SELECT v FROM Venta v WHERE v.bodega.idBodega = :idBodega")
    List<Venta> findByBodegaId(@Param("idBodega") Long idBodega);

    // Buscar ventas por usuario
    @Query("SELECT v FROM Venta v WHERE v.usuario.id = :idUsuario")
    List<Venta> findByUsuarioId(@Param("idUsuario") Long idUsuario);

    // Buscar ventas por bodega y usuario
    @Query("SELECT v FROM Venta v WHERE v.bodega.idBodega = :idBodega AND v.usuario.id = :idUsuario")
    List<Venta> findByBodegaIdAndUsuarioId(@Param("idBodega") Long idBodega, @Param("idUsuario") Long idUsuario);

    // Buscar ventas por período
    @Query("SELECT v FROM Venta v WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Venta> findByFechaBetween(@Param("fechaInicio") Date fechaInicio,
                                   @Param("fechaFin") Date fechaFin);

    // Obtener total de ventas por bodega
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.bodega.idBodega = :idBodega")
    Double findTotalVentasByBodega(@Param("idBodega") Long idBodega);

    // Obtener estadísticas de ventas
    @Query("SELECT new map(v.bodega.nombre as bodega, SUM(v.total) as total, COUNT(v) as cantidad) " +
            "FROM Venta v GROUP BY v.bodega.idBodega")
    List<Map<String, Object>> findEstadisticasVentas();
}

