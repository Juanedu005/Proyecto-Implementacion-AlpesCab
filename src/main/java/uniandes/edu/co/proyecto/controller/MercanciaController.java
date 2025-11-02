package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Mercancia;
import uniandes.edu.co.proyecto.repositorio.MercanciaRepository;

@Controller
public class MercanciaController {
    
    @Autowired
    private MercanciaRepository mercanciaRepository;

    @GetMapping("/mercancias")
    public String mercancias(Model model){
        model.addAttribute("mercancias", mercanciaRepository.darMercancias());
        return model.toString();
    }

    @GetMapping("/mercancias/new")
    public String mercanciaForm(Model model){
        model.addAttribute("mercancia", new Mercancia());
        return "mercanciaNuevo";   
    }

    @PostMapping("/mercancias/new/save")
    public String mercanciaGuardar(@ModelAttribute Mercancia mercancia){
        mercanciaRepository.insertarMercancia(mercancia.getElementoRecogido());
        return "redirect:/mercancias";
    }

    @PostMapping("/mercancias/{Servicio_id}/edit")
    public String mercanciaEditarForm(@PathVariable("Servicio_id") int Servicio_id, Model model){
        Mercancia mercancia = mercanciaRepository.darMercancia(Servicio_id);
        
        if (mercancia != null) {
            model.addAttribute("mercancia", mercancia);
            return "mercanciaEditar";
        } else {
            return "redirect:/mercancias";
        }
    }

    @PostMapping("/mercancias/{Servicio_id}/edit/save")
    public String mercanciaEditarGuardar(@PathVariable("Servicio_id") int Servicio_id, @ModelAttribute Mercancia mercancia){

        mercanciaRepository.actualizarMercancia(Servicio_id, mercancia.getElementoRecogido());
        return "redirect:/mercancias";
    }

    @GetMapping("/mercancias/{Servicio_id}/delete")
    public String mercanciaEliminar(@PathVariable("Servicio_id") int Servicio_id){
        mercanciaRepository.eliminarMercancia(Servicio_id);
        return "redirect:/mercancias";
    }

    
}

