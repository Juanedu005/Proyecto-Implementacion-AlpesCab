package uniandes.edu.co.proyecto.api_pruebas;


import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.repositorio.UconductorRepository;

@RestController
@RequestMapping("/api/conductores")
public class UconductorRestController {

    @Autowired
    private UconductorRepository repo;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            Integer idUsuario = (body.get("idUsuario") instanceof Number)
                    ? ((Number) body.get("idUsuario")).intValue()
                    : null;

            repo.insertarUconductor(null, idUsuario);

            return ResponseEntity
                    .created(URI.create("/api/conductores"))
                    .body(Map.of("mensaje", "Conductor registrado", "idUsuario", idUsuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getClass().getSimpleName() + ": " + e.getMessage()));
        }
    }
}
