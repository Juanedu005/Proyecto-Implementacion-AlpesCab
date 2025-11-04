package uniandes.edu.co.proyecto.api_pruebas;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoRestController {

    @Autowired
    private VehiculoRepository repo;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            String tipo      = (String) body.get("tipo");
            String marca     = (String) body.get("marca");
            String modelo    = (String) body.get("modelo");
            String color     = (String) body.get("color");
            String placa     = (String) body.get("placa");

            Integer capacidad       = ((Number) body.get("capacidad")).intValue();
            Integer ciudadId        = ((Number) body.get("ciudadId")).intValue();
            Integer ucondIdcond     = ((Number) body.get("ucondIdcond")).intValue();
            Integer ucondIdusuario  = ((Number) body.get("ucondIdusuario")).intValue();

            // Insert: ID lo pone el trigger
            repo.insertarVehiculo(
                tipo, marca, modelo, color, placa, capacidad,
                ciudadId, ucondIdcond, ucondIdusuario
            );

            // Respuesta mínima (si quieres devolver el ID, te paso un SELECT luego)
            return ResponseEntity
                    .created(URI.create("/api/vehiculos"))
                    .body(Map.of(
                        "mensaje", "Vehículo registrado",
                        "tipo", tipo, "marca", marca, "modelo", modelo, "color", color, "placa", placa,
                        "capacidad", capacidad, "ciudadId", ciudadId,
                        "ucondIdcond", ucondIdcond, "ucondIdusuario", ucondIdusuario
                    ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getClass().getSimpleName() + ": " + e.getMessage()));
        }
    }
}
