package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Franja;
import uniandes.edu.co.proyecto.repositorio.FranjaRepository;

@Controller
public class FranjaController {

    @Autowired
    private FranjaRepository franjaRepository;

    @GetMapping("/franjas")
    public String franjas(Model model) {
        model.addAttribute("franjas", franjaRepository.darFranjas());
        return "franjas"; 
    }

    
    @GetMapping("/franjas/new")
    public String franjaForm(Model model) {
        model.addAttribute("franja", new Franja());
        return "franjaNuevo";
    }

    @PostMapping("/franjas/new/save")
    public String franjaGuardar(
            @ModelAttribute Franja franja,
            @RequestParam("vehiculoId") int vehiculoId,
            @RequestParam("ucondIdcond") int ucondIdcond,
            @RequestParam("ucondIdusuario") int ucondIdusuario) {

      
        franjaRepository.insertarFranja(
                franja.getHoraInicio(),
                franja.getHoraFin(),
                franja.getOcupado(), 
                vehiculoId,
                ucondIdcond,
                ucondIdusuario
        );
        return "redirect:/franjas";
    }


    @GetMapping("/franjas/{id}/edit")
    public String franjaEditarForm(@PathVariable("id") int id, Model model) {
        Optional<Franja> franjaOpt = franjaRepository.darFranja(id);
        if (franjaOpt.isPresent()) {
            model.addAttribute("franja", franjaOpt.get());
        
            return "franjaEditar";
        }
        return "redirect:/franjas";
    }

    
    @PostMapping("/franjas/{id}/edit/save")
    public String franjaEditarGuardar(
            @PathVariable("id") int id,
            @ModelAttribute Franja franja,
            @RequestParam("vehiculoId") int vehiculoId,
            @RequestParam("ucondIdcond") int ucondIdcond,
            @RequestParam("ucondIdusuario") int ucondIdusuario) {

        franjaRepository.actualizarFranja(
                id,
                franja.getHoraInicio(),
                franja.getHoraFin(),
                franja.getOcupado(),
                vehiculoId,
                ucondIdcond,
                ucondIdusuario
        );
        return "redirect:/franjas";
    }

 
    @GetMapping("/franjas/{id}/delete")
    public String franjaEliminar(@PathVariable("id") int id) {
        franjaRepository.eliminarFranja(id);
        return "redirect:/franjas";
    }
}
