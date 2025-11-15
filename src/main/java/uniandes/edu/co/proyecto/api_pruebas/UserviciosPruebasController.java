package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.modelo.UserviciosPK;
import uniandes.edu.co.proyecto.modelo.Usuario;
import uniandes.edu.co.proyecto.repositorio.UserviciosRepository;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;

import java.sql.Date;

@RestController
@RequestMapping("/api/uservicios")
public class UserviciosPruebasController {

    @Autowired
    private UserviciosRepository userviciosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    public ResponseEntity<?> crearMedioPago(@RequestBody UservicioRequest request) {
 
        //Validar usuario que exista
        Usuario usuario = usuarioRepository.darUsuario(request.usuarioId());
        if (usuario == null) {
            return ResponseEntity
                .status(404)
                .body("El usuario con id " + request.usuarioId() + " no existe.");
        }

    
        Date fechaVenc = Date.valueOf(request.fechaVencimiento());

        // 3. Crear PK
        UserviciosPK pk = new UserviciosPK();
        pk.setId_usuario(request.usuarioId());


        Uservicios nuevo = new Uservicios();
        nuevo.setPk(pk);
        nuevo.setNombre_tc(request.nombreTC());
        nuevo.setNumero_tc(request.numeroTC());
        nuevo.setFecha_vencimiento(fechaVenc);
        nuevo.setCv(request.cv());

        // Insertar en el repo
        userviciosRepository.insertarUservicio(
                request.usuarioId(),
                request.nombreTC(),
                request.numeroTC(),
                fechaVenc,
                request.cv()
        );

        return ResponseEntity.ok().body(nuevo);
    }

    // DTO para el JSON
    public static record UservicioRequest(
        Integer usuarioId,
        String nombreTC,
        Long numeroTC,
        String fechaVencimiento, 
        Integer cv
    ) {}
}
