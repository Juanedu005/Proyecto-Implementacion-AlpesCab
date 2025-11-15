package uniandes.edu.co.proyecto.api_pruebas;


import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Usuario;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository repo;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearUsuarioRequest req) {
        try {
            if (req == null
                || req.getNombre() == null || req.getNombre().trim().isEmpty()
                || req.getEmail() == null || req.getEmail().trim().isEmpty()
                || req.getCedula() == null || req.getCedula().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "nombre, email y cedula son obligatorios."));
            }

            String nombre = req.getNombre().trim();
            String email  = req.getEmail().trim();
            String cedula = req.getCedula().trim();

            Usuario creado = repo.save(new Usuario(nombre, cedula, email));
            return ResponseEntity.created(URI.create("/api/usuarios/" + creado.getId())).body(creado);
        } catch (Exception e) {
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

    public static class CrearUsuarioRequest {
        private String nombre;
        private String email;
        private String cedula;
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCedula() { return cedula; }
        public void setCedula(String cedula) { this.cedula = cedula; }
    }
}
