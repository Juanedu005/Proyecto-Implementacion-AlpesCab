package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.FinalizarViajeRequest;
import uniandes.edu.co.proyecto.dto.FinalizarViajeResponse;
import uniandes.edu.co.proyecto.servicio.RF9Service;

@RestController
@RequestMapping("/api/rf9")
public class RF9Controller {

    private final RF9Service rf9Service;

    public RF9Controller(RF9Service rf9Service) {
        this.rf9Service = rf9Service;
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizar(@RequestBody FinalizarViajeRequest req) {
        if (req == null || req.getServicioId() == null) {
            return ResponseEntity.badRequest().body("Falta servicioId");
        }
        var s = rf9Service.finalizarViaje(req.getServicioId(),
                                          req.getDistanciaRecorrida(),
                                          req.getHoraFin());
        var resp = new FinalizarViajeResponse(
                s.getId(),
                s.getHora_incio(),
                s.getHora_fin(),
                s.getDistancia_recorrida()
        );
        return ResponseEntity.ok(resp);
    }
}
