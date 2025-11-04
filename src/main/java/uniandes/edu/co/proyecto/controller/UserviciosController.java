package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.modelo.UserviciosPK;
import uniandes.edu.co.proyecto.repositorio.UserviciosRepository;

@Controller
public class UserviciosController {

    @Autowired
    private UserviciosRepository userviciosRepository;

    /* ===== LISTAR ===== */
    @GetMapping("/uservicios")
    public String uservicios(Model model) {
        model.addAttribute("uservicios", userviciosRepository.darUservicios());
        return "uservicios"; // ← nombre de la vista (no model.toString)
    }

    /* ===== FORM NUEVO ===== */
    @GetMapping("/uservicios/new")
    public String uservicioForm(Model model) {
        model.addAttribute("uservicio", new Uservicios());
        return "uservicioNuevo";
    }

    /* ===== GUARDAR NUEVO ===== */
    @PostMapping("/uservicios/new/save")
    public String uservicioGuardar(@ModelAttribute Uservicios uservicio) {

        // Solo pasamos id_usuario (id_servicios se genera con la secuencia)
        Integer idUsuario = (uservicio.getPk() != null) ? uservicio.getPk().getId_usuario() : null;

        userviciosRepository.insertarUservicio(
            idUsuario,
            uservicio.getNombre_tc(),
            uservicio.getNumero_tc(),
            uservicio.getFecha_vencimiento(),
            uservicio.getCv()
        );

        return "redirect:/uservicios";
    }

    /* ===== FORM EDITAR ===== */
    @GetMapping("/uservicios/{id_servicios}/{id_usuario}/edit")
    public String uservicioEditarForm(@PathVariable("id_servicios") int idServicios,
                                      @PathVariable("id_usuario") int idUsuario,
                                      Model model) {

        Optional<Uservicios> uservicioOpt = userviciosRepository.darUservicio(idUsuario, idServicios);

        if (uservicioOpt.isPresent()) {
            model.addAttribute("uservicio", uservicioOpt.get());
            return "uservicioEditar";
        } else {
            return "redirect:/uservicios";
        }
    }

    /* ===== GUARDAR EDICIÓN ===== */
    @PostMapping("/uservicios/{id_servicios}/{id_usuario}/edit/save")
    public String uservicioEditarGuardar(@PathVariable("id_servicios") int idServicios,
                                         @PathVariable("id_usuario") int idUsuario,
                                         @ModelAttribute Uservicios uservicio) {

        userviciosRepository.actualizarUservicio(
            idUsuario,
            idServicios,
            uservicio.getNombre_tc(),
            uservicio.getNumero_tc(),
            uservicio.getFecha_vencimiento(),
            uservicio.getCv()
        );

        return "redirect:/uservicios";
    }

    /* ===== ELIMINAR ===== */
    @GetMapping("/uservicios/{id_servicios}/{id_usuario}/delete")
    public String uservicioEliminar(@PathVariable("id_servicios") int idServicios,
                                    @PathVariable("id_usuario") int idUsuario) {

        userviciosRepository.eliminarUservicio(idUsuario, idServicios);
        return "redirect:/uservicios";
    }
}
