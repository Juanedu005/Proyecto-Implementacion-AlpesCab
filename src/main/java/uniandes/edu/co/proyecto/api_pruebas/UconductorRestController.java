package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Usuario;
import uniandes.edu.co.proyecto.repositorio.UconductorRepository;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;

@RestController
@RequestMapping("/api/uconductor")
public class UconductorRestController {

    @Autowired
    private UconductorRepository uconductorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
public ResponseEntity<?> crearUconductor(@RequestBody UconductorRequest request) {

    // Validar que el usuario exista
    Usuario usuario = usuarioRepository.darUsuario(request.usuarioId());
    if (usuario == null) {
        return ResponseEntity
                .status(404)
                .body("El usuario con id " + request.usuarioId() + " no existe.");
    }

    // Insertar en Uconductor (id_usuario)
    uconductorRepository.insertarUconductor(request.usuarioId());

    return ResponseEntity
            .status(201)
            .body("Uconductor creado para usuario " + request.usuarioId());
}


    // DTO (JSON → Java)
    public static record UconductorRequest(Integer usuarioId) {}
}
