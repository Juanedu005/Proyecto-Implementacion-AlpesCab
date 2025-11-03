package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.modelo.UserviciosPK;

public interface UserviciosRepository extends JpaRepository<Uservicios, UserviciosPK> {

    @Query(value= "SELECT * FROM Uservicios", nativeQuery = true)
    Collection<Uservicios> darUservicios();

    @Query(value="SELECT * FROM Uservicios WHERE id_servicios= :id_servicios AND id_usuario= :id_usuario", nativeQuery = true)
    Uservicios darUservicio(@Param("id_servicios") int id_servicios, @Param("id_usuario") int id_usuario);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Uservicios (id_servicios, id_usuario, nombre_tc, numero_tc, fecha_vencimiento, cv) VALUES ( Uservicios_id_servicios_SEQ.nextval , Usuario_id_SEQ.nextval , :nombre_tc, :numero_tc, :fecha_vencimiento, :cv)", nativeQuery = true)
    void insertarUservicio(@Param("id_servicios") Integer id_servicios, @Param("id_usuario") Integer id_usuario, @Param("nombre_tc") String nombre_tc, @Param("numero_tc") Integer numero_tc, @Param("fecha_vencimiento") java.sql.Date fecha_vencimiento, @Param("cv") Integer cv);
    
    @Modifying
    @Transactional
    @Query(value= "UPDATE Uservicios SET nombre_tc=: nombre_tc, numero_tc=: numero_tc, fecha_vencimiento=: fecha_vencimiento, cv=: cv WHERE id_servicios =:id_servicios AND id_usuario= :id_usuario", nativeQuery = true)
    void actualizarUservicio(@Param("id_servicios") int id_servicios, @Param("id_usuario") int id_usuario, @Param("nombre_tc") String nombre_tc, @Param("numero_tc") Integer numero_tc, @Param("fecha_vencimiento") java.sql.Date fecha_vencimiento, @Param("cv") Integer cv);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Uservicios WHERE id_servicios=:id_servicios AND id_usuario= :id_usuario", nativeQuery = true)
    void eliminarUservicio(@Param("id_servicios") int id_servicios, @Param("id_usuario") int id_usuario );
}
