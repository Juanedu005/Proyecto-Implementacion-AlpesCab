package uniandes.edu.co.proyecto.api_pruebas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.PuntoDTO;
import uniandes.edu.co.proyecto.modelo.Punto;
import uniandes.edu.co.proyecto.servicio.PuntoService;

@RestController
@RequestMapping("/puntos")
public class PuntoRestController {

    @Autowired
    private PuntoService puntoService;

    @PostMapping("/registrar")
    public ResponseEntity<Punto> registrarPunto(@RequestBody PuntoDTO dto) {
        Punto nuevo = puntoService.registrarPunto(
                dto.getDireccion(),
                dto.getLatitud(),
                dto.getLongitud(),
                dto.getCiudadId(),
                dto.getServicioId()
        );
        return ResponseEntity.ok(nuevo);
    }
}
