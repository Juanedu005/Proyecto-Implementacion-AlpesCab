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
    public String franjas(Model model) {
        model.addAttribute("franjas", franjaRepository.darFranjas());
        return "franjas"; // <- antes retornaba model.toString()
    }

    @GetMapping("/franjas/new")
    public String franjaForm(Model model) {
        model.addAttribute("franja", new Franja());
        return "franjaNuevo";
    }

    @PostMapping("/franjas/new/save")
    public String franjaGuardar(@ModelAttribute Franja franja) {
        Integer vehiculoId   = (franja.getVehiculo()   != null) ? franja.getVehiculo().getId() : null;
        Integer idConductor  = (franja.getUconductor() != null) ? franja.getUconductor().getPk().getId_conductor() : null;
        Integer idUsuarioC   = (franja.getUconductor() != null) ? franja.getUconductor().getPk().getId_usuario()   : null;

        franjaRepository.insertarFranja(
            franja.getHora_incio(),
            franja.getHora_fin(),
            franja.getOcupado(),
            vehiculoId,
            idConductor,
            idUsuarioC
        );
        return "redirect:/franjas";
    }

    @PostMapping("/franjas/{id}/edit")
    public String franjaEditarForm(@PathVariable("id") int id, Model model) {
        Franja franja = franjaRepository.darFranja(id);
        if (franja != null) {
            model.addAttribute("franja", franja);
            return "franjaEditar";
        } else {
            return "redirect:/franjas";
        }
    }

    @PostMapping("/franjas/{id}/edit/save")
    public String franjaEditarGuardar(@PathVariable("id") int id, @ModelAttribute Franja franja) {
        Integer vehiculoId   = (franja.getVehiculo()   != null) ? franja.getVehiculo().getId() : null;
        Integer idConductor  = (franja.getUconductor() != null) ? franja.getUconductor().getPk().getId_conductor() : null;
        Integer idUsuarioC   = (franja.getUconductor() != null) ? franja.getUconductor().getPk().getId_usuario()   : null;

        franjaRepository.actualizarFranja(
            id,
            franja.getHora_incio(),
            franja.getHora_fin(),
            franja.getOcupado(),
            vehiculoId,
            idConductor,
            idUsuarioC
        );
        return "redirect:/franjas";
    }

    @GetMapping("/franjas/{id}/delete")
    public String franjaEliminar(@PathVariable("id") int id) {
        franjaRepository.eliminarFranja(id);
        return "redirect:/franjas";
    }
}
