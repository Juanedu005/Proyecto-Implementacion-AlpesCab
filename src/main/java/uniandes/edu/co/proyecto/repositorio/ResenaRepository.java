package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Resena;
import uniandes.edu.co.proyecto.modelo.ResenaPK;

public interface ResenaRepository  extends JpaRepository<Resena, ResenaPK> {

    @Query(value= "SELECT * FROM Resena", nativeQuery = true)
    Collection<Resena> darResenas();

    @Query(value="SELECT * FROM Resena WHERE User_idusuario= :User_idusuario AND Ucond_idusuario= :Ucond_idusuario", nativeQuery = true)
    Resena darResena(@Param("User_idusuario") int User_idusuario, @Param("Ucond_idusuario") int Ucond_idusuario);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Resena (User_idusuario, Ucond_idusuario, comentario, puntuacion) VALUES ( :User_idusuario, :Ucond_idusuario, :comentario, :puntuacion)", nativeQuery = true)
    void insertarResena(@Param("User_idusuario") Integer User_idusuario, @Param("Ucond_idusuario") Integer Ucond_idusuario, @Param("comentario") String comentario, @Param("puntuacion") Integer puntuacion);

    @Modifying
    @Transactional
    @Query(value= "UPDATE Resena SET comentario=: comentario, puntuacion=: puntuacion WHERE User_idusuario =:User_idusuario AND Ucond_idusuario= :Ucond_idusuario", nativeQuery = true)
    void actualizarResena(@Param("User_idusuario") int User_idusuario, @Param("Ucond_idusuario") int Ucond_idusuario, @Param("comentario") String comentario, @Param("puntuacion") Integer puntuacion);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Resena WHERE User_idusuario=:User_idusuario AND Ucond_idusuario= :Ucond_idusuario", nativeQuery = true)
    void eliminarResena(@Param("User_idusuario") int User_idusuario, @Param("Ucond_idusuario") int Ucond_idusuario );
}
