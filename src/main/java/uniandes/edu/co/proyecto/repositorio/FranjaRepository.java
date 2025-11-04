package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Franja;

public interface FranjaRepository extends JpaRepository<Franja, Integer> {

    @Query(value = "SELECT * FROM FRANJA", nativeQuery = true)
    Collection<Franja> darFranjas();

    @Query(value = "SELECT * FROM FRANJA WHERE ID_FRANJA = :id_franja", nativeQuery = true)
    Optional<Franja> darFranja(@Param("id_franja") int id_franja);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        INSERT INTO FRANJA
          (ID_FRANJA, HORA_INICIO, HORA_FIN, OCUPADO, VEHICULO_ID, UCOND_IDCOND, UCOND_IDUSUARIO)
        VALUES
          (NULL, :hora_inicio, :hora_fin, :ocupado, :vehiculo_id, :ucond_idcond, :ucond_idusuario)
        """, nativeQuery = true)
    void insertarFranja(@Param("hora_inicio") LocalDateTime hora_inicio,
                        @Param("hora_fin") LocalDateTime hora_fin,
                        @Param("ocupado") Integer ocupado, 
                        @Param("vehiculo_id") int vehiculo_id,
                        @Param("ucond_idcond") int ucond_idcond,
                        @Param("ucond_idusuario") int ucond_idusuario);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE FRANJA
           SET HORA_INICIO     = :hora_inicio,
               HORA_FIN        = :hora_fin,
               OCUPADO         = :ocupado,
               VEHICULO_ID     = :vehiculo_id,
               UCOND_IDCOND    = :ucond_idcond,
               UCOND_IDUSUARIO = :ucond_idusuario
         WHERE ID_FRANJA       = :id_franja
        """, nativeQuery = true)
    void actualizarFranja(@Param("id_franja") int id_franja,
                          @Param("hora_inicio") LocalDateTime hora_inicio,
                          @Param("hora_fin") LocalDateTime hora_fin,
                          @Param("ocupado") Integer ocupado,
                          @Param("vehiculo_id") int vehiculo_id,
                          @Param("ucond_idcond") int ucond_idcond,
                          @Param("ucond_idusuario") int ucond_idusuario);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM FRANJA WHERE ID_FRANJA = :id_franja", nativeQuery = true)
    void eliminarFranja(@Param("id_franja") int id_franja);
}
