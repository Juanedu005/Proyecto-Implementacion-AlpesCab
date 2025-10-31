package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Servicio")
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

    private Integer tarifa_fija; 
    private String distancia_recorrida; 
    private LocalDateTime hora_incio;
    private LocalDateTime hora_fin; 

    @OneToOne
    @JoinColumn(name="P_Punto_id", referencedColumnName = "Punto_id")
    private Punto P_Punto_id; 

    @ManyToOne
    @JoinColumn(name="User_idser", referencedColumnName = "id_servicios")
    private Uservicios User_idser; 

    @ManyToOne
    @JoinColumn(name="User_idusuario", referencedColumnName = "id_usuario")
    private Uservicios User_idusuario; 

    public Servicio(){;}

    public Servicio(Integer id, Integer tarifa_fija, String distancia_recorrida, LocalDateTime hora_incio,
            LocalDateTime hora_fin, Punto p_Punto_id, Uservicios user_idser, Uservicios user_idusuario) {
        this.id = id;
        this.tarifa_fija = tarifa_fija;
        this.distancia_recorrida = distancia_recorrida;
        this.hora_incio = hora_incio;
        this.hora_fin = hora_fin;
        P_Punto_id = p_Punto_id;
        User_idser = user_idser;
        User_idusuario = user_idusuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTarifa_fija() {
        return tarifa_fija;
    }

    public void setTarifa_fija(Integer tarifa_fija) {
        this.tarifa_fija = tarifa_fija;
    }

    public String getDistancia_recorrida() {
        return distancia_recorrida;
    }

    public void setDistancia_recorrida(String distancia_recorrida) {
        this.distancia_recorrida = distancia_recorrida;
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

    public Punto getP_Punto_id() {
        return P_Punto_id;
    }

    public void setP_Punto_id(Punto p_Punto_id) {
        P_Punto_id = p_Punto_id;
    }

    public Uservicios getUser_idser() {
        return User_idser;
    }

    public void setUser_idser(Uservicios user_idser) {
        User_idser = user_idser;
    }

    public Uservicios getUser_idusuario() {
        return User_idusuario;
    }

    public void setUser_idusuario(Uservicios user_idusuario) {
        User_idusuario = user_idusuario;
    }

    



    
    
}
