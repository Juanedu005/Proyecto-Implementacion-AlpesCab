package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Franja")

public class Franja {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id_franja; 

    private LocalDateTime hora_incio; 
    private LocalDateTime hora_fin; 
    private Boolean ocupado; 

    @ManyToOne
    @JoinColumn(name="Vehiculo_id", referencedColumnName = "id")
    private Vehiculo Vehiculo_id;

    @ManyToOne
    @JoinColumn(name="Ucond_idcond", referencedColumnName = "id_conductor")
    private Uconductor Ucond_idcond; 

    @ManyToOne
    @JoinColumn(name="Ucond_idusuario", referencedColumnName = "id_usuario")
    private Uconductor Ucond_idusuario;

    public Franja(){;}

    public Franja(Integer id_franja, LocalDateTime hora_incio, LocalDateTime hora_fin, Boolean ocupado,
            Vehiculo vehiculo_id, Uconductor ucond_idcond, Uconductor ucond_idusuario) {
        this.id_franja = id_franja;
        this.hora_incio = hora_incio;
        this.hora_fin = hora_fin;
        this.ocupado = ocupado;
        Vehiculo_id = vehiculo_id;
        Ucond_idcond = ucond_idcond;
        Ucond_idusuario = ucond_idusuario;
    }

    public Integer getId_franja() {
        return id_franja;
    }

    public void setId_franja(Integer id_franja) {
        this.id_franja = id_franja;
    }

    public LocalDateTime getHora_incio() {
        return hora_incio;
    }

    public void setHora_incio(LocalDateTime hora_incio) {
        this.hora_incio = hora_incio;
    }

    public LocalDateTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(LocalDateTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public Boolean getOcupado() {
        return ocupado;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public Vehiculo getVehiculo_id() {
        return Vehiculo_id;
    }

    public void setVehiculo_id(Vehiculo vehiculo_id) {
        Vehiculo_id = vehiculo_id;
    }

    public Uconductor getUcond_idcond() {
        return Ucond_idcond;
    }

    public void setUcond_idcond(Uconductor ucond_idcond) {
        Ucond_idcond = ucond_idcond;
    }

    public Uconductor getUcond_idusuario() {
        return Ucond_idusuario;
    }

    public void setUcond_idusuario(Uconductor ucond_idusuario) {
        Ucond_idusuario = ucond_idusuario;
    }

    


}
