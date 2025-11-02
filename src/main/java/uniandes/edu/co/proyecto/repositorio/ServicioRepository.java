package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio,Integer> {

    @Query(value= "SELECT * FROM Servicio WHERE id= :id", nativeQuery = true)
    Collection<Servicio> darServicios();

    @Query(value="SELECT * FROM Servicio WHERE id= :id", nativeQuery = true)
    Servicio darServicio(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Servicio (id, tarifa_fija, distancia_recorrida, hora_inicio, hora_fin, P_Punto_id, User_idser, User_idusuario) VALUES (alpescab_sequence.nextval, :tarifa_fija, :distancia_recorrida, :hora_inicio, :hora_fin, :P_Punto_id, :User_idser, :User_idusuario)", nativeQuery = true)
    void insertarServicio(@Param("tarifa_fija") Integer tarifa_fija, @Param("distancia_recorrida") Integer distancia_recorrida, @Param("hora_inicio") LocalDateTime hora_inicio, @Param("hora_fin") LocalDateTime hora_fin, @Param("P_Punto_id") Integer P_Punto_id, @Param("User_idser") Integer User_idser, @Param("User_idusuario") Integer User_idusuario);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Servicio SET tarifa_fija=: tarifa_fija, distancia_recorrida=: distancia_recorrida, hora_inicio=: hora_inicio, hora_fin=: hora_fin, P_Punto_id=: P_Punto_id, User_idser=: User_idser, User_idusuario=: User_idusuario WHERE id =:id", nativeQuery = true)
    void actualizarServicio(@Param("id") int id, @Param("tarifa_fija") Integer tarifa_fija, @Param("distancia_recorrida") Integer distancia_recorrida, @Param("hora_inicio") LocalDateTime hora_inicio, @Param("hora_fin") LocalDateTime hora_fin, @Param("P_Punto_id") Integer P_Punto_id, @Param("User_idser") Integer User_idser, @Param("User_idusuario") Integer User_idusuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Servicio WHERE id=:id", nativeQuery = true)
    void eliminarServicio(@Param("id") int id );
    
}
