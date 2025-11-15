package uniandes.edu.co.proyecto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.RFC1Request;
import uniandes.edu.co.proyecto.servicio.RFC1Service;

@RestController
@RequestMapping("/api_pruebas/rfc1")
public class RFC1Controller {

    private final RFC1Service rfc1Service;

    public RFC1Controller(RFC1Service rfc1Service) {
        this.rfc1Service = rfc1Service;
    }

    @PostMapping("/read-committed")
    public ResponseEntity<?> ejecutarReadCommitted(@RequestBody RFC1Request req) {
        return ResponseEntity.ok(rfc1Service.ejecutarReadCommitted(req));
    }

    @PostMapping("/serializable")
    public ResponseEntity<?> ejecutarSerializable(@RequestBody RFC1Request req) {
        return ResponseEntity.ok(rfc1Service.ejecutarSerializable(req));
    }
}
