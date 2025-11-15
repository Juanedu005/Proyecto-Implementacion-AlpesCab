package uniandes.edu.co.proyecto.servicio;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.FranjaRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@Service
public class RF9Service {

    private final ServicioRepository servicioRepository;
    private final FranjaRepository franjaRepository;

    public RF9Service(ServicioRepository servicioRepository, FranjaRepository franjaRepository) {
        this.servicioRepository = servicioRepository;
        this.franjaRepository = franjaRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Servicio finalizarViaje(Integer servicioId, Integer distanciaRecorrida, LocalDateTime horaFin) {
        if (servicioId == null) throw new IllegalArgumentException("servicioId es obligatorio");
        if (horaFin == null) horaFin = LocalDateTime.now();

   
        Servicio s = servicioRepository.darServicio(servicioId);
        if (s == null) throw new IllegalArgumentException("Servicio no encontrado (id=" + servicioId + ")");


        Integer distancia = (distanciaRecorrida != null) ? distanciaRecorrida : s.getDistancia_recorrida();
        if (distancia == null) distancia = 0;
        servicioRepository.actualizarFinYDistancia(servicioId, horaFin, distancia);


        franjaRepository.liberarPorServicio(servicioId);

        return servicioRepository.darServicio(servicioId);
    }
}
