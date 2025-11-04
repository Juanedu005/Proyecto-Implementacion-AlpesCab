package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Mercancia;

public interface MercanciaRepository extends JpaRepository<Mercancia, Integer> {

    @Query(value = "SELECT * FROM MERCANCIA", nativeQuery = true)
    Collection<Mercancia> darMercancias();

    @Query(value = "SELECT * FROM MERCANCIA WHERE SERVICIO_ID = :id", nativeQuery = true)
    Optional<Mercancia> darMercancia(@Param("id") int servicioId);

    // IMPORTANTE: No hay secuencia para MERCANCIA. Se debe pasar SERVICIO_ID existente.
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO MERCANCIA (SERVICIO_ID, ELEMENTORECOGIDO) VALUES (:id, :elem)", nativeQuery = true)
    void insertarMercancia(@Param("id") int servicioId,
                           @Param("elem") String elementoRecogido);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE MERCANCIA SET ELEMENTORECOGIDO = :elem WHERE SERVICIO_ID = :id", nativeQuery = true)
    void actualizarMercancia(@Param("id") int servicioId,
                             @Param("elem") String elementoRecogido);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM MERCANCIA WHERE SERVICIO_ID = :id", nativeQuery = true)
    void eliminarMercancia(@Param("id") int servicioId);
}
