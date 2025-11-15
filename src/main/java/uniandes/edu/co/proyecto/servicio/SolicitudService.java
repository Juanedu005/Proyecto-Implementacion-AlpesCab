/* */
package uniandes.edu.co.proyecto.servicio;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.dto.SolicitudDTO;
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

    

    //RF8
    @Transactional(rollbackFor = Exception.class)
    public Servicio solicitarServicioRF8(
            int usuarioId,
            int puntoOrigenId,
            int tarifaFija,
            int distancia,
            boolean forzarError) {

  
        Usuario usuario = usuarioRepo.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + usuarioId));

     
        Punto origen = puntoRepo.findById(puntoOrigenId)
            .orElseThrow(() -> new IllegalArgumentException("Punto no existe: " + puntoOrigenId));

        Servicio s = new Servicio();
        s.setTarifa_fija(tarifaFija);
        s.setDistancia_recorrida(distancia);
        s.setHora_incio(LocalDateTime.now());
        s.setHora_fin(null); 
        s.setP_Punto_id(origen);

        s = servicioRepo.save(s);

        Uservicios u = new Uservicios();
        UserviciosPK pk = new UserviciosPK();
        pk.setId_usuario(usuario.getId());
        pk.setId_servicios(s.getId());
        u.setPk(pk);
        u.setUsuario(usuario);
        //u.setServicio(s);
        userviciosRepo.save(u);

        if (forzarError) {
            throw new RuntimeException("Error forzado para probar rollback RF8");
        }

        return s; 
    }


}
