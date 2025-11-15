package uniandes.edu.co.proyecto.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.dto.RFC1Request;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@Service
public class RFC1Service {

    private final ServicioRepository servicioRepo;

    public RFC1Service(ServicioRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    /**
     * RFC1 en nivel READ COMMITTED
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> ejecutarReadCommitted(RFC1Request req) {

        // 1. Consulta ANTES del temporizador
        List<Servicio> antes = servicioRepo.rfc1HistoricoPorUsuario(
                req.getIdUsuario(),
                req.getIdServicio()
        );

        // 2. Temporizador de 30 segundos
        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 3. Consulta DESPUÉS del temporizador
        List<Servicio> despues = servicioRepo.rfc1HistoricoPorUsuario(
                req.getIdUsuario(),
                req.getIdServicio()
        );

        // 4. Armamos la respuesta para que puedas comparar en el informe
        Map<String, Object> resp = new HashMap<>();
        resp.put("nivelAislamiento", "READ_COMMITTED");
        resp.put("antes", antes);
        resp.put("despues", despues);

        return resp;
    }

    /**
     * RFC1 en nivel SERIALIZABLE
     */
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> ejecutarSerializable(RFC1Request req) {

        List<Servicio> antes = servicioRepo.rfc1HistoricoPorUsuario(
                req.getIdUsuario(),
                req.getIdServicio()
        );

        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<Servicio> despues = servicioRepo.rfc1HistoricoPorUsuario(
                req.getIdUsuario(),
                req.getIdServicio()
        );

        Map<String, Object> resp = new HashMap<>();
        resp.put("nivelAislamiento", "SERIALIZABLE");
        resp.put("antes", antes);
        resp.put("despues", despues);

        return resp;
    }
}
