package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Pasajeros;

public interface PasajerosRepository extends JpaRepository<PasajerosRepository, Integer> {

    @Query(value= "SELECT * FROM Pasajeros", nativeQuery = true)
    Collection<PasajerosRepository> darPasajeros();

    @Query(value="SELECT * FROM Pasajeros WHERE Servicio_id= : Servicio_id", nativeQuery = true)
    Pasajeros darPasajero(@Param("Servicio_id") int Servicio_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Pasajeros (Servicio_id, nivel) VALUES (Servicio_id_SEQ.nextval,:nivel)", nativeQuery = true)
    void insertarPasajero(@Param("nivel") String nivel);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Pasajeros SET nivel=: nivel WHERE Servicio_id =:Servicio_id", nativeQuery = true)
    void actualizarPasajero(@Param("Servicio_id") int Servicio_id, @Param("nivel") String nivel);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Pasajeros WHERE Servicio_id=:Servicio_id", nativeQuery = true)
    void eliminarPasajero(@Param("Servicio_id") int Servicio_id );
    
}
