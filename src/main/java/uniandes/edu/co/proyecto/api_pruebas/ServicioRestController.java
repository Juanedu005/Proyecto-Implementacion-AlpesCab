package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.dto.SolicitudDTO;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.servicio.SolicitudServiceRF8;

@RestController
@RequestMapping("/servicios")
public class ServicioRestController {

    @Autowired
    private SolicitudServiceRF8 solicitudService;

    @PostMapping("/servicios/solicitar")
    public ResponseEntity<Servicio> solicitar(@RequestBody SolicitudDTO dto) {
        Servicio servicio = solicitudService.solicitarServicio(
            dto.getUsuarioId(),
            dto.getPuntoOrigenId(),
            dto.getPuntoDestinoId(),
            dto.getNivel(),
            dto.getDistancia(),
            dto.isForzarError()
        );
        return ResponseEntity.ok(servicio);
    }
}
