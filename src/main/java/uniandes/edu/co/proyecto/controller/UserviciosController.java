package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Uservicios;
import uniandes.edu.co.proyecto.repositorio.UserviciosRepository;

@Controller
public class UserviciosController {
    
    @Autowired
    private UserviciosRepository userviciosRepository;

    @GetMapping("/uservicios")
    public String uservicios(Model model){
        model.addAttribute("uservicios", userviciosRepository.darUservicios());
        return model.toString();
    }

    @GetMapping("/uservicios/new")
    public String uservicioForm(Model model){
        model.addAttribute("uservicio", new Uservicios());
        return "uservicioNuevo";
    }

    @PostMapping("/uservicios/new/save")
    public String uservicioGuardar(@ModelAttribute Uservicios uservicio){
        userviciosRepository.insertarUservicio(uservicio.getPk().getId_servicios(), uservicio.getPk().getId_usuario(), uservicio.getNombre_tc(), uservicio.getNumero_tc(), uservicio.getFecha_vencimiento(), uservicio.getCv());
        return "redirect:/uservicios";
    }

    @PostMapping("/uservicios/{id_servicios}/{id_usuario}/edit")
    public String uservicioEditarForm(@PathVariable("id_servicios") int id_servicios, @PathVariable("id_usuario") int id_usuario, Model model){
        Uservicios uservicio = userviciosRepository.darUservicio(id_servicios, id_usuario);
        
        if (uservicio != null) {
            model.addAttribute("uservicio", uservicio);
            return "uservicioEditar";
        } else {
            return "redirect:/uservicios";
        }
    }


    @PostMapping("/uservicios/{id_servicios}/{id_usuario}/edit/save")
    public String uservicioEditarGuardar(@PathVariable("id_servicios") int id_servicios, @PathVariable("id_usuario") int id_usuario, @ModelAttribute Uservicios uservicio){

        userviciosRepository.actualizarUservicio(id_servicios, id_usuario, uservicio.getNombre_tc(), uservicio.getNumero_tc(), uservicio.getFecha_vencimiento(), uservicio.getCv());
        return "redirect:/uservicios";
    }


    @GetMapping("/uservicios/{id_servicios}/{id_usuario}/delete")
    public String uservicioEliminar(@PathVariable("id_servicios") int id_servicios, @PathVariable("id_usuario") int id_usuario){
        userviciosRepository.eliminarUservicio(id_servicios, id_usuario);
        return "redirect:/uservicios";
    }


    
}
