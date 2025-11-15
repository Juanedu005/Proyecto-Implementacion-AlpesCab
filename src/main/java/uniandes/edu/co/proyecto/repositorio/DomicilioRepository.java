package uniandes.edu.co.proyecto.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Domicilio;

public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {

    Optional<Domicilio> findById(Integer servicioId); 

 
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Domicilio d SET d.nombreRestaurante = :nombre, d.orden = :orden WHERE d.servicioId = :id")
    void actualizarDomicilio(@Param("id") int servicioId,
                             @Param("nombre") String nombreRestaurante,
                             @Param("orden") String orden);
}
