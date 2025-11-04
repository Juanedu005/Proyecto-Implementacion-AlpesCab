
package uniandes.edu.co.proyecto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.servicio.RFC1Service;
import uniandes.edu.co.proyecto.servicio.RFC1Service.RFC1Resultado;

@RestController
public class RFC1Controller {

    private final RFC1Service service;

    public RFC1Controller(RFC1Service service) {
        this.service = service;
    }

    @GetMapping("/rfc1/serializable")
    public RFC1Resultado serializable(@RequestParam int usuarioId) throws Exception {
        return service.consultarSerializable(usuarioId);
    }

    @GetMapping("/rfc1/read-committed")
    public RFC1Resultado readCommitted(@RequestParam int usuarioId) throws Exception {
        return service.consultarReadCommitted(usuarioId);
    }
}
