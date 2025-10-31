package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Resena")
public class Resena {

    @EmbeddedId
    private ResenaPK pk; 

    private String comentario; 
    private Integer puntuacion; 

    @ManyToOne
    @JoinColumn(name="Ucond_idcond", referencedColumnName = "id_conductor")
    private Uconductor Ucond_idcond; 


    @ManyToOne
    @JoinColumn(name="User_idser", referencedColumnName = "id_sercivicios")
    private Uservicios User_idser;


    public Resena(){;}


    public Resena(ResenaPK pk, String comentario, Integer puntuacion, Uconductor ucond_idcond, Uservicios user_idser) {
        this.pk = pk;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        Ucond_idcond = ucond_idcond;
        User_idser = user_idser;
    }


    public ResenaPK getPk() {
        return pk;
    }


    public void setPk(ResenaPK pk) {
        this.pk = pk;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public Integer getPuntuacion() {
        return puntuacion;
    }


    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }


    public Uconductor getUcond_idcond() {
        return Ucond_idcond;
    }


    public void setUcond_idcond(Uconductor ucond_idcond) {
        Ucond_idcond = ucond_idcond;
    }


    public Uservicios getUser_idser() {
        return User_idser;
    }


    public void setUser_idser(Uservicios user_idser) {
        User_idser = user_idser;
    }

    
    
    
}
