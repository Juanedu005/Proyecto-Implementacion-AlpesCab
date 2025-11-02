package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Punto;



public interface PuntoRepository extends JpaRepository<Punto,Integer> {

    @Query(value= "SELECT * FROM Punto", nativeQuery = true)
    Collection<Punto> darPuntos(); 
    
    @Query(value="SELECT * FROM Punto WHERE Punto_id= :Punto_id", nativeQuery = true)
    Punto darPunto(@Param("Punto_id") int Punto_id);

    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Punto (Punto_id, direccion, latitud, longitud, Servicio_id, Ciudad_id) VALUES(alpescab_sequence.nextval, :direccion, :latitud, :longitud, :Servicio_id, :Ciudad_id)", nativeQuery= true)
    void insertarPunto(@Param("direccion") String direccion, @Param("latitud") String latitud, @Param("longitud") String longitud, @Param("Servicio_id") Integer Servicio_id, @Param("Ciudad_id") Integer Ciudad_id);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Punto SET direccion=: direccion, latitud=: latitud, longitud=: longitud, Servicio_id=:Servicio_id, Ciudad_id=: Ciudad_id WHERE Punto_id =:Punto_id", nativeQuery = true)
    void actualizarPunto(@Param("Punto_id") int Punto_id, @Param("direccion") String direccion, @Param("latitud") String latitud, @Param("longitud") String longitud, @Param("Servicio_id") Integer Servicio_id, @Param("Ciudad_id") Integer Ciudad_id);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Punto WHERE Punto_id=:Punto_id", nativeQuery = true)
    void eliminarPunto(@Param("Punto_id") int Punto_id );
}
