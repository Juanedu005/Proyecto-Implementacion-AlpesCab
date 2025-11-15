package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query(value = "SELECT * FROM Servicio", nativeQuery = true)
    Collection<Servicio> darServicios();

    @Query(value = "SELECT * FROM Servicio WHERE id = :id", nativeQuery = true)
    Servicio darServicio(@Param("id") Integer id);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Servicio " +
                   " (tarifa_fija, distancia_recorrida, hora_inicio, hora_fin, P_Punto_id) " +
                   " VALUES (:tarifa, :distancia, :hini, :hfin, :puntoId)",
           nativeQuery = true)
    void insertarServicio(@Param("tarifa") Integer tarifa,
                          @Param("distancia") Integer distancia,
                          @Param("hini") LocalDateTime horaInicio,
                          @Param("hfin") LocalDateTime horaFin,
                          @Param("puntoId") Integer puntoId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Servicio " +
                   "   SET tarifa_fija = :tarifa, " +
                   "       distancia_recorrida = :distancia, " +
                   "       hora_inicio = :hini, " +
                   "       hora_fin    = :hfin, " +
                   "       P_Punto_id  = :puntoId " +
                   " WHERE id = :id",
           nativeQuery = true)
    void actualizarServicio(@Param("id") Integer id,
                            @Param("tarifa") Integer tarifa,
                            @Param("distancia") Integer distancia,
                            @Param("hini") LocalDateTime horaInicio,
                            @Param("hfin") LocalDateTime horaFin,
                            @Param("puntoId") Integer puntoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Servicio WHERE id = :id", nativeQuery = true)
    void eliminarServicio(@Param("id") Integer id);


    //rfc1
    @Query(value =
        "SELECT s.* " +
        "  FROM Servicio s " +
        "  JOIN Uservicios us " +
        "    ON us.id_servicios = s.id " +
        " WHERE us.id_usuario  = :idUsuario " +
        "   AND s.id           = :idServicio",
        nativeQuery = true)
    List<Servicio> rfc1HistoricoPorUsuario(@Param("idUsuario") int idUsuario,
                                           @Param("idServicio") int idServicio);


    //RFC4
    @Query(value =
        "SELECT p.Ciudad_id          AS ciudad_id, " +
        "       COUNT(s.id)          AS total_servicios " +
        "  FROM Servicio s " +
        "  JOIN Punto p ON p.Servicio_id = s.id " +
        " WHERE p.Ciudad_id = TO_NUMBER(:ciudad) " +
        "   AND s.hora_inicio >= :fechaIni " +
        "   AND s.hora_fin    <= :fechaFin " +
        " GROUP BY p.Ciudad_id",
        nativeQuery = true)
    List<Object[]> rfc4UtilizacionPorCiudadYRango(@Param("ciudad") String ciudad,
                                                  @Param("fechaIni") LocalDateTime fechaIni,
                                                  @Param("fechaFin") LocalDateTime fechaFin);




    //RFC1
    @Query(value = """
    SELECT s.* 
      FROM SERVICIO s
      JOIN USERVICIOS u ON u.ID_SERVICIOS = s.ID
     WHERE u.ID_USUARIO = :usuarioId
     ORDER BY s.HORA_INICIO
    """, nativeQuery = true)
    List<Servicio> listarHistoricoUsuario(@Param("usuarioId") int usuarioId);



    @Modifying
    @Transactional
    @Query(value = """
        UPDATE SERVICIO
        SET HORA_FIN = :horaFin,
            DISTANCIA_RECORRIDA = :distancia
        WHERE ID = :id
        """, nativeQuery = true)
    void actualizarFinYDistancia(@Param("id") Integer id,
                                @Param("horaFin") java.time.LocalDateTime horaFin,
                                @Param("distancia") Integer distancia);
}                                                
