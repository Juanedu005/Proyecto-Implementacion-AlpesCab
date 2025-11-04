package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.Mercancia;
import uniandes.edu.co.proyecto.repositorio.MercanciaRepository;

@Controller
public class MercanciaController {
    
    @Autowired
    private MercanciaRepository mercanciaRepository;

    // LISTAR
    @GetMapping("/mercancias")
    public String mercancias(Model model){
        model.addAttribute("mercancias", mercanciaRepository.darMercancias());
        return "mercancias"; // nombre de la vista
    }

    // FORM NUEVO
    @GetMapping("/mercancias/new")
    public String mercanciaForm(Model model){
        model.addAttribute("mercancia", new Mercancia());
        return "mercanciaNuevo";   
    }

    // GUARDAR NUEVO
    @PostMapping("/mercancias/new/save")
    public String mercanciaGuardar(@ModelAttribute Mercancia mercancia,
                                   @RequestParam("servicioId") int servicioId) {
        // El repo nativo exige pasar el ID del servicio explícito
        mercanciaRepository.insertarMercancia(servicioId, mercancia.getElementoRecogido());
        return "redirect:/mercancias";
    }

    // FORM EDITAR (GET)
    @GetMapping("/mercancias/{id}/edit")
    public String mercanciaEditarForm(@PathVariable("id") int id, Model model){
        Optional<Mercancia> opt = mercanciaRepository.darMercancia(id);
        if (opt.isPresent()) {
            model.addAttribute("mercancia", opt.get());
            model.addAttribute("servicioId", id); // útil si el form lo necesita como hidden
            return "mercanciaEditar";
        } else {
            return "redirect:/mercancias";
        }
    }

    // GUARDAR EDICIÓN (POST)
    @PostMapping("/mercancias/{id}/edit/save")
    public String mercanciaEditarGuardar(@PathVariable("id") int id,
                                         @ModelAttribute Mercancia mercancia) {
        mercanciaRepository.actualizarMercancia(id, mercancia.getElementoRecogido());
        return "redirect:/mercancias";
    }

    // ELIMINAR
    @GetMapping("/mercancias/{id}/delete")
    public String mercanciaEliminar(@PathVariable("id") int id){
        mercanciaRepository.eliminarMercancia(id);
        return "redirect:/mercancias";
    }
}
