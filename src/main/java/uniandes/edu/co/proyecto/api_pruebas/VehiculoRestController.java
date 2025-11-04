package uniandes.edu.co.proyecto.api_pruebas;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoRestController {

    @Autowired
    private VehiculoRepository repo;

    // Para validaciones rápidas (existencia en BD y recuperar ID)
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

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            // 1) Parseo seguro (acepta snake_case y camelCase)
            String tipo   = getStr(body, "tipo");
            String marca  = getStr(body, "marca");
            String modelo = getStr(body, "modelo");
            String color  = getStr(body, "color");
            String placa  = getStr(body, "placa");

            Integer capacidad      = getInt(body, "capacidad");
            Integer ciudadId       = getInt(body, "ciudadId", "Ciudad_id", "ciudad_id");
            Integer ucondIdcond    = getInt(body, "ucondIdcond", "Ucond_idcond", "ucond_idcond");
            Integer ucondIdusuario = getInt(body, "ucondIdusuario", "Ucond_idusuario", "ucond_idusuario");

            if (tipo == null || marca == null || modelo == null || color == null || placa == null ||
                capacidad == null || ciudadId == null || ucondIdcond == null || ucondIdusuario == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Faltan campos obligatorios",
                    "esperados", "tipo, marca, modelo, color, placa, capacidad, ciudadId, ucondIdcond, ucondIdusuario",
                    "recibido", body
                ));
            }

            // 2) Validaciones de existencia (evita errores posteriores)
            Integer existeCiudad = jdbc.queryForObject(
                "SELECT COUNT(*) FROM Ciudad WHERE id = ?",
                Integer.class, ciudadId
            );
            if (existeCiudad == null || existeCiudad == 0) {
                return ResponseEntity.status(404).body(Map.of(
                    "error", "Ciudad no existe",
                    "ciudadId", ciudadId
                ));
            }

            Integer existeConductor = jdbc.queryForObject("""
                SELECT COUNT(*) FROM Uconductor
                 WHERE id_conductor = ? AND id_usuario = ?
                """, Integer.class, ucondIdcond, ucondIdusuario
            );
            if (existeConductor == null || existeConductor == 0) {
                return ResponseEntity.status(404).body(Map.of(
                    "error", "Conductor no existe en Uconductor",
                    "ucondIdcond", ucondIdcond,
                    "ucondIdusuario", ucondIdusuario
                ));
            }

            // 3) Evitar duplicado de placa para ese dueño (opcional pero recomendable)
            Integer duplicado = jdbc.queryForObject("""
                SELECT COUNT(*) FROM Vehiculo
                 WHERE placa = ? AND Ucond_idcond = ? AND Ucond_idusuario = ?
                """, Integer.class, placa, ucondIdcond, ucondIdusuario
            );
            if (duplicado != null && duplicado > 0) {
                return ResponseEntity.status(409).body(Map.of(
                    "error", "Ya existe un vehículo con esa placa para ese conductor",
                    "placa", placa,
                    "ucondIdcond", ucondIdcond,
                    "ucondIdusuario", ucondIdusuario
                ));
            }

            // 4) Insertar (ID lo asigna el trigger)
            repo.insertarVehiculo(
                tipo, marca, modelo, color, placa, capacidad,
                ciudadId, ucondIdcond, ucondIdusuario
            );

            // 5) Recuperar el ID recién creado (por placa + dueño; el más reciente)
            Integer vehiculoId = jdbc.queryForObject("""
                SELECT id FROM Vehiculo
                 WHERE placa = ? AND Ucond_idcond = ? AND Ucond_idusuario = ?
                 ORDER BY id DESC
                """, Integer.class, placa, ucondIdcond, ucondIdusuario
            );

            Map<String, Object> respuesta = new HashMap<>();
respuesta.put("id", vehiculoId);
respuesta.put("mensaje", "Vehículo registrado");
respuesta.put("tipo", tipo);
respuesta.put("marca", marca);
respuesta.put("modelo", modelo);
respuesta.put("color", color);
respuesta.put("placa", placa);
respuesta.put("capacidad", capacidad);
respuesta.put("ciudadId", ciudadId);
respuesta.put("ucondIdcond", ucondIdcond);
respuesta.put("ucondIdusuario", ucondIdusuario);

return ResponseEntity
        .created(URI.create("/api/vehiculos/" + vehiculoId))
        .body(respuesta);


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
