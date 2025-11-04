package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Pasajeros;

public interface PasajerosRepository extends JpaRepository<Pasajeros, Integer> {

    @Query(value = "SELECT * FROM PASAJEROS", nativeQuery = true)
    Collection<Pasajeros> darPasajeros();

    @Query(value = "SELECT * FROM PASAJEROS WHERE SERVICIO_ID = :id", nativeQuery = true)
    Optional<Pasajeros> darPasajero(@Param("id") int servicioId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO PASAJEROS (SERVICIO_ID, NIVEL) VALUES (:id, :nivel)", nativeQuery = true)
    void insertarPasajero(@Param("id") int servicioId, @Param("nivel") String nivel);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE PASAJEROS SET NIVEL = :nivel WHERE SERVICIO_ID = :id", nativeQuery = true)
    void actualizarPasajero(@Param("id") int servicioId, @Param("nivel") String nivel);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM PASAJEROS WHERE SERVICIO_ID = :id", nativeQuery = true)
    void eliminarPasajero(@Param("id") int servicioId);
}
