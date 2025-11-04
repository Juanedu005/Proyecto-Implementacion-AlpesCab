package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Ciudad;
import uniandes.edu.co.proyecto.repositorio.CiudadRepository;

@Controller
public class CiudadController {
    
    @Autowired
    private CiudadRepository ciudadRepository;

    // LISTAR
    @GetMapping("/ciudades")
    public String ciudades(Model model){
        model.addAttribute("ciudades", ciudadRepository.darCiudades());
        return "ciudades"; // <<--- nombre de la vista (Thymeleaf/JSP)
    }

    // FORM CREAR
    @GetMapping("/ciudades/new")
    public String ciudadForm(Model model){
        model.addAttribute("ciudad", new Ciudad());
        return "ciudadNuevo";
    }

    // GUARDAR NUEVA (usa tu INSERT nativo)
    @PostMapping("/ciudades/new/save")
    public String ciudadGuardar(@ModelAttribute Ciudad ciudad){
        ciudadRepository.insertarCiudad(ciudad.getNombre());
        return "redirect:/ciudades";
    }

    // FORM EDITAR (DEBE SER GET)
    @GetMapping("/ciudades/{id}/edit")
    public String ciudadEditarForm(@PathVariable("id") int id, Model model){
        Optional<Ciudad> ciudadOpt = ciudadRepository.darCiudad(id);
        if (ciudadOpt.isPresent()) {
            model.addAttribute("ciudad", ciudadOpt.get());
            return "ciudadEditar";
        } else {
            return "redirect:/ciudades";
        }
    }

    // GUARDAR EDICIÓN
    @PostMapping("/ciudades/{id}/edit/save")
    public String ciudadEditarGuardar(@PathVariable("id") int id, @ModelAttribute Ciudad ciudad){
        ciudadRepository.actualizarCiudad(id, ciudad.getNombre());
        return "redirect:/ciudades";
    }

    // ELIMINAR
    @GetMapping("/ciudades/{id}/delete")
    public String ciudadEliminar(@PathVariable("id") int id){
        ciudadRepository.eliminarCiudad(id);
        return "redirect:/ciudades";
    }
}
