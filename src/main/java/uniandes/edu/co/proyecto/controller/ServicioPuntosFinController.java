package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import uniandes.edu.co.proyecto.modelo.ServicioPuntosFin;
import uniandes.edu.co.proyecto.repositorio.ServicioPuntosFinRepository;
;

@Controller
public class ServicioPuntosFinController {
    
    @Autowired
    private ServicioPuntosFinRepository servicioPuntosFinRepository;

    @GetMapping("/serviciopuntosfins")
    public String serviciopuntosfins(Model model){
        model.addAttribute("serviciopuntosfins", servicioPuntosFinRepository.darServiciosPuntosFins());
        return model.toString();
    }

    @GetMapping("/serviciopuntosfins/new")
    public String serviciopuntosfinForm(Model model){
        model.addAttribute("serviciopuntosfin", new ServicioPuntosFin());
        return "serviciopuntosfinNuevo";
    }
    
    @PostMapping("/serviciopuntosfins/new/save")
    public String serviciopuntosfinGuardar(@ModelAttribute ServicioPuntosFin serviciopuntosfin){
        servicioPuntosFinRepository.insertarServiciosPuntosFin(serviciopuntosfin.getPk().getServicio_id().getId(), serviciopuntosfin.getPk().getP_Punto_id().getPunto_id());
        return "redirect:/serviciopuntosfins";
    }

    @PostMapping("serviciopuntosfins/{Servicios_id}/{P_Punto_id}/edit")
    public String serviciopuntosfinEditarForm(@PathVariable("Servicios_id") int Servicios_id, @PathVariable("P_Punto_id") int P_Punto_id, Model model){
        ServicioPuntosFin serviciopuntosfin = servicioPuntosFinRepository.darServiciosPuntosFin(Servicios_id, P_Punto_id);
        
        if (serviciopuntosfin != null) {
            model.addAttribute("serviciopuntosfin", serviciopuntosfin);
            return "serviciopuntosfinEditar";
        } else {
            return "redirect:/serviciopuntosfins";
        }
    }

    @PostMapping("serviciopuntosfins/{Servicios_id}/{P_Punto_id}/edit/save")
    public String serviciopuntosfinEditarGuardar(@PathVariable("Servicios_id") int Servicios_id, @PathVariable("P_Punto_id") int P_Punto_id, @ModelAttribute ServicioPuntosFin serviciopuntosfin){

        servicioPuntosFinRepository.actualizarServiciosPuntosFin(Servicios_id, P_Punto_id);
        return "redirect:/serviciopuntosfins";
    }

    @GetMapping("serviciopuntosfins/{Servicios_id}/{P_Punto_id}/delete")
    public String serviciopuntosfinEliminar(@PathVariable("Servicios_id") int Servicios_id, @PathVariable("P_Punto_id") int P_Punto_id){
        servicioPuntosFinRepository.eliminarServiciosPuntosFin(Servicios_id, P_Punto_id);
        return "redirect:/serviciopuntosfins";
    }
    

}
