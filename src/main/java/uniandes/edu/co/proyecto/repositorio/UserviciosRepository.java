package uniandes.edu.co.proyecto.repositorio;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.modelo.UserviciosPK;

public interface UserviciosRepository extends JpaRepository<Uservicios, UserviciosPK> {

    /* ===== SELECTS ===== */
    @Query(value = "SELECT * FROM USERVICIOS", nativeQuery = true)
    Collection<Uservicios> darUservicios();

    @Query(value = "SELECT * FROM USERVICIOS WHERE id_usuario = :id_usuario AND id_servicios = :id_servicios", nativeQuery = true)
    Optional<Uservicios> darUservicio(@Param("id_usuario") int idUsuario,
                                      @Param("id_servicios") int idServicios);

    /* ===== INSERT ===== */
    // Genera id_servicios con la secuencia; id_usuario viene de USUARIO (FK existente)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        INSERT INTO USERVICIOS (id_usuario, id_servicios, nombre_tc, numero_tc, fecha_vencimiento, cv)
        VALUES (:id_usuario, USERVICIOS_ID_SERVICIOS_SEQ.NEXTVAL, :nombre_tc, :numero_tc, :fecha_vencimiento, :cv)
        """, nativeQuery = true)
    void insertarUservicio(@Param("id_usuario") Integer idUsuario,
                           @Param("nombre_tc") String nombreTc,
                           @Param("numero_tc") Integer numeroTc,
                           @Param("fecha_vencimiento") Date fechaVencimiento,
                           @Param("cv") Integer cv);

    /* ===== UPDATE ===== */
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
                             @Param("numero_tc") Integer numeroTc,
                             @Param("fecha_vencimiento") Date fechaVencimiento,
                             @Param("cv") Integer cv);

    /* ===== DELETE ===== */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM USERVICIOS WHERE id_usuario = :id_usuario AND id_servicios = :id_servicios", nativeQuery = true)
    void eliminarUservicio(@Param("id_usuario") Integer idUsuario,
                           @Param("id_servicios") Integer idServicios);
}
