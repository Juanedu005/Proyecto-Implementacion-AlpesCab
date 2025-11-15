package uniandes.edu.co.proyecto.repositorio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.modelo.UserviciosPK;

public interface UserviciosRepository extends JpaRepository<Uservicios, UserviciosPK> {

    @Query(value = "SELECT * FROM USERVICIOS", nativeQuery = true)
    Collection<Uservicios> darUservicios();

    @Query(value = "SELECT * FROM USERVICIOS WHERE id_usuario = :id_usuario AND id_servicios = :id_servicios", nativeQuery = true)
    Optional<Uservicios> darUservicio(@Param("id_usuario") int idUsuario,
                                      @Param("id_servicios") int idServicios);
    
    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
      FROM USERVICIOS
     WHERE ID_USUARIO = :userId
       AND FECHA_VENCIMIENTO >= TRUNC(SYSDATE)
    """, nativeQuery = true)
    int hasValidPayment(@Param("userId") Integer userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        INSERT INTO USERVICIOS (id_usuario, id_servicios, nombre_tc, numero_tc, fecha_vencimiento, cv)
        VALUES (:id_usuario, USERVICIOS_ID_SERVICIOS_SEQ.NEXTVAL, :nombre_tc, :numero_tc, :fecha_vencimiento, :cv)
        """, nativeQuery = true)
    void insertarUservicio(@Param("id_usuario") Integer idUsuario,
                           @Param("nombre_tc") String nombreTc,
                           @Param("numero_tc") Long numeroTc,
                           @Param("fecha_vencimiento") Date fechaVencimiento,
                           @Param("cv") Integer cv);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE USERVICIOS
           SET nombre_tc = :nombre_tc,
               numero_tc = :numero_tc,
               fecha_vencimiento = :fecha_vencimiento,
               cv = :cv
         WHERE id_usuario = :id_usuario
           AND id_servicios = :id_servicios
        """, nativeQuery = true)
    void actualizarUservicio(@Param("id_usuario") Integer idUsuario,
                             @Param("id_servicios") Integer idServicios,
                             @Param("nombre_tc") String nombreTc,
                             @Param("numero_tc") Long numeroTc,
                             @Param("fecha_vencimiento") Date fechaVencimiento,
                             @Param("cv") Integer cv);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM USERVICIOS WHERE id_usuario = :id_usuario AND id_servicios = :id_servicios", nativeQuery = true)
    void eliminarUservicio(@Param("id_usuario") Integer idUsuario,
                           @Param("id_servicios") Integer idServicios);

    @Query("""
    SELECT u
    FROM Uservicios u
    WHERE u.usuario.id = :idUsuario
    AND u.fecha_vencimiento >= CURRENT_DATE
    ORDER BY u.fecha_vencimiento ASC
    """)
    List<Uservicios> encontrarMediosPagoVigentes(@Param("idUsuario") Integer idUsuario);
    
}
