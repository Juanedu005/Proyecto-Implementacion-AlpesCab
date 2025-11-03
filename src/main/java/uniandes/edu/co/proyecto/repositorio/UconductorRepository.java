package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Uconductor;
import uniandes.edu.co.proyecto.modelo.UconductorPk;


public interface UconductorRepository extends JpaRepository<Uconductor, UconductorPk> {

    @Query(value= "SELECT * FROM Uconductor", nativeQuery = true)
    Collection<Uconductor> darUconductors();
    
    @Query(value="SELECT * FROM Uconductor WHERE id_conductor= :id_conductor AND id_usuario= :id_usuario", nativeQuery = true)
    Uconductor darUconductor(@Param("id_conductor") int id_conductor, @Param("id_usuario") int id_usuario);
        
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Uconductor (id_conductor, id_usuario) VALUES (Uconductor_id_conductor_SEQ.nextval,Usuario_id_SEQ.nextval)", nativeQuery = true)
    void insertarUconductor(@Param("id_conductor") Integer id_conductor, @Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Uconductor SET  WHERE id_conductor =:id_conductor AND id_usuario= :id_usuario", nativeQuery = true)
    void actualizarUconductor(@Param("id_conductor") int id_conductor, @Param("id_usuario") int id_usuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Uconductor WHERE id_conductor=:id_conductor AND id_usuario= :id_usuario", nativeQuery = true)
    void eliminarUconductor(@Param("id_conductor") int id_conductor, @Param("id_usuario") int id_usuario );


     // ========================= RFC2 =========================
    // Top 20 conductores con más servicios prestados
    // Devuelve: id_conductor, id_usuario_conductor, nombre, email, cantidad_servicios
    @Query(value =
        "WITH svc_asig AS ( " +
        "  SELECT s.id, f.Ucond_idcond, f.Ucond_idusuario " +
        "  FROM Servicio s " +
        "  JOIN Franja f " +
        "    ON s.hora_inicio >= f.hora_inicio " +
        "   AND s.hora_fin    <= f.hora_fin " +
        ") " +
        "SELECT uc.id_conductor, uc.id_usuario AS id_usuario_conductor, u.nombre, u.email, " +
        "       COUNT(sa.id) AS cantidad_servicios " +
        "FROM svc_asig sa " +
        "JOIN Uconductor uc ON uc.id_conductor = sa.Ucond_idcond AND uc.id_usuario = sa.Ucond_idusuario " +
        "JOIN Usuario u    ON u.id = uc.id_usuario " +
        "GROUP BY uc.id_conductor, uc.id_usuario, u.nombre, u.email " +
        "ORDER BY cantidad_servicios DESC " +
        "FETCH FIRST 20 ROWS ONLY",
        nativeQuery = true)
    Collection<Object[]> rfc2Top20Conductores();


    // ========================= RFC3 =========================
    // Total de dinero obtenido por un conductor, por cada vehículo y discriminado por servicio
    @Query(value =
        "WITH svc_asig AS ( " +
        "  SELECT s.id, f.Vehiculo_id " +
        "  FROM Servicio s " +
        "  JOIN Franja f " +
        "    ON s.hora_inicio >= f.hora_inicio " +
        "   AND s.hora_fin    <= f.hora_fin " +
        "  WHERE f.Ucond_idcond = :idConductor AND f.Ucond_idusuario = :idUsuarioConductor " +
        "), tipos AS ( " +
        "  SELECT pa.Servicio_id AS id, 'PASAJEROS' AS tipo FROM Pasajeros pa " +
        "  UNION ALL " +
        "  SELECT d.Servicio_id, 'DOMICILIO' FROM Domicilio d " +
        "  UNION ALL " +
        "  SELECT m.Servicio_id, 'MERCANCIA' FROM Mercancia m " +
        ") " +
        "SELECT sa.Vehiculo_id AS id_vehiculo, NVL(t.tipo, 'OTRO') AS tipo_servicio, " +
        "       SUM(s.tarifa_fija * (1 - :comisionPct)) AS total_neto " +
        "FROM svc_asig sa " +
        "JOIN Servicio s ON s.id = sa.id " +
        "LEFT JOIN tipos t ON t.id = s.id " +
        "GROUP BY sa.Vehiculo_id, t.tipo " +
        "ORDER BY sa.Vehiculo_id, t.tipo",
        nativeQuery = true)
    Collection<Object[]> rfc3DineroPorVehiculoYTipo(
        @Param("id_conductor") int idConductor,
        @Param("id_usuario") int idUsuarioConductor,
        @Param("comisionPct") double comisionPct
    );


}
