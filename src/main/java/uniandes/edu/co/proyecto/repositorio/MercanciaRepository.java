package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Mercancia;

public interface MercanciaRepository extends JpaRepository<Mercancia, Integer> {
    
    @Query(value= "SELECT * FROM Mercancia", nativeQuery = true)
    Collection<Mercancia> darMercancias();

    @Query(value="SELECT * FROM Mercancia WHERE id_mercancia= :id_mercancia", nativeQuery = true)
    Mercancia darMercancia(@Param("Servicio_id") int id);
    
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Mercancia (Servicio_id, ElementoRecogido) VALUES (Servicio_id_SEQ.nextval,:ElementoRecogido)", nativeQuery = true)
    void insertarMercancia(@Param("ElementoRecogido") String ElementoRecogido);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Mercancia SET ElementoRecogido=: ElementoRecogido WHERE Servicio_id =:Servicio_id", nativeQuery = true)
    void actualizarMercancia(@Param("Servicio_id") int Servicio_id, @Param("ElementoRecogido") String ElementoRecogido);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Mercancia WHERE Servicio_id=:Servicio_id", nativeQuery = true)
    void eliminarMercancia(@Param("Servicio_id") int Servicio_id );    


}
