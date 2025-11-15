package uniandes.edu.co.proyecto.api_pruebas;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.repositorio.FranjaRepository;

@RestController
@RequestMapping("/api/franjas")
public class FranjaEliminarRestController {

    @Autowired
    private FranjaRepository franjaRepo;

    @Autowired
    private JdbcTemplate jdbc;

    @DeleteMapping("/{idFranja}")
    public ResponseEntity<?> eliminar(@PathVariable("idFranja") Integer idFranja) {
        try {
            Integer existe = jdbc.queryForObject(
                "SELECT COUNT(*) FROM FRANJA WHERE ID_FRANJA = ?",
                Integer.class, idFranja
            );
            if (existe == null || existe == 0) {
                return ResponseEntity.status(404).body(Map.of(
                    "error", "Franja no encontrada",
                    "idFranja", idFranja
                ));
            }

            

            franjaRepo.eliminarFranja(idFranja);

            return ResponseEntity.ok(Map.of(
                "mensaje", "Franja eliminada exitosamente",
                "idFranja", idFranja
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", e.getClass().getSimpleName(),
                "detalle", e.getMessage()
            ));
        }
    }
}
