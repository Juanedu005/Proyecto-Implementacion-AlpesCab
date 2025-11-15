package uniandes.edu.co.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.modelo.Ciudad;
import uniandes.edu.co.proyecto.modelo.Punto;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.CiudadRepository;
import uniandes.edu.co.proyecto.repositorio.PuntoRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@Service
public class PuntoService {

    @Autowired
    private PuntoRepository puntoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired(required = false)
    private ServicioRepository servicioRepository;

    @Transactional
    public Punto registrarPunto(String direccion, String latitud, String longitud, Integer ciudadId, Integer servicioId) {

        Ciudad ciudad = ciudadRepository.getReferenceById(ciudadId);
    
        // Servicio puede ser opcional
        Servicio servicio = null;
        if (servicioId != null) {
            servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + servicioId));
        }
    
        Punto punto = new Punto();
        punto.setDireccion(direccion);
        punto.setLatitud(latitud);
        punto.setLongitud(longitud);
        punto.setCiudad_id(ciudad);
        punto.setServicio_id(servicio);
    
        return puntoRepository.save(punto);
    }
}
