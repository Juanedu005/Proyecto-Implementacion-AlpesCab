package uniandes.edu.co.proyecto.api_pruebas;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.CrearCiudadRequest;
import uniandes.edu.co.proyecto.modelo.Ciudad;
import uniandes.edu.co.proyecto.repositorio.CiudadRepository;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadRestController {

    @Autowired
    private CiudadRepository repo;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearCiudadRequest req) {
        try {
            if (req == null || req.getNombre() == null || req.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El nombre es obligatorio."));
            }
            String nombre = req.getNombre().trim();


            Ciudad creada = repo.save(new Ciudad(null, nombre)); // usa el @GeneratedValue (secuencia)
            return ResponseEntity.created(URI.create("/api/ciudades/" + creada.getId())).body(creada);
        } catch (Exception e) {
            // Para ver la causa exacta del error en la respuesta
            return ResponseEntity.internalServerError().body(Map.of("error", e.getClass().getSimpleName() + ": " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        return repo.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
