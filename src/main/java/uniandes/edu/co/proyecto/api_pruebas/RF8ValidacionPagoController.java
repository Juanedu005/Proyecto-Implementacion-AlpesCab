package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.ValidacionPagoRequest;
import uniandes.edu.co.proyecto.dto.ValidacionPagoResponse;
import uniandes.edu.co.proyecto.servicio.ValidacionPagoService;

@RestController
@RequestMapping("/api/rf8")
public class RF8ValidacionPagoController {

    private final ValidacionPagoService validacionPagoService;

    public RF8ValidacionPagoController(ValidacionPagoService validacionPagoService) {
        this.validacionPagoService = validacionPagoService;
    }

    // RF8 – Punto 1: verificar que el usuario tiene medio de pago vigente
    @PostMapping("/validar-medio-pago")
    public ResponseEntity<ValidacionPagoResponse> validarMedioPago(@RequestBody ValidacionPagoRequest req) {
        if (req == null || req.getUsuarioId() == null) {
            return ResponseEntity.badRequest()
                    .body(new ValidacionPagoResponse(false, "Falta usuarioId"));
        }

        var r = validacionPagoService.validarMedioPago(req.getUsuarioId());

        if (!r.encontrado) {
            return ResponseEntity.status(404)
                    .body(new ValidacionPagoResponse(false, r.motivo));
        }

        if (r.valido) {
            return ResponseEntity.ok(new ValidacionPagoResponse(true, null));
        } else {
            return ResponseEntity.ok(new ValidacionPagoResponse(false, r.motivo));
        }
    }
}
