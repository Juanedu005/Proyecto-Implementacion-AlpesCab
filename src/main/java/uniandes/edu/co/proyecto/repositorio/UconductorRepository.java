package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Uconductor;
import uniandes.edu.co.proyecto.modelo.UconductorPk;


public interface UconductorRepository extends JpaRepository<Uconductor, UconductorPk> {

    @Query(value= "SELECT * FROM Uconductor", nativeQuery = true)
    Collection<Uconductor> darUconductors();
    
    @Query(value="SELECT * FROM Uconductor WHERE id_conductor= :id_conductor AND id_usuario= :id_usuario", nativeQuery = true)
    Uconductor darUconductor(@Param("id_conductor") int id_conductor, @Param("id_usuario") int id_usuario);
        
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Uconductor (id_conductor, id_usuario) VALUES ( :id_conductor, :id_usuario)", nativeQuery = true)
    void insertarUconductor(@Param("id_conductor") Integer id_conductor, @Param("id_usuario") Integer id_usuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Uconductor SET  WHERE id_conductor =:id_conductor AND id_usuario= :id_usuario", nativeQuery = true)
    void actualizarUconductor(@Param("id_conductor") int id_conductor, @Param("id_usuario") int id_usuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Uconductor WHERE id_conductor=:id_conductor AND id_usuario= :id_usuario", nativeQuery = true)
    void eliminarUconductor(@Param("id_conductor") int id_conductor, @Param("id_usuario") int id_usuario );
}
