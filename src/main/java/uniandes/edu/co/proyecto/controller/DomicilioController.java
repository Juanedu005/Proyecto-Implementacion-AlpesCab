package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Domicilio;
import uniandes.edu.co.proyecto.modelo.Servicio;
import uniandes.edu.co.proyecto.repositorio.DomicilioRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@Controller 
public class DomicilioController {
    
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ServicioRepository servicioRepository;


    @GetMapping("/domicilios")
    public String domicilios(Model model){
        model.addAttribute("domicilios", domicilioRepository.findAll());
        return "domicilios"; 
    }

    @GetMapping("/domicilios/new")
    public String domicilioForm(Model model){
        model.addAttribute("domicilio", new Domicilio());
        model.addAttribute("servicios", servicioRepository.findAll());
        return "domicilioNuevo";
    }

    @PostMapping("/domicilios/new/save")
    public String domicilioGuardar(@ModelAttribute Domicilio domicilio){
        Integer servicioId = domicilio.getServicioId(); 
        if (servicioId == null) {
            return "redirect:/domicilios";
        }
        Servicio s = servicioRepository.findById(servicioId)
                        .orElseThrow(() -> new IllegalArgumentException("Servicio no existe: " + servicioId));
        domicilio.setServicio(s); 
        domicilioRepository.save(domicilio);
        return "redirect:/domicilios";
    }

   
    @GetMapping("/domicilios/{servicioId}/edit")
    public String domicilioEditarForm(@PathVariable("servicioId") int servicioId, Model model){
        Optional<Domicilio> domOpt = domicilioRepository.findById(servicioId);
        if (domOpt.isPresent()) {
            model.addAttribute("domicilio", domOpt.get());
            model.addAttribute("servicios", servicioRepository.findAll()); // si quieres permitir cambiar de servicio
            return "domicilioEditar";
        } else {
            return "redirect:/domicilios";
        }
    }


    @PostMapping("/domicilios/{servicioId}/edit/save")
    public String domicilioEditarGuardar(@PathVariable("servicioId") int servicioId, @ModelAttribute Domicilio form){
        Domicilio existente = domicilioRepository.findById(servicioId)
                                 .orElseThrow(() -> new IllegalArgumentException("Domicilio no existe: " + servicioId));

        existente.setNombreRestaurante(form.getNombreRestaurante());
        existente.setOrden(form.getOrden());

        if (form.getServicioId() != null && form.getServicioId() != servicioId) {
            Servicio nuevo = servicioRepository.findById(form.getServicioId())
                               .orElseThrow(() -> new IllegalArgumentException("Servicio no existe: " + form.getServicioId()));
            existente.setServicio(nuevo); 
        }

        domicilioRepository.save(existente);
        return "redirect:/domicilios";
    }

    @GetMapping("/domicilios/{servicioId}/delete")
    public String domicilioEliminar(@PathVariable("servicioId") int servicioId){
        domicilioRepository.deleteById(servicioId);
        return "redirect:/domicilios";
    }
}
