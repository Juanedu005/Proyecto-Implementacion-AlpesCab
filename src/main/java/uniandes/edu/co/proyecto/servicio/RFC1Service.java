
package uniandes.edu.co.proyecto.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@Service
public class RFC1Service {

    private final ServicioRepository servicioRepo;

    public RFC1Service(ServicioRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public RFC1Resultado consultarSerializable(int usuarioId) throws InterruptedException {
        List<Servicio> antes = servicioRepo.listarHistoricoUsuario(usuarioId);
        Thread.sleep(30_000);
        List<Servicio> despues = servicioRepo.listarHistoricoUsuario(usuarioId);
        return new RFC1Resultado("SERIALIZABLE", antes, despues);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public RFC1Resultado consultarReadCommitted(int usuarioId) throws InterruptedException {
        List<Servicio> antes = servicioRepo.listarHistoricoUsuario(usuarioId);
        Thread.sleep(30_000);
        List<Servicio> despues = servicioRepo.listarHistoricoUsuario(usuarioId);
        return new RFC1Resultado("READ_COMMITTED", antes, despues);
    }

    public static record RFC1Resultado(
        String aislamiento,
        List<Servicio> antes,
        List<Servicio> despues
    ) {}
}
