package uniandes.edu.co.proyecto.controller;

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
    public String puntos(Model model){
        model.addAttribute("puntos", puntoRepository.darPuntos());
        return model.toString();    
    }

    @GetMapping("/puntos/new")
    public String puntoForm(Model model){
        model.addAttribute("punto", new Punto());
        return "puntoNuevo";
    }

    @PostMapping("/puntos/new/save")
    public String puntoGuardar(@ModelAttribute Punto punto){
        puntoRepository.insertarPunto(punto.getDireccion(), punto.getLatitud(), punto.getLongitud(), punto.getServicio_id().getId(), punto.getCiudad_id().getId());
        return "redirect:/puntos";
    }

    @PostMapping("/puntos/{id}/edit")
    public String puntoEditarForm(@PathVariable("Punto_id") int Punto_id, Model model){
        Punto punto = puntoRepository.darPunto(Punto_id);

        if (punto != null) {
            model.addAttribute("punto", punto);
            return "puntoEditar";
        } else {
            return "redirect:/puntos";
        }
    }

    @PostMapping("/puntos/{Punto_id}/edit/save")
    public String puntoEditarGuardar(@PathVariable("Punto_id") int Punto_id, @ModelAttribute Punto punto){

        puntoRepository.actualizarPunto(Punto_id, punto.getDireccion(), punto.getLatitud(), punto.getLongitud(), punto.getServicio_id().getId(), punto.getCiudad_id().getId()); 
        return "redirect:/puntos";
    }

    @GetMapping("/puntos/{Punto_id}/delete")
    public String puntoEliminar(@PathVariable("Punto_id") int Punto_id){
        puntoRepository.eliminarPunto(Punto_id);
        return "redirect:/puntos";  
    }


}