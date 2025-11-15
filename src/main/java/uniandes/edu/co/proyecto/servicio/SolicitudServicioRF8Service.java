package uniandes.edu.co.proyecto.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;

//import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.dto.ServicioRF8Response;
import uniandes.edu.co.proyecto.dto.SolicitudServicioRF8Request;
import uniandes.edu.co.proyecto.modelo.*;
import uniandes.edu.co.proyecto.repositorio.*;
import java.util.List;

@Service
public class SolicitudServicioRF8Service {

    private final UserviciosRepository userviciosRepo;
    private final UsuarioRepository usuarioRepo;
    private final PuntoRepository puntoRepo;
    private final FranjaRepository franjaRepo;
    private final ServicioRepository servicioRepo;
    private final ServicioPuntosFinRepository servicioPuntosFinRepo;

    public SolicitudServicioRF8Service(UserviciosRepository userviciosRepo,
                                       UsuarioRepository usuarioRepo,
                                       PuntoRepository puntoRepo,
                                       FranjaRepository franjaRepo,
                                       ServicioRepository servicioRepo,
                                       ServicioPuntosFinRepository servicioPuntosFinRepo) {
        this.userviciosRepo = userviciosRepo;
        this.usuarioRepo = usuarioRepo;
        this.puntoRepo = puntoRepo;
        this.franjaRepo = franjaRepo;
        this.servicioRepo = servicioRepo;
        this.servicioPuntosFinRepo = servicioPuntosFinRepo;
    }

    @Transactional
    public ServicioRF8Response solicitarServicio(SolicitudServicioRF8Request req) {
    
        LocalDateTime ahora = LocalDateTime.now();
    
        // 1. Validar medio de pago
        List<Uservicios> medios = userviciosRepo
        .encontrarMediosPagoVigentes(req.getIdUsuarioServicio());

        if (medios.isEmpty()) {
            throw new IllegalArgumentException("El usuario no tiene un medio de pago vigente");
        }

        // Por ejemplo, tomamos el primero (el que vence más pronto)
        Uservicios medioPago = medios.get(0);

    
        if (medioPago.getFecha_vencimiento().toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La tarjeta está vencida");
        }
    
        // 2. Buscar franja disponible
        Franja franja = franjaRepo
                .encontrarFranjaDisponible(req.getIdCiudad(), ahora)
                .orElseThrow(() -> new IllegalStateException(
                        "No hay conductores disponibles en la ciudad " + req.getIdCiudad()));
    
        // 3. Marcar franja como ocupada
        franja.setOcupado(1);
        franjaRepo.save(franja);
    
        // 4. Crear Servicio
        Servicio servicio = new Servicio();
    
        servicio.setTarifa_fija(
                calcularTarifa(req.getTipoServicio(), req.getNivelPasajero(), req.getDistanciaKm())
        );
    
        // si en la entidad es String, mejor así:
        servicio.setDistancia_recorrida(req.getDistanciaKm()); // o req.getDistanciaKm() + " km"
    
        servicio.setHora_incio(ahora);
        servicio.setHora_fin(ahora); // RF9 la actualizará
    
        Punto puntoOrigen = puntoRepo.findById(req.getIdPuntoOrigen())
                .orElseThrow(() -> new IllegalArgumentException("Punto origen no existe"));
        servicio.setP_Punto_id(puntoOrigen);
    
        servicio = servicioRepo.save(servicio);
    
        // 5. Registrar ServicioPuntosFin (destino)
        Punto puntoDestino = puntoRepo.findById(req.getIdPuntoDestino())
                .orElseThrow(() -> new IllegalArgumentException("Punto destino no existe"));
    
        ServicioPuntosFinPK spfPk = new ServicioPuntosFinPK(servicio, puntoDestino);
        ServicioPuntosFin spf = new ServicioPuntosFin(spfPk);
        servicioPuntosFinRepo.save(spf);
    
        // 6. Error forzado para rollback
        if (req.isForzarError()) {
            throw new RuntimeException("Rollback forzado en RF8");
        }
    
        // 7. Construir DTO de respuesta (sin tocar proxies raros)
        return new ServicioRF8Response(
                servicio.getId(),
                servicio.getTarifa_fija(),
                req.getDistanciaKm(),
                servicio.getHora_incio(),
                servicio.getHora_fin(),
                puntoOrigen.getPunto_id(),
                puntoDestino.getPunto_id(),
                puntoOrigen.getCiudad_id().getId()
        );
    }

    private int calcularTarifa(String tipoServicio, String nivel, Integer distanciaKm) {
        int porKm;

        if (tipoServicio.equalsIgnoreCase("PASAJEROS")) {
            if ("CONFORT".equalsIgnoreCase(nivel)) porKm = 2500;
            else if ("LARGE".equalsIgnoreCase(nivel)) porKm = 3000;
            else porKm = 2000;
        } else if (tipoServicio.equalsIgnoreCase("DOMICILIO")) {
            porKm = 1500;
        } else {
            porKm = 2200; // mercancía
        }

        return porKm * distanciaKm;
    }
}
