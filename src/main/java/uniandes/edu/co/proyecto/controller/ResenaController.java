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
        Integer userIdUsuario = (resena.getUservicios() != null) ? resena.getUservicios().getPk().getId_usuario()   : null;
        Integer userIdSer     = (resena.getUservicios() != null) ? resena.getUservicios().getPk().getId_servicios() : null;

        Integer ucondIdCond   = (resena.getUconductor() != null) ? resena.getUconductor().getPk().getId_conductor() : null;
        Integer ucondIdUsu    = (resena.getUconductor() != null) ? resena.getUconductor().getPk().getId_usuario()   : null;

        resenaRepository.insertarResena(
            userIdUsuario,  
            ucondIdUsu,  
            ucondIdCond,  
            userIdSer,  
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
