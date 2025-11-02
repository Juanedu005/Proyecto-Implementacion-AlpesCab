package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Domicilio;
import uniandes.edu.co.proyecto.repositorio.DomicilioRepository;

@Controller 
public class DomicilioController {
    
    @Autowired
    private DomicilioRepository domicilioRepository;

    @GetMapping("/domicilios")
    public String domicilios(Model model){
        model.addAttribute("domicilios", domicilioRepository.darDomicilios());
        return model.toString();
    }

    @GetMapping("/domicilios/new")
    public String domicilioForm(Model model){
        model.addAttribute("domicilio", new Object());
        return "domicilioNuevo";
    }

    @PostMapping("/domicilios/new/save")
    public String domicilioGuardar(@ModelAttribute Domicilio domicilio){
        domicilioRepository.insertarDomicilio(domicilio.getNombre_restaurante(), domicilio.getOrden());
        return "redirect:/domicilios";
    }

    @PostMapping("/domicilios/{Servicio_id}/edit")
    public String domicilioEditarForm(@PathVariable("Servicio_id") int Servicio_id, Model model){
        Domicilio domicilio = domicilioRepository.darDomicilio(Servicio_id);
        
        if (domicilio != null) {
            model.addAttribute("domicilio", domicilio);
            return "domicilioEditar";
        } else {
            return "redirect:/domicilios";
        }
    }

    @PostMapping("/domicilios/{Servicio_id}/edit/save")
    public String domicilioEditarGuardar(@PathVariable("Servicio_id") int Servicio_id, @ModelAttribute Domicilio domicilio){

        domicilioRepository.actualizarDomicilio(Servicio_id, domicilio.getNombre_restaurante(), domicilio.getOrden());
        return "redirect:/domicilios";
    
}

    @GetMapping("/domicilios/{Servicio_id}/delete")
    public String domicilioEliminar(@PathVariable("Servicio_id") int Servicio_id){
        domicilioRepository.eliminarDomicilio(Servicio_id);
        return "redirect:/domicilios";
    
}
}