package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Domicilio;


public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {

    @Query(value="SELECT * FROM Domicilio", nativeQuery = true)
    Collection<Domicilio> darDomicilios();

    @Query(value="SELECT * FROM Domicilio WHERE Servicio_id= :Servicio_id", nativeQuery = true)
    Domicilio darDomicilio(@Param("Servicio_id") int Servicio_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Domicilio (Servicio_id, nombre_restaurante, orden) VALUES (Servicio_id_SEQ.nextval, :nombre_restaurante, :orden)", nativeQuery= true)
    void insertarDomicilio(@Param("nombre_restaurante") String nombre_restaurante, @Param("orden") String orden);
    
    @Modifying
    @Transactional
    @Query(value= "UPDATE Domicilio SET nombre_restaurante=: nombre_restaurante, orden=: orden WHERE Servicio_id =:Servicio_id", nativeQuery = true)
    void actualizarDomicilio(@Param("Servicio_id") int Servicio_id, @Param("nombre_restaurante") String nombre_restaurante, @Param("orden") String orden);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Domicilio WHERE Servicio_id=:Servicio_id", nativeQuery = true)
    void eliminarDomicilio(@Param("Servicio_id") int Servicio_id );
}
