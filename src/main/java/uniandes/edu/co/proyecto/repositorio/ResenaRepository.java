package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Resena;
import uniandes.edu.co.proyecto.modelo.ResenaPK;

public interface ResenaRepository extends JpaRepository<Resena, ResenaPK> {

    
    @Query(value = "SELECT * FROM Resena", nativeQuery = true)
    Collection<Resena> darResenas();

   
    @Query(value = "SELECT * FROM Resena " +
                   "WHERE User_idusuario = :userIdUsuario " +
                   "  AND Ucond_idusuario = :ucondIdUsuario",
           nativeQuery = true)
    Resena darResena(@Param("userIdUsuario") int userIdUsuario,
                     @Param("ucondIdUsuario") int ucondIdUsuario);

 
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Resena " +
                   " (User_idusuario, Ucond_idusuario, Ucond_idcond, User_idser, comentario, puntuacion) " +
                   " VALUES (:userIdUsuario, :ucondIdUsuario, :ucondIdCond, :userIdSer, :comentario, :puntuacion)",
           nativeQuery = true)
    void insertarResena(@Param("userIdUsuario") Integer userIdUsuario,
                        @Param("ucondIdUsuario") Integer ucondIdUsuario,
                        @Param("ucondIdCond") Integer ucondIdCond,
                        @Param("userIdSer") Integer userIdSer,
                        @Param("comentario") String comentario,
                        @Param("puntuacion") Integer puntuacion);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Resena " +
                   "   SET comentario = :comentario, " +
                   "       puntuacion = :puntuacion " +
                   " WHERE User_idusuario = :userIdUsuario " +
                   "   AND Ucond_idusuario = :ucondIdUsuario",
           nativeQuery = true)
    void actualizarResena(@Param("userIdUsuario") int userIdUsuario,
                          @Param("ucondIdUsuario") int ucondIdUsuario,
                          @Param("comentario") String comentario,
                          @Param("puntuacion") Integer puntuacion);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Resena " +
                   " WHERE User_idusuario = :userIdUsuario " +
                   "   AND Ucond_idusuario = :ucondIdUsuario",
           nativeQuery = true)
    void eliminarResena(@Param("userIdUsuario") int userIdUsuario,
                        @Param("ucondIdUsuario") int ucondIdUsuario);
}
