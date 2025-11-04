package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    @Query(value = "SELECT * FROM Vehiculo", nativeQuery = true)
    Collection<Vehiculo> darVehiculos();

    @Query(value = "SELECT * FROM Vehiculo WHERE id = :id", nativeQuery = true)
    Vehiculo darVehiculo(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Vehiculo " +
                   "(id, tipo, marca, modelo, color, placa, capacidad, Ciudad_id, Ucond_idcond, Ucond_idusuario) " +
                   "VALUES (Vehiculo_id_SEQ.nextval, :tipo, :marca, :modelo, :color, :placa, :capacidad, :Ciudad_id, :Ucond_idcond, :Ucond_idusuario)",
           nativeQuery = true)
    void insertarVehiculo(@Param("tipo") String tipo,
                          @Param("marca") String marca,
                          @Param("modelo") String modelo,
                          @Param("color") String color,
                          @Param("placa") String placa,
                          @Param("capacidad") int capacidad,
                          @Param("Ciudad_id") int Ciudad_id,
                          @Param("Ucond_idcond") int Ucond_idcond,
                          @Param("Ucond_idusuario") int Ucond_idusuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Vehiculo SET " +
                   "tipo = :tipo, marca = :marca, modelo = :modelo, color = :color, " +
                   "placa = :placa, capacidad = :capacidad, Ciudad_id = :Ciudad_id, " +
                   "Ucond_idcond = :Ucond_idcond, Ucond_idusuario = :Ucond_idusuario " +
                   "WHERE id = :id",
           nativeQuery = true)
    void actualizarVehiculo(@Param("id") int id,
                            @Param("tipo") String tipo,
                            @Param("marca") String marca,
                            @Param("modelo") String modelo,
                            @Param("color") String color,
                            @Param("placa") String placa,
                            @Param("capacidad") int capacidad,
                            @Param("Ciudad_id") int Ciudad_id,
                            @Param("Ucond_idcond") int Ucond_idcond,
                            @Param("Ucond_idusuario") int Ucond_idusuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Vehiculo WHERE id = :id", nativeQuery = true)
    void eliminarVehiculo(@Param("id") int id);
}
