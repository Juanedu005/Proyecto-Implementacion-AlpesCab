package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ServiciosPuntosFinRepostory extends JpaRepository<ServiciosPuntosFin, ServiciosPuntosFinPK> {
    
    @Query(value= "SELECT * FROM ServiciosPuntosFin", nativeQuery = true)
    Collection<ServiciosPuntosFin> darServiciosPuntosFins();
    
    @Query(value="SELECT * FROM ServiciosPuntosFin WHERE Servicios_id= :Servicios_id AND P_Punto_id= :P_Punto_id", nativeQuery = true)
    ServiciosPuntosFin darServiciosPuntosFin(@Param("Servicios_id") int Servicios_id, @Param("P_Punto_id") int P_Punto_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ServiciosPuntosFin (Servicios_id, P_Punto_id) VALUES ( :Servicios_id, :P_Punto_id)", nativeQuery = true)
    void insertarServiciosPuntosFin(@Param("Servicios_id") Integer Servicios_id, @Param("P_Punto_id") Integer P_Punto_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ServiciosPuntosFin SET  WHERE Servicios_id =:Servicios_id AND P_Punto_id= :P_Punto_id", nativeQuery = true)
    void actualizarServiciosPuntosFin(@Param("Servicios_id") int Servicios_id, @Param("P_Punto_id") int P_Punto_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ServiciosPuntosFin WHERE Servicios_id=:Servicios_id AND P_Punto_id= :P_Punto_id", nativeQuery = true)
    void eliminarServiciosPuntosFin(@Param("Servicios_id") int Servicios_id, @Param("P_Punto_id") int P_Punto_id );
}
