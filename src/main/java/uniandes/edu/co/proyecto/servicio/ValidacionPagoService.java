package uniandes.edu.co.proyecto.servicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.repositorio.UsuarioRepository;
import uniandes.edu.co.proyecto.repositorio.UserviciosRepository;

@Service
public class ValidacionPagoService {

    private final UsuarioRepository usuarioRepository;
    private final UserviciosRepository userviciosRepository;

    public ValidacionPagoService(UsuarioRepository usuarioRepository,
                                 UserviciosRepository userviciosRepository) {
        this.usuarioRepository = usuarioRepository;
        this.userviciosRepository = userviciosRepository;
    }

    @Transactional(readOnly = true)
    public Resultado validarMedioPago(Integer usuarioId) {
        var usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return Resultado.noEncontrado("Usuario no encontrado");
        }
        boolean tiene = (userviciosRepository.hasValidPayment(usuarioId) == 1);
        if (tiene) return Resultado.ok();
        return Resultado.fallo("El usuario no tiene un medio de pago vigente (o no tiene tarjetas registradas).");
    }

    public static class Resultado {
        public final boolean encontrado;
        public final boolean valido;
        public final String motivo;

        private Resultado(boolean encontrado, boolean valido, String motivo) {
            this.encontrado = encontrado;
            this.valido = valido;
            this.motivo = motivo;
        }
        public static Resultado ok() { return new Resultado(true, true, null); }
        public static Resultado fallo(String m) { return new Resultado(true, false, m); }
        public static Resultado noEncontrado(String m) { return new Resultado(false, false, m); }
    }
}
