package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.PuntoDTO;
import uniandes.edu.co.proyecto.dto.PuntoRespuestaDTO;
import uniandes.edu.co.proyecto.modelo.Punto;
import uniandes.edu.co.proyecto.servicio.PuntoService;

@RestController
@RequestMapping("/puntos")
public class PuntoRestController {

    @Autowired
    private PuntoService puntoService;

    @PostMapping("/registrar")
    public ResponseEntity<PuntoRespuestaDTO> registrarPunto(@RequestBody PuntoDTO dto) {
        var nuevo = puntoService.registrarPunto(
                dto.getDireccion(),
                dto.getLatitud(),
                dto.getLongitud(),
                dto.getCiudadId(),
                dto.getServicioId()
        );

        PuntoRespuestaDTO respuesta = new PuntoRespuestaDTO(
                nuevo.getPunto_id(),
                nuevo.getDireccion(),
                nuevo.getLatitud(),
                nuevo.getLongitud(),
                nuevo.getCiudad_id().getId(),
                nuevo.getServicio_id() != null ? nuevo.getServicio_id().getId() : null
        );

        return ResponseEntity.ok(respuesta);
    }
}
