
package uniandes.edu.co.proyecto.servicio;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.modelo.Punto;
import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.modelo.UserviciosPK;
import uniandes.edu.co.proyecto.modelo.Usuario;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;
import uniandes.edu.co.proyecto.repositorio.PuntoRepository;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;
import uniandes.edu.co.proyecto.repositorio.UserviciosRepository;

@Service
public class SolicitudService {

    private final ServicioRepository servicioRepo;
    private final PuntoRepository puntoRepo;
    private final UsuarioRepository usuarioRepo;
    private final UserviciosRepository userviciosRepo;

    public SolicitudService(ServicioRepository servicioRepo,
                            PuntoRepository puntoRepo,
                            UsuarioRepository usuarioRepo,
                            UserviciosRepository userviciosRepo) {
        this.servicioRepo = servicioRepo;
        this.puntoRepo = puntoRepo;
        this.usuarioRepo = usuarioRepo;
        this.userviciosRepo = userviciosRepo;
    }

    /**
     * RF8: crea un servicio y relaciona al usuario con el servicio dentro de una
     * única transacción. Si ocurre cualquier excepción, se hace rollback automático.
     *
     * @param usuarioId  id del usuario que solicita
     * @param puntoOrigenId id del punto (o se podría crear un nuevo punto)
     * @param tarifaFija tarifa calculada previamente
     * @param distancia  distancia recorrida (estimada)
     * @param forzarError si es true, se lanza un error para verificar rollback
     */
    @Transactional(rollbackFor = Exception.class)
    public Servicio solicitarServicioRF8(
            int usuarioId,
            int puntoOrigenId,
            int tarifaFija,
            int distancia,
            boolean forzarError) {

        // 1) validar usuario
        Usuario usuario = usuarioRepo.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + usuarioId));

        // 2) leer/validar punto
        Punto origen = puntoRepo.findById(puntoOrigenId)
            .orElseThrow(() -> new IllegalArgumentException("Punto no existe: " + puntoOrigenId));

        // 3) crear servicio (usamos JPA save para obtener el id)
        Servicio s = new Servicio();
        s.setTarifa_fija(tarifaFija);
        s.setDistancia_recorrida(distancia);
        s.setHora_incio(LocalDateTime.now());
        s.setHora_fin(null); // aún no finaliza
        s.setP_Punto_id(origen);

        s = servicioRepo.save(s); // id poblado

        // 4) crear enlace en USERVICIOS (histórico/medio de pago)
        Uservicios u = new Uservicios();
        UserviciosPK pk = new UserviciosPK();
        pk.setId_usuario(usuario.getId());
        pk.setId_servicios(s.getId());
        u.setPk(pk);
        u.setUsuario(usuario);
        u.setServicio(s);
        // NOTA: si tu USERVICIOS tiene columnas de tarjeta, setéalas aquí desde parámetros/DTO
        userviciosRepo.save(u);

        if (forzarError) {
            // simula un error para comprobar que TODO revierte
            throw new RuntimeException("Error forzado para probar rollback RF8");
        }

        return s; // commit implícito al salir sin excepciones
    }
}
