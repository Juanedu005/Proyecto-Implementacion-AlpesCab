package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Embeddable
public class ResenaPK implements Serializable {

    @ManyToOne
    @JoinColumn(name= "User_idusuario", referencedColumnName = "id_usuario")
    private Uservicios User_idusuario; 

    @ManyToOne
    @JoinColumn(name= "Ucond_idusuario", referencedColumnName = "id_usuario")
    private Uservicios Ucond_idusuario;
    
    public ResenaPK(){
        super();

    }

    public ResenaPK(Uservicios user_idusuario, Uservicios ucond_idusuario) {
        super();
        User_idusuario = user_idusuario;
        Ucond_idusuario = ucond_idusuario;
    }

    public Uservicios getUser_idusuario() {
        return User_idusuario;
    }

    public void setUser_idusuario(Uservicios user_idusuario) {
        User_idusuario = user_idusuario;
    }

    public Uservicios getUcond_idusuario() {
        return Ucond_idusuario;
    }

    public void setUcond_idusuario(Uservicios ucond_idusuario) {
        Ucond_idusuario = ucond_idusuario;
    }

    
    
    
}
