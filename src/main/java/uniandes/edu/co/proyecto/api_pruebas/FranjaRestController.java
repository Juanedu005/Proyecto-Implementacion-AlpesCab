package uniandes.edu.co.proyecto.api_pruebas;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.repositorio.FranjaRepository;

@RestController
@RequestMapping("/api/franjas")
public class FranjaRestController {

    @Autowired
    private FranjaRepository repo;

    @Autowired
    private JdbcTemplate jdbc;

    private Integer getInt(Map<String, Object> body, String... keys) {
        for (String k : keys) {
            Object v = body.get(k);
            if (v == null) continue;
            if (v instanceof Number) return ((Number) v).intValue();
            if (v instanceof String && !((String) v).isBlank()) return Integer.parseInt((String) v);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            String horaInicioStr = (String) body.get("horaInicio");
            String horaFinStr    = (String) body.get("horaFin");
            Integer ocupado      = getInt(body, "ocupado", "isOcupado");
            Integer vehiculoId   = getInt(body, "vehiculoId", "vehiculo_id");

            if (horaInicioStr == null || horaFinStr == null || ocupado == null || vehiculoId == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Faltan campos obligatorios",
                    "esperados", "horaInicio, horaFin, ocupado, vehiculoId",
                    "recibido", body
                ));
            }

            LocalDateTime horaInicio;
            LocalDateTime horaFin;
            try {
                horaInicio = LocalDateTime.parse(horaInicioStr);
                horaFin    = LocalDateTime.parse(horaFinStr);
            } catch (Exception dtEx) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Formato de fecha/hora inválido. Usa ISO-8601, ej: 2025-11-04T08:00:00",
                    "detalle", dtEx.getMessage()
                ));
            }

            if (!horaInicio.isBefore(horaFin)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Rango horario inválido: horaInicio debe ser anterior a horaFin"
                ));
            }


            Map<String, Object> dueno;
            try {
                dueno = jdbc.queryForMap(
                    "SELECT Ucond_idcond AS idCond, Ucond_idusuario AS idUsu FROM Vehiculo WHERE id = ?",
                    vehiculoId
                );
            } catch (Exception notFound) {
                return ResponseEntity.status(404).body(Map.of(
                    "error", "Vehículo no encontrado",
                    "vehiculoId", vehiculoId
                ));
            }
            Integer ucondIdcond    = ((Number) dueno.get("IDCOND")).intValue();
            Integer ucondIdusuario = ((Number) dueno.get("IDUSU")).intValue();


            Integer solapes = jdbc.queryForObject("""
                SELECT COUNT(*) FROM Franja f
                 WHERE f.Ucond_idcond = ?
                   AND f.Ucond_idusuario = ?
                   AND NOT (? <= f.hora_inicio OR ? >= f.hora_fin)
                """,
                Integer.class,
                ucondIdcond, ucondIdusuario,
                Timestamp.valueOf(horaFin),
                Timestamp.valueOf(horaInicio)
            );
            if (solapes != null && solapes > 0) {
                return ResponseEntity.status(409).body(Map.of(
                    "error", "Traslape de disponibilidad para el mismo conductor",
                    "detalle", "El rango solicitado se cruza con una franja existente"
                ));
            }


            repo.insertarFranja(horaInicio, horaFin, ocupado, vehiculoId, ucondIdcond, ucondIdusuario);

            Integer idFranja = null;
            try {
                idFranja = jdbc.queryForObject("""
                    SELECT ID_FRANJA FROM Franja
                     WHERE HORA_INICIO = ?
                       AND HORA_FIN    = ?
                       AND OCUPADO     = ?
                       AND VEHICULO_ID = ?
                       AND UCOND_IDCOND = ?
                       AND UCOND_IDUSUARIO = ?
                     ORDER BY ID_FRANJA DESC
                    """,
                    Integer.class,
                    Timestamp.valueOf(horaInicio), Timestamp.valueOf(horaFin), ocupado,
                    vehiculoId, ucondIdcond, ucondIdusuario
                );
            } catch (Exception ignore) { /* si no se encuentra exacto, igual respondemos sin id */ }

            return ResponseEntity
                .created(URI.create(idFranja != null ? ("/api/franjas/" + idFranja) : "/api/franjas"))
                .body(Map.of(
                    "mensaje", "Franja registrada exitosamente",
                    "idFranja", idFranja,
                    "horaInicio", horaInicioStr,
                    "horaFin", horaFinStr,
                    "ocupado", ocupado,
                    "vehiculoId", vehiculoId,
                    "ucondIdcond", ucondIdcond,
                    "ucondIdusuario", ucondIdusuario
                ));

        } catch (NumberFormatException nfe) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Formato numérico inválido",
                "detalle", nfe.getMessage()
            ));
        } catch (org.springframework.dao.DataIntegrityViolationException dive) {
            String msg = dive.getMostSpecificCause() != null ? dive.getMostSpecificCause().getMessage() : dive.getMessage();
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Violación de integridad",
                "detalle", msg
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", e.getClass().getSimpleName(),
                "detalle", e.getMessage()
            ));
        }
    }
}
