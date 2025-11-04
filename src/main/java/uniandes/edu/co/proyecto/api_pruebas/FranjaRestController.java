package uniandes.edu.co.proyecto.api_pruebas;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.repositorio.FranjaRepository;

@RestController
@RequestMapping("/api/franjas")
public class FranjaRestController {

    @Autowired
    private FranjaRepository repo;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            String horaInicioStr   = (String) body.get("horaInicio"); // ej: "2025-11-03T09:00:00"
            String horaFinStr      = (String) body.get("horaFin");    // ej: "2025-11-03T11:00:00"
            Integer ocupado        = ((Number) body.get("ocupado")).intValue(); // 0 o 1
            Integer vehiculoId     = ((Number) body.get("vehiculoId")).intValue();
            Integer ucondIdcond    = ((Number) body.get("ucondIdcond")).intValue();
            Integer ucondIdusuario = ((Number) body.get("ucondIdusuario")).intValue();

            // ✅ usar LocalDateTime directamente (no Timestamp)
            LocalDateTime horaInicio = LocalDateTime.parse(horaInicioStr);
            LocalDateTime horaFin    = LocalDateTime.parse(horaFinStr);

            repo.insertarFranja(horaInicio, horaFin, ocupado, vehiculoId, ucondIdcond, ucondIdusuario);

            // ✅ eliminar variable id no existente
            return ResponseEntity
                    .created(URI.create("/api/franjas"))
                    .body(Map.of(
                        "mensaje", "Franja registrada exitosamente",
                        "horaInicio", horaInicioStr,
                        "horaFin", horaFinStr,
                        "ocupado", ocupado,
                        "vehiculoId", vehiculoId,
                        "ucondIdcond", ucondIdcond,
                        "ucondIdusuario", ucondIdusuario
                    ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getClass().getSimpleName() + ": " + e.getMessage()));
        }
    }
}
