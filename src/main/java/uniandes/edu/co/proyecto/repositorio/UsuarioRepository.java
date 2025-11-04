package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    @Query(value = "SELECT * FROM Usuario", nativeQuery = true)
    Collection<Usuario> darUsuarios(); 

    @Query(value = "SELECT * FROM Usuario WHERE id = :id", nativeQuery = true)
    Usuario darUsuario(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Usuario (id, nombre, email, cedula) " +
                   "VALUES (Usuario_id_SEQ.nextval, :nombre, :email, :cedula)",
           nativeQuery = true)
    void insertarUsuario(@Param("nombre") String nombre,
                         @Param("email") String email,
                         @Param("cedula") Integer cedula);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Usuario " +
                   "SET nombre = :nombre, email = :email, cedula = :cedula " +
                   "WHERE id = :id",
           nativeQuery = true)
    void actualizarUsuario(@Param("id") int id,
                           @Param("nombre") String nombre,
                           @Param("email") String email,
                           @Param("cedula") Integer cedula);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Usuario WHERE id = :id", nativeQuery = true)
    void eliminarUsuario(@Param("id") int id);
}
