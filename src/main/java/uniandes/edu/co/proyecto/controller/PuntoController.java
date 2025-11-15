package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Punto;
import uniandes.edu.co.proyecto.repositorio.PuntoRepository;

@Controller
public class PuntoController {

    @Autowired
    private PuntoRepository puntoRepository;

 
    @GetMapping("/puntos")
    public String puntos(Model model) {
        model.addAttribute("puntos", puntoRepository.darPuntos());
        return "puntos"; 
    }

    
    @GetMapping("/puntos/new")
    public String puntoForm(Model model) {
        model.addAttribute("punto", new Punto());
        return "puntoNuevo";
    }


    @PostMapping("/puntos/new/save")
    public String puntoGuardar(@ModelAttribute Punto punto) {
        Integer servicioId = (punto.getServicio_id() != null) ? punto.getServicio_id().getId() : null;
        Integer ciudadId   = (punto.getCiudad_id()   != null) ? punto.getCiudad_id().getId()   : null;

        puntoRepository.insertarPunto(
            punto.getDireccion(),
            punto.getLatitud(),
            punto.getLongitud(),
            servicioId,
            ciudadId
        );
        return "redirect:/puntos";
    }

  
    @GetMapping("/puntos/{id}/edit")
    public String puntoEditarForm(@PathVariable("id") int id, Model model) {
        Optional<Punto> opt = puntoRepository.darPunto(id);
        if (opt.isPresent()) {
            model.addAttribute("punto", opt.get());
            return "puntoEditar";
        }
        return "redirect:/puntos";
    }

  
    @PostMapping("/puntos/{id}/edit/save")
    public String puntoEditarGuardar(@PathVariable("id") int id, @ModelAttribute Punto punto) {
        Integer servicioId = (punto.getServicio_id() != null) ? punto.getServicio_id().getId() : null;
        Integer ciudadId   = (punto.getCiudad_id()   != null) ? punto.getCiudad_id().getId()   : null;

        puntoRepository.actualizarPunto(
            id,
            punto.getDireccion(),
            punto.getLatitud(),
            punto.getLongitud(),
            servicioId,
            ciudadId
        );
        return "redirect:/puntos";
    }

    
    @GetMapping("/puntos/{id}/delete")
    public String puntoEliminar(@PathVariable("id") int id) {
        puntoRepository.eliminarPunto(id);
        return "redirect:/puntos";
    }
}
