package uniandes.edu.co.proyecto.api_pruebas;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Franja;
import uniandes.edu.co.proyecto.repositorio.FranjaRepository;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

@RestController
@RequestMapping("/api/franjas")
public class FranjaModificarRestController {

    @Autowired
    private FranjaRepository franjaRepo;

    @Autowired
    private VehiculoRepository vehiculoRepo;

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

    private String getStr(Map<String, Object> body, String... keys) {
        for (String k : keys) {
            Object v = body.get(k);
            if (v instanceof String && !((String) v).isBlank()) return (String) v;
        }
        return null;
    }

    @PutMapping("/{idFranja}")
    public ResponseEntity<?> modificar(
            @PathVariable("idFranja") Integer idFranja,
            @RequestBody Map<String, Object> body) {
        try {
            Optional<Franja> opt = franjaRepo.darFranja(idFranja);
            if (opt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of(
                        "error", "Franja no encontrada",
                        "idFranja", idFranja
                ));
            }
            Franja actual = opt.get();

            String horaInicioStr = getStr(body, "horaInicio", "hora_inicio");
            String horaFinStr    = getStr(body, "horaFin", "hora_fin");
            Integer ocupado      = getInt(body, "ocupado", "isOcupado");
            Integer vehiculoId   = getInt(body, "vehiculoId", "vehiculo_id");

            if (horaInicioStr == null || horaFinStr == null || ocupado == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Faltan campos obligatorios",
                        "esperados", "horaInicio, horaFin, ocupado (vehiculoId es opcional)",
                        "recibido", body
                ));
            }

            LocalDateTime horaInicio, horaFin;
            try {
                horaInicio = LocalDateTime.parse(horaInicioStr);
                horaFin    = LocalDateTime.parse(horaFinStr);
            } catch (Exception dtEx) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Formato de fecha/hora inválido (ISO-8601 requerido, ej: 2025-11-04T08:00:00)",
                        "detalle", dtEx.getMessage()
                ));
            }

            if (!horaInicio.isBefore(horaFin)) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Rango horario inválido: horaInicio debe ser anterior a horaFin"
                ));
            }
            if (ocupado != 0 && ocupado != 1) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Valor inválido para 'ocupado'. Debe ser 0 o 1.",
                        "ocupado", ocupado
                ));
            }

            Integer nuevoVehiculoId = vehiculoId != null ? vehiculoId
                    : (actual.getVehiculo() != null ? actual.getVehiculo().getId() : null);

            if (nuevoVehiculoId == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "No se pudo determinar el vehiculoId (ninguno enviado y franja actual no tiene vehículo asociado)"
                ));
            }

            Map<String, Object> dueno;
            try {
                dueno = jdbc.queryForMap("""
                        SELECT Ucond_idcond AS idCond, Ucond_idusuario AS idUsu
                        FROM Vehiculo
                        WHERE id = ?
                        """, nuevoVehiculoId);
            } catch (Exception notFound) {
                return ResponseEntity.status(404).body(Map.of(
                        "error", "Vehículo no encontrado",
                        "vehiculoId", nuevoVehiculoId
                ));
            }
            Integer ucondIdcond    = ((Number) dueno.get("IDCOND")).intValue();
            Integer ucondIdusuario = ((Number) dueno.get("IDUSU")).intValue();

            Integer solapes = jdbc.queryForObject("""
                    SELECT COUNT(*) FROM Franja f
                     WHERE f.Ucond_idcond = ?
                       AND f.Ucond_idusuario = ?
                       AND f.ID_FRANJA <> ?
                       AND NOT (? <= f.hora_inicio OR ? >= f.hora_fin)
                    """,
                    Integer.class,
                    ucondIdcond, ucondIdusuario,
                    idFranja,
                    Timestamp.valueOf(horaFin), 
                    Timestamp.valueOf(horaInicio)
            );
            if (solapes != null && solapes > 0) {
                return ResponseEntity.status(409).body(Map.of(
                        "error", "Traslape de disponibilidad para el mismo conductor",
                        "detalle", "El rango solicitado se cruza con otra franja existente del mismo conductor"
                ));
            }

            franjaRepo.actualizarFranja(
                    idFranja,
                    horaInicio,
                    horaFin,
                    ocupado,
                    nuevoVehiculoId,
                    ucondIdcond,
                    ucondIdusuario
            );

            // 5) Responder OK
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Franja modificada exitosamente",
                    "idFranja", idFranja,
                    "horaInicio", horaInicioStr,
                    "horaFin", horaFinStr,
                    "ocupado", ocupado,
                    "vehiculoId", nuevoVehiculoId,
                    "ucondIdcond", ucondIdcond,
                    "ucondIdusuario", ucondIdusuario
            ));

        } catch (NumberFormatException nfe) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Formato numérico inválido",
                    "detalle", nfe.getMessage()
            ));
        } catch (org.springframework.dao.DataIntegrityViolationException dive) {
            String msg = (dive.getMostSpecificCause() != null)
                    ? dive.getMostSpecificCause().getMessage()
                    : dive.getMessage();
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
