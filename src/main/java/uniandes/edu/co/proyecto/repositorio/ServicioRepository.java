package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio,Integer> {

    @Query(value= "SELECT * FROM Servicio WHERE id= :id", nativeQuery = true)
    Collection<Servicio> darServicios();

    @Query(value="SELECT * FROM Servicio WHERE id= :id", nativeQuery = true)
    Servicio darServicio(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Servicio (id, tarifa_fija, distancia_recorrida, hora_inicio, hora_fin, P_Punto_id, User_idser, User_idusuario) VALUES (Servicio_id_SEQ.nextval, :tarifa_fija, :distancia_recorrida, :hora_inicio, :hora_fin, :P_Punto_id, :User_idser, :User_idusuario)", nativeQuery = true)
    void insertarServicio(@Param("tarifa_fija") Integer tarifa_fija, @Param("distancia_recorrida") Integer distancia_recorrida, @Param("hora_inicio") LocalDateTime hora_inicio, @Param("hora_fin") LocalDateTime hora_fin, @Param("P_Punto_id") Integer P_Punto_id, @Param("User_idser") Integer User_idser, @Param("User_idusuario") Integer User_idusuario);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Servicio SET tarifa_fija=: tarifa_fija, distancia_recorrida=: distancia_recorrida, hora_inicio=: hora_inicio, hora_fin=: hora_fin, P_Punto_id=: P_Punto_id, User_idser=: User_idser, User_idusuario=: User_idusuario WHERE id =:id", nativeQuery = true)
    void actualizarServicio(@Param("id") int id, @Param("tarifa_fija") Integer tarifa_fija, @Param("distancia_recorrida") Integer distancia_recorrida, @Param("hora_inicio") LocalDateTime hora_inicio, @Param("hora_fin") LocalDateTime hora_fin, @Param("P_Punto_id") Integer P_Punto_id, @Param("User_idser") Integer User_idser, @Param("User_idusuario") Integer User_idusuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Servicio WHERE id=:id", nativeQuery = true)
    void eliminarServicio(@Param("id") int id );
    
    @Query(value =
        "SELECT s.id, s.tarifa_fija, s.distancia_recorrida, s.hora_inicio, s.hora_fin, " +
        "        AS tipo_servicio, " +
        "       c1.nombre AS ciudad_origen, p1.direccion AS direccion_origen, " +
        "       c2.nombre AS ciudad_destino, p2.direccion AS direccion_destino " +
        "FROM Servicio s " +
        "JOIN Punto p1            ON p1.Punto_id = s.P_Punto_id " +
        "JOIN Ciudad c1           ON c1.id = p1.Ciudad_id " +
        "LEFT JOIN ServicioPuntosFin spf ON spf.Servicio_id = s.id " +
        "LEFT JOIN Punto p2       ON p2.Punto_id = spf.P_Punto_id " +
        "LEFT JOIN Ciudad c2      ON c2.id = p2.Ciudad_id " +
        "LEFT JOIN ( " +
        "   SELECT pa.Servicio_id AS id, 'PASAJEROS' AS tipo_servicio FROM Pasajeros pa " +
        "   UNION ALL " +
        "   SELECT d.Servicio_id, 'DOMICILIO' FROM Domicilio d " +
        "   UNION ALL " +
        "   SELECT m.Servicio_id, 'MERCANCIA' FROM Mercancia m " +
        ") t ON t.id = s.id " +
        "WHERE s.User_idusuario = :idUsuario AND s.User_idser = :idServicio " +
        "ORDER BY s.hora_inicio DESC",
        nativeQuery = true)
    Collection<Object[]> rfc1HistoricoPorUsuario( @Param("idUsuario") int idUsuario, @Param("idServicio") int idServicio);
    

    // ========================= RFC4 =========================
    // Utilización de servicios por ciudad y rango de fechas (conteo y %)
    // Devuelve: tipo_servicio, cantidad, porcentaje (0-100)
    @Query(value =
        "WITH base AS ( " +
        "  SELECT s.id, " +
        "         CASE " +
        "           WHEN EXISTS (SELECT 1 FROM Pasajeros pa WHERE pa.Servicio_id = s.id) THEN 'PASAJEROS' " +
        "           WHEN EXISTS (SELECT 1 FROM Domicilio d  WHERE d.Servicio_id  = s.id) THEN 'DOMICILIO' " +
        "           WHEN EXISTS (SELECT 1 FROM Mercancia m  WHERE m.Servicio_id  = s.id) THEN 'MERCANCIA' " +
        "         END AS tipo " +
        "  FROM Servicio s " +
        "  JOIN Punto p   ON p.Punto_id = s.P_Punto_id " +
        "  JOIN Ciudad c  ON c.id = p.Ciudad_id " +
        "  WHERE c.nombre = :ciudad " +
        "    AND s.hora_inicio >= :fechaIni " +
        "    AND s.hora_fin    <  :fechaFin " +
        ") " +
        "SELECT tipo AS tipo_servicio, COUNT(*) AS cantidad, " +
        "       ROUND(100 * COUNT(*) / SUM(COUNT(*)) OVER (), 2) AS porcentaje " +
        "FROM base " +
        "GROUP BY tipo " +
        "ORDER BY cantidad DESC",
        nativeQuery = true)
    Collection<Object[]> rfc4UtilizacionPorCiudadYRango(
        @Param("ciudad") String ciudad,
        @Param("hora_incio") LocalDateTime fechaIni,
        @Param("hora_fin") LocalDateTime fechaFin
    );
}
