package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uniandes.edu.co.proyecto.modelo.Uconductor;
import uniandes.edu.co.proyecto.repositorio.UconductorRepository;

import org.springframework.ui.Model;

@Controller
public class UconductorController {

    @Autowired
    private UconductorRepository uconductorRepository;

    @GetMapping("/uconductores")
    public String uconductores(Model model) {
        model.addAttribute("uconductores", uconductorRepository.darUconductors());
        return model.toString();
    }

    @GetMapping("/uconductores/new")
    public String uconductorForm(Model model) {
        model.addAttribute("uconductor", new UconductorController());
        return "uconductorNuevo";
    }

    @PostMapping("/uconductores/new/save")
    public String uconductorGuardar(@ModelAttribute Uconductor uconductor) {
        uconductorRepository.insertarUconductor(uconductor.getPk().getId_conductos(), uconductor.getPk().getId_usuario().getId());
        return "redirect:/uconductores";
    }

    @PostMapping("/uconductores/{id_conductor}/{id_usuario}/edit")
    public String uconductorEditarForm(@PathVariable("id_conductor") int id_conductor, @PathVariable("id_usuario") int id_usuario, Model model) {
        Uconductor uconductor = uconductorRepository.darUconductor(id_conductor, id_usuario);

        if (uconductor != null) {
            model.addAttribute("uconductor", uconductor);
            return "uconductorEditar";
        } else {
            return "redirect:/uconductores";
        }
    }

    @PostMapping("/uconductores/{id_conductor}/{id_usuario}/edit/save")
    public String uconductorEditarGuardar(@PathVariable("id_conductor") int id_conductor, @PathVariable("id_usuario") int id_usuario, @ModelAttribute Uconductor uconductor) {

        uconductorRepository.actualizarUconductor(id_conductor, id_usuario);
        return "redirect:/uconductores";
    }

    @GetMapping("/uconductores/{id_conductor}/{id_usuario}/delete")
    public String uconductorEliminar(@PathVariable("id_conductor") int id_conductor, @PathVariable("id_usuario") int id_usuario) {
        uconductorRepository.eliminarUconductor(id_conductor, id_usuario);
        return "redirect:/uconductores";
    }

    // ========================= RFC2 =========================
    // Top 20 conductores con más servicios 
    @GetMapping("/uconductores/top")
    public String rfc2Top20Conductores(Model model) {

    model.addAttribute("topConductores",
        uconductorRepository.rfc2Top20Conductores());

    return "uconductoresTop";
    }

    // ========================= RFC3 =========================
    // Total de dinero por vehículo y tipo de servicio para un conductor específico
    // La comisión se pasa por query param: ?comisionPct=0.20 (por ejemplo 20%)
    @GetMapping("/uconductores/{id_conductor}/{id_usuario}/recaudo")
    public String rfc3DineroPorVehiculoYTipo(
            @PathVariable("id_conductor") int idConductor,
            @PathVariable("id_usuario") int idUsuarioConductor,
            @RequestParam(name = "comisionPct", defaultValue = "0.20") double comisionPct,
            Model model) {

        model.addAttribute("recaudoVehiculoTipo",
            uconductorRepository.rfc3DineroPorVehiculoYTipo(
                idConductor,
                idUsuarioConductor,
                comisionPct
            )
        );

        return "uconductorRecaudo";
    }



}
