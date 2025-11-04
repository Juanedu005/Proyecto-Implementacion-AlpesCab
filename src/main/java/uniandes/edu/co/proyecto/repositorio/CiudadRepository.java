package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    @Query(value = "SELECT * FROM CIUDAD", nativeQuery = true)
    Collection<Ciudad> darCiudades();

    @Query(value = "SELECT * FROM CIUDAD WHERE ID = :id", nativeQuery = true)
    Optional<Ciudad> darCiudad(@Param("id") int id);

    // Si tu PK se genera con secuencia en Oracle:
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO CIUDAD (ID, NOMBRE) VALUES (CIUDAD_ID_SEQ.NEXTVAL, :nombre)", nativeQuery = true)
    void insertarCiudad(@Param("nombre") String nombre);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE CIUDAD SET NOMBRE = :nombre WHERE ID = :id", nativeQuery = true)
    void actualizarCiudad(@Param("id") int id, @Param("nombre") String nombre);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM CIUDAD WHERE ID = :id", nativeQuery = true)
    void eliminarCiudad(@Param("id") int id);
}
