package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.ServicioRF8Response;
import uniandes.edu.co.proyecto.dto.SolicitudServicioRF8Request;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.servicio.SolicitudServicioRF8Service;

@RestController
@RequestMapping("/api/rf8")
public class RF8Controller {

    private final SolicitudServicioRF8Service solicitudServicioRF8Service;

    public RF8Controller(SolicitudServicioRF8Service rf8Service) {
        this.solicitudServicioRF8Service = rf8Service;
    }
    @PostMapping("/rf8/solicitar")
    public ResponseEntity<ServicioRF8Response> solicitar(@RequestBody SolicitudServicioRF8Request req) {
        ServicioRF8Response resp = solicitudServicioRF8Service.solicitarServicio(req);
        return ResponseEntity.ok(resp);
}
}
