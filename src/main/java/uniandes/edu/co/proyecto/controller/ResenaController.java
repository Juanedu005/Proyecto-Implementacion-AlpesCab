package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.Resena;
import uniandes.edu.co.proyecto.repositorio.ResenaRepository;

@Controller
public class ResenaController {

    @Autowired
    private ResenaRepository resenaRepository;

    @GetMapping("/resenas")
    public String resenas(Model model) {
        model.addAttribute("resenas", resenaRepository.darResenas());
        return "resenas"; // <- no model.toString()
    }

    @GetMapping("/resenas/new")
    public String resenaForm(Model model) {
        model.addAttribute("resena", new Resena());
        return "resenaNuevo";
    }

    @PostMapping("/resenas/new/save")
    public String resenaGuardar(@ModelAttribute Resena resena) {
        // FK compuesta a USERVICIOS
        Integer userIdUsuario = (resena.getUservicios() != null) ? resena.getUservicios().getPk().getId_usuario()   : null;
        Integer userIdSer     = (resena.getUservicios() != null) ? resena.getUservicios().getPk().getId_servicios() : null;

        // FK compuesta a UCONDUCTOR
        Integer ucondIdCond   = (resena.getUconductor() != null) ? resena.getUconductor().getPk().getId_conductor() : null;
        Integer ucondIdUsu    = (resena.getUconductor() != null) ? resena.getUconductor().getPk().getId_usuario()   : null;

        // IMPORTANTE: tu tabla RESENA tiene NOT NULL en las 4 columnas anteriores.
        resenaRepository.insertarResena(
            userIdUsuario,  // User_idusuario
            ucondIdUsu,     // Ucond_idusuario  (también parte de la PK)
            ucondIdCond,    // Ucond_idcond
            userIdSer,      // User_idser
            resena.getComentario(),
            resena.getPuntuacion()
        );
        return "redirect:/resenas";
    }

    @PostMapping("/resenas/{User_idusuario}/{Ucond_idusuario}/edit")
    public String resenaEditarForm(@PathVariable("User_idusuario") int userIdUsuario,
                                   @PathVariable("Ucond_idusuario") int ucondIdUsuario,
                                   Model model) {
        Resena resena = resenaRepository.darResena(userIdUsuario, ucondIdUsuario);
        if (resena != null) {
            model.addAttribute("resena", resena);
            return "resenaEditar";
        } else {
            return "redirect:/resenas";
        }
    }

    @PostMapping("/resenas/{User_idusuario}/{Ucond_idusuario}/edit/save")
    public String resenaEditarGuardar(@PathVariable("User_idusuario") int userIdUsuario,
                                      @PathVariable("Ucond_idusuario") int ucondIdUsuario,
                                      @ModelAttribute Resena resena) {
        resenaRepository.actualizarResena(
            userIdUsuario,
            ucondIdUsuario,
            resena.getComentario(),
            resena.getPuntuacion()
        );
        return "redirect:/resenas";
    }

    @GetMapping("/resenas/{User_idusuario}/{Ucond_idusuario}/delete")
    public String resenaEliminar(@PathVariable("User_idusuario") int userIdUsuario,
                                 @PathVariable("Ucond_idusuario") int ucondIdUsuario) {
        resenaRepository.eliminarResena(userIdUsuario, ucondIdUsuario);
        return "redirect:/resenas";
    }
}
