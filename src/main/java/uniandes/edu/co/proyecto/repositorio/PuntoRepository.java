package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.modelo.Punto;

public interface PuntoRepository extends JpaRepository<Punto, Integer> {

    // LISTAR
    @Query(value = "SELECT * FROM PUNTO", nativeQuery = true)
    Collection<Punto> darPuntos();

    // OBTENER POR PK
    @Query(value = "SELECT * FROM PUNTO WHERE PUNTO_ID = :puntoId", nativeQuery = true)
    Optional<Punto> darPunto(@Param("puntoId") int puntoId);

    // INSERTAR (usa tu secuencia PUNTO_PUNTO_ID_SEQ)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        INSERT INTO PUNTO (PUNTO_ID, DIRECCION, LATITUD, LONGITUD, SERVICIO_ID, CIUDAD_ID)
        VALUES (PUNTO_PUNTO_ID_SEQ.NEXTVAL, :direccion, :latitud, :longitud, :servicioId, :ciudadId)
        """, nativeQuery = true)
    void insertarPunto(@Param("direccion") String direccion,
                       @Param("latitud") String latitud,
                       @Param("longitud") String longitud,
                       @Param("servicioId") Integer servicioId,
                       @Param("ciudadId") Integer ciudadId);

    // ACTUALIZAR
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE PUNTO
           SET DIRECCION   = :direccion,
               LATITUD     = :latitud,
               LONGITUD    = :longitud,
               SERVICIO_ID = :servicioId,
               CIUDAD_ID   = :ciudadId
         WHERE PUNTO_ID    = :puntoId
        """, nativeQuery = true)
    void actualizarPunto(@Param("puntoId") int puntoId,
                         @Param("direccion") String direccion,
                         @Param("latitud") String latitud,
                         @Param("longitud") String longitud,
                         @Param("servicioId") Integer servicioId,
                         @Param("ciudadId") Integer ciudadId);

    // ELIMINAR
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM PUNTO WHERE PUNTO_ID = :puntoId", nativeQuery = true)
    void eliminarPunto(@Param("puntoId") int puntoId);
}
