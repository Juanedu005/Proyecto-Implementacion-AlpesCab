package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.SolicitudServicioRF8Request;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.servicio.SolicitudServicioRF8Service;

@RestController
@RequestMapping("/api/rf8")
public class RF8Controller {

    private final SolicitudServicioRF8Service rf8Service;

    public RF8Controller(SolicitudServicioRF8Service rf8Service) {
        this.rf8Service = rf8Service;
    }
    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarServicio(@RequestBody SolicitudServicioRF8Request request) {
        try {
            Servicio servicio = rf8Service.solicitarServicio(request);
            return ResponseEntity.ok(servicio);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Error cuando faltan datos o no se cumple una precondición
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Rolback
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
