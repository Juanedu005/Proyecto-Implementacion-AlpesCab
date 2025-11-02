package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import uniandes.edu.co.proyecto.modelo.Franja;
import uniandes.edu.co.proyecto.repositorio.FranjaRepository;

@Controller
public class FranjaController {
    
    @Autowired
    private FranjaRepository franjaRepository;
    
    @GetMapping("/franjas")
    public String franjas(Model model){
        model.addAttribute("franjas", franjaRepository.darFranjas());
        return model.toString();
    }

    @GetMapping("/franjas/new")
    public String franjaForm(Model model){
        model.addAttribute("franja", new Franja());
        return "franjaNuevo";   
    }

    @PostMapping("/franjas/new/save")
    public String franjaGuardar(@ModelAttribute Franja franja){
        
        franjaRepository.insertarFranja(franja.getHora_incio(), franja.getHora_fin(),
        franja.getOcupado(), franja.getVehiculo_id().getId(), franja.getUcond_idcond().getPk().getId_conductos(), franja.getUcond_idusuario().getPk().getId_usuario().getId());
        return "redirect:/franjas";
    }
    

    @PostMapping("/franjas/{id}/edit")
    public String franjaEditarForm(@PathVariable("id_franja") int id_franja, @ModelAttribute Franja franja){
        franjaRepository.actualizarFranja(id_franja, franja.getHora_incio(), franja.getHora_fin(), franja.getOcupado(), franja.getVehiculo_id().getId(), franja.getUcond_idcond().getPk().getId_conductos(), franja.getUcond_idusuario().getPk().getId_usuario().getId());
        return "redirect:/franjas"; 
    }

    @GetMapping("/franjas/{id}/delete")
    public String franjaEliminar(@PathVariable("id_franja") int id_franja){
        franjaRepository.eliminarFranja(id_franja);
        return "redirect:/franjas";
    }


}
