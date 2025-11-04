package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Pasajeros;
import uniandes.edu.co.proyecto.repositorio.PasajerosRepository;

@Controller
public class PasajerosController {

    @Autowired
    private PasajerosRepository pasajerosRepository;

    @GetMapping("/pasajeros")
    public String listar(Model model) {
        model.addAttribute("pasajeros", pasajerosRepository.darPasajeros());
        return "pasajeros"; // nombre de la vista
    }

    @GetMapping("/pasajeros/new")
    public String formNuevo(Model model) {
        model.addAttribute("pasajero", new Pasajeros());
        return "pasajerosNuevo";
    }

    @PostMapping("/pasajeros/new/save")
    public String guardarNuevo(@RequestParam("servicioId") int servicioId,
                               @RequestParam("nivel") String nivel) {
        pasajerosRepository.insertarPasajero(servicioId, nivel);
        return "redirect:/pasajeros";
    }

    @GetMapping("/pasajeros/{id}/edit")
    public String formEditar(@PathVariable("id") int id, Model model) {
        Optional<Pasajeros> opt = pasajerosRepository.darPasajero(id);
        if (opt.isEmpty()) return "redirect:/pasajeros";
        model.addAttribute("pasajero", opt.get());
        model.addAttribute("servicioId", id);
        return "pasajerosEditar";
    }

    @PostMapping("/pasajeros/{id}/edit/save")
    public String guardarEdicion(@PathVariable("id") int id,
                                 @RequestParam("nivel") String nivel) {
        pasajerosRepository.actualizarPasajero(id, nivel);
        return "redirect:/pasajeros";
    }

    @GetMapping("/pasajeros/{id}/delete")
    public String eliminar(@PathVariable("id") int id) {
        pasajerosRepository.eliminarPasajero(id);
        return "redirect:/pasajeros";
    }
}
