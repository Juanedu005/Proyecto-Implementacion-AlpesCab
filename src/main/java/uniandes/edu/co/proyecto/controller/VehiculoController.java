package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Vehiculo;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

@Controller
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping("/vehiculos")
    public String vehiculos(Model model){
        model.addAttribute("vehiculos", vehiculoRepository.darVehiculos());
        return model.toString();
    }

    @GetMapping("/vehiculos/new")
    public String vehiculoForm(Model model){
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculoNuevo";
    }

    @PostMapping("/vehiculos/new/save")
    public String vehiculoGuardar(@ModelAttribute Vehiculo vehiculo){
        vehiculoRepository.insertarVehiculo(vehiculo.getTipo(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getColor(), vehiculo.getPlaca(), vehiculo.getCapacidad(), vehiculo.getCiudad_id().getId(), vehiculo.getUcond_idcond().getPk().getId_conductos(), vehiculo.getUcond_idusuario().getPk().getId_usuario().getId());
        return "redirect:/vehiculos";
    }

    @PostMapping("/vehiculos/{id}/edit")
    public String vehiculoEditarForm(@PathVariable("id") int id, Model model){
        Vehiculo vehiculo = vehiculoRepository.darVehiculo(id);
        
        if (vehiculo != null) {
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculoEditar";
        } else {
            return "redirect:/vehiculos";
        }
    }

    @PostMapping("/vehiculos/{id}/edit/save")
    public String vehiculoEditarGuardar(@PathVariable("id") int id, @ModelAttribute Vehiculo vehiculo){

        vehiculoRepository.actualizarVehiculo(id, vehiculo.getTipo(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getColor(), vehiculo.getPlaca(), vehiculo.getCapacidad(), vehiculo.getCiudad_id().getId(), vehiculo.getUcond_idcond().getPk().getId_conductos(), vehiculo.getUcond_idusuario().getPk().getId_usuario().getId());
        return "redirect:/vehiculos";
    }
    
    @PostMapping("/vehiculos/{id}/delete")
    public String vehiculoEliminar(@PathVariable("id") int id){
        vehiculoRepository.eliminarVehiculo(id);
        return "redirect:/vehiculos";
    }
    
}
