package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Franja;

public interface FranjaRepository extends JpaRepository<Franja, Integer> {
    
    @Query(value= "SELECT * FROM Franja", nativeQuery = true)
    Collection<Franja> darFranjas();

    @Query(value="SELECT * FROM Franja WHERE id_franja= :id_franja", nativeQuery = true)
    Franja darFranja(@Param("id_franja") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Franja (id_franja, hora_inicio, hora_fin, ocupado, Vehiculo_id, Ucond_idcond, Ucond_idusuario) VALUES (alpescab_sequence.nextval, :hora_inicio, :hora_fin, :ocupado, :Vehiculo_id, :Ucond_idcond, :Ucond_idusuario)", nativeQuery = true)
    void insertarFranja(@Param("hora_inicio") String hora_inicio, @Param("hora_fin") String hora_fin, @Param("ocupado") boolean ocupado, @Param("Vehiculo_id") int Vehiculo_id, @Param("Ucond_idcond") int Ucond_idcond, @Param("Ucond_idusuario") int Ucond_idusuario);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Franja SET hora_inicio=: hora_inicio, hora_fin=: hora_fin, ocupado=: ocupado, Vehiculo_id=: Vehiculo_id, Ucond_idcond=: Ucond_idcond, Ucond_idusuario=: Ucond_idusuario WHERE id_franja =:id_franja", nativeQuery = true)
    void actualizarFranja(@Param("id_franja") int id_franja, @Param("hora_inicio") String hora_inicio, @Param("hora_fin") String hora_fin, @Param("ocupado") boolean ocupado, @Param("Vehiculo_id") int Vehiculo_id, @Param("Ucond_idcond") int Ucond_idcond, @Param("Ucond_idusuario") int Ucond_idusuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Franja WHERE id_franja=:id_franja", nativeQuery = true)
    void eliminarFranja(@Param("id_franja") int id_franja );
}
