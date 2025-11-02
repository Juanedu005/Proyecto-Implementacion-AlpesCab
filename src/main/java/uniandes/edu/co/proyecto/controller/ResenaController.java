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
    public String resenas(Model model){
        model.addAttribute("resenas", resenaRepository.darResenas());
        return model.toString();
    }

    @GetMapping("/resenas/new")
    public String resenaForm(Model model){
        model.addAttribute("resena", new Resena());
        return "resenaNuevo";
    }

    @PostMapping("/resenas/new/save")
    public String resenaGuardar(@ModelAttribute Resena resena){
        resenaRepository.insertarResena(resena.getUser_idser().getPk().getId_usuario().getId(),resena.getUcond_idcond().getPk().getId_usuario().getId(), resena.getComentario(), resena.getPuntuacion());
        return "redirect:/resenas";
    }

    @PostMapping("/resenas/{User_idusuario}/{Ucond_idusuario}/edit")
    public String resenaEditarForm(@PathVariable("User_idusuario") int User_idusuario, @PathVariable("Ucond_idusuario") int Ucond_idusuario , Model model){
        Resena resena = resenaRepository.darResena(User_idusuario, Ucond_idusuario);

        if (resena != null){
            model.addAttribute("resena", resena);
            return "resenaEditar";
        } else {
            return "redirect:/resenas";
        }
    }

    
    @PostMapping("/resenas/{User_idusuario}/{Ucond_idusuario}/edit/save")
    public String resenaEditarGuardar(@PathVariable("User_idusuario") int User_idusuario, @PathVariable("Ucond_idusuario") int Ucond_idusuario, @ModelAttribute Resena resena){
        resenaRepository.actualizarResena(User_idusuario, Ucond_idusuario, resena.getComentario(), resena.getPuntuacion());

        return "redirect:/resenas";
    }

    @GetMapping("/resenas/{User_idusuario}/{Ucond_idusuario}/delete")
    public String resenaEliminar(@PathVariable("User_idusuario") int User_idusuario, @PathVariable("Ucond_idusuario") int Ucond_idusuario){
        resenaRepository.eliminarResena(User_idusuario, Ucond_idusuario);
        return "redirect:/resenas";
    }


    
}
