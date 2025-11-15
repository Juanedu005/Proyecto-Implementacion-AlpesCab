package uniandes.edu.co.proyecto.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Punto;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.*;

@Service
public class SolicitudServiceRF8 {

    @Autowired
    private UserviciosRepository userviciosRepository;

    @Autowired
    private UconductorRepository uconductorRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private FranjaRepository franjaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private PuntoRepository puntoRepository;

    @Transactional(rollbackFor = Exception.class)
    public Servicio solicitarServicio(Integer usuarioId,
                                    Integer puntoOrigenId,
                                    Integer puntoDestinoId,
                                    String nivel,
                                    Integer distancia,
                                    boolean forzarError) {

        boolean tienePago = userviciosRepository.darUservicios().stream()
                .anyMatch(u -> u.getPk().getId_usuario().equals(usuarioId));

        if (!tienePago)
            throw new RuntimeException("El usuario no tiene un medio de pago registrado.");


        var conductor = uconductorRepository.darUconductors()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay conductores disponibles."));


        var vehiculo = vehiculoRepository.darVehiculos()
                .stream()
                .filter(v -> {
                    var pk = conductor.getPk();
                    var vcond = v.getUconductor();
                    return vcond != null &&
                        vcond.getPk().getId_conductor().equals(pk.getId_conductor()) &&
                        vcond.getPk().getId_usuario().equals(pk.getId_usuario());
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El conductor no tiene vehículo registrado."));


        franjaRepository.insertarFranja(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1, // 1 = ocupado
                vehiculo.getId(),
                conductor.getPk().getId_conductor(),
                conductor.getPk().getId_usuario()
        );


        Punto puntoOrigen = puntoRepository.findById(puntoOrigenId)
                .orElseThrow(() -> new RuntimeException("Punto de origen no encontrado."));

        Servicio servicio = new Servicio();
        servicio.setTarifa_fija(calcularTarifa(nivel, distancia));
        servicio.setDistancia_recorrida(distancia);
        servicio.setHora_incio(LocalDateTime.now());
        servicio.setHora_fin(LocalDateTime.now().plusMinutes(30));
        servicio.setP_Punto_id(puntoOrigen);

        servicioRepository.save(servicio);

        if (forzarError)
            throw new RuntimeException("Error forzado para probar rollback.");

        return servicio;
    }

    private Integer calcularTarifa(String nivel, Integer distancia) {
        int base = 2000;
        if ("CONFORT".equalsIgnoreCase(nivel)) base = 2500;
        if ("LARGE".equalsIgnoreCase(nivel)) base = 3000;
        return base * distancia;
    }
    
}
