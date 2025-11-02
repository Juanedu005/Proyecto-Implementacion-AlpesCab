package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Mercancia;
import uniandes.edu.co.proyecto.modelo.Pasajeros;
import uniandes.edu.co.proyecto.repositorio.PasajerosRepository;

@Controller
public class PasajerosController {
    

    @Autowired
    private PasajerosRepository pasajerosRepository;

    @GetMapping("/pasajeros")
    public String pasajeros(Model model){
        model.addAttribute("pasajeros", pasajerosRepository.darPasajeros());
        return model.toString();
    }

    @GetMapping("/pasajeros/new")
    public String pasajeroForm(Model model){
        model.addAttribute("pasajero", new Mercancia());
        return "pasajeroNuevo";
    }

    @PostMapping("/pasajeros/new/save")
    public String pasajeroGuardar(@ModelAttribute Pasajeros pasajero){
        pasajerosRepository.insertarPasajero(pasajero.getNivel());
        return "redirect:/pasajeros";
    }

    @PostMapping("/pasajeros/{Servicio_id}/edit")
    public String pasajeroEditarForm(@PathVariable("Servicio_id") int Servicio_id, Model model){
        Pasajeros pasajero = pasajerosRepository.darPasajero(Servicio_id);
        
        if (pasajero != null) {
            model.addAttribute("pasajero", pasajero);
            return "pasajeroEditar";
        } else {
            return "redirect:/pasajeros";
        }
    }

    @PostMapping("/pasajeros/{Servicio_id}/edit/save")
    public String pasajeroEditarGuardar(@PathVariable("Servicio_id") int Servicio_id, @ModelAttribute Pasajeros pasajero){

        pasajerosRepository.actualizarPasajero(Servicio_id, pasajero.getNivel());
        return "redirect:/pasajeros";
    }

    @GetMapping("/pasajeros/{Servicio_id}/delete")
    public String pasajeroEliminar(@PathVariable("Servicio_id") int Servicio_id){
        pasajerosRepository.eliminarPasajero(Servicio_id);
        return "redirect:/pasajeros";
    }

    
}
