package uniandes.edu.co.proyecto.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@Controller
public class ServicioController {
    
    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/servicios")
    public String servicios(Model model){
        model.addAttribute("servicios", servicioRepository.darServicios());
        return model.toString();
    }

    @GetMapping("/servicios/new")
    public String servicioForm(Model model){
        model.addAttribute("servicio", new Servicio());
        return "servicioNuevo";
    }

    @PostMapping("/servicios/new/save")
    public String servicioGuardar(@ModelAttribute Servicio servicio){
        servicioRepository.insertarServicio(servicio.getTarifa_fija(), servicio.getDistancia_recorrida(), servicio.getHora_incio(), servicio.getHora_fin(), servicio.getP_Punto_id().getPunto_id(), servicio.getUser_idser().getPk().getId_servicios(), servicio.getUser_idusuario().getPk().getId_usuario().getId());
        return "redirect:/servicios";
    }

    @PostMapping("/servicios/{id}/edit")
    public String servicioEditarForm(@PathVariable("id") int id, Model model){
        Servicio servicio = servicioRepository.darServicio(id);
        
        if (servicio != null) {
            model.addAttribute("servicio", servicio);
            return "servicioEditar";
        } else {
            return "redirect:/servicios";
        }
    }

    @PostMapping("/servicios/{id}/edit/save")
    public String servicioEditarGuardar(@PathVariable("id") int id, @ModelAttribute Servicio servicio){

        servicioRepository.actualizarServicio(id, servicio.getTarifa_fija(), servicio.getDistancia_recorrida(), servicio.getHora_incio(), servicio.getHora_fin(), servicio.getP_Punto_id().getPunto_id(), servicio.getUser_idser().getPk().getId_servicios(), servicio.getUser_idusuario().getPk().getId_usuario().getId());
        return "redirect:/servicios";
    }

    @GetMapping("/servicios/{id}/delete")
    public String servicioEliminar(@PathVariable("id") int id){
        servicioRepository.eliminarServicio(id);
        return "redirect:/servicios";
    }

    // ========================= RFC1 =========================
    // Histórico de servicios de un usuario de servicios 
    @GetMapping("/servicios/{idUsuario}/{idServicio}/historial")
    public String rfc1HistoricoPorUsuario(
            @PathVariable("id_usuario") int idUsuario,
            @PathVariable("id_conductor") int idServicio,
            Model model) {

        model.addAttribute("historial",
            servicioRepository.rfc1HistoricoPorUsuario(idUsuario, idServicio));

        // Vista sugerida (ajústala a tu template real)
        return "serviciosHistorial";
    }

    // ========================= RFC4 =========================
    @GetMapping("/servicios/uso")
    public String rfc4UtilizacionPorCiudadYRango(
            @RequestParam("ciudad_id") String ciudad,
            @RequestParam("hora_incio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaIni,
            @RequestParam("hora_fin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            Model model) {

        model.addAttribute("usoPorTipo",
            servicioRepository.rfc4UtilizacionPorCiudadYRango(
                ciudad,
                fechaIni,
                fechaFin
            )
        );

        return "serviciosUsoCiudad"; // <-- aquí está el return de la vista
    }

}
