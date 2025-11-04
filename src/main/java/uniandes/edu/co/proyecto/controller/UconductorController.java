package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uniandes.edu.co.proyecto.modelo.Uconductor;
import uniandes.edu.co.proyecto.repositorio.UconductorRepository;

@Controller
public class UconductorController {

    @Autowired
    private UconductorRepository uconductorRepository;

    @GetMapping("/uconductores")
    public String uconductores(Model model) {
        model.addAttribute("uconductores", uconductorRepository.darUconductors());
        return "uconductores"; // antes: model.toString()
    }

    @GetMapping("/uconductores/new")
    public String uconductorForm(Model model) {
        model.addAttribute("uconductor", new Uconductor()); // antes: new UconductorController()
        return "uconductorNuevo";
    }

    @PostMapping("/uconductores/new/save")
    public String uconductorGuardar(@ModelAttribute Uconductor uconductor) {
        // Si dejas id_conductor = null, el TRIGGER lo autogenera
        Integer idConductor = (uconductor.getPk() != null) ? uconductor.getPk().getId_conductor() : null;
        Integer idUsuario   = (uconductor.getPk() != null) ? uconductor.getPk().getId_usuario()   : null;

        uconductorRepository.insertarUconductor(idConductor, idUsuario);
        return "redirect:/uconductores";
    }

    @PostMapping("/uconductores/{id_conductor}/{id_usuario}/edit")
    public String uconductorEditarForm(@PathVariable("id_conductor") int id_conductor,
                                       @PathVariable("id_usuario") int id_usuario,
                                       Model model) {
        Uconductor uconductor = uconductorRepository.darUconductor(id_conductor, id_usuario);
        if (uconductor != null) {
            model.addAttribute("uconductor", uconductor);
            return "uconductorEditar";
        } else {
            return "redirect:/uconductores";
        }
    }

    @PostMapping("/uconductores/{id_conductor}/{id_usuario}/edit/save")
    public String uconductorEditarGuardar(@PathVariable("id_conductor") int id_conductor,
                                          @PathVariable("id_usuario") int id_usuario,
                                          @ModelAttribute Uconductor uconductor) {
        // En esta tabla normalmente no se "actualiza" nada (PK compuesta),
        // hacemos no-op para mantener la ruta viva:
        uconductorRepository.actualizarUconductor(id_conductor, id_usuario);
        return "redirect:/uconductores";
    }

    @GetMapping("/uconductores/{id_conductor}/{id_usuario}/delete")
    public String uconductorEliminar(@PathVariable("id_conductor") int id_conductor,
                                     @PathVariable("id_usuario") int id_usuario) {
        uconductorRepository.eliminarUconductor(id_conductor, id_usuario);
        return "redirect:/uconductores";
    }

    // ========================= RFC2 =========================
    @GetMapping("/uconductores/top")
    public String rfc2Top20Conductores(Model model) {
        model.addAttribute("topConductores", uconductorRepository.rfc2Top20Conductores());
        return "uconductoresTop";
    }

    // ========================= RFC3 =========================
    @GetMapping("/uconductores/{id_conductor}/{id_usuario}/recaudo")
    public String rfc3DineroPorVehiculoYTipo(@PathVariable("id_conductor") int idConductor,
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
