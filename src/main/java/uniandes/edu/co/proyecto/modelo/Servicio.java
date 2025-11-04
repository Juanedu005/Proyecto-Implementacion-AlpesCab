package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer tarifa_fija;
    private Integer distancia_recorrida;
    private LocalDateTime hora_incio;
    private LocalDateTime hora_fin;

    @OneToOne
    @JoinColumn(name = "P_Punto_id", referencedColumnName = "Punto_id")
    private Punto P_Punto_id;

    // Si quieres la relación inversa hacia Uservicios, puedes agregar:
    // @OneToMany(mappedBy = "servicio")
    // private List<Uservicios> uservicios;

    public Servicio() { }

    public Servicio(Integer id, Integer tarifa_fija, Integer distancia_recorrida, LocalDateTime hora_incio,
                    LocalDateTime hora_fin, Punto p_Punto_id) {
        this.id = id;
        this.tarifa_fija = tarifa_fija;
        this.distancia_recorrida = distancia_recorrida;
        this.hora_incio = hora_incio;
        this.hora_fin = hora_fin;
        this.P_Punto_id = p_Punto_id;
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

    public Integer getDistancia_recorrida() {
        return distancia_recorrida;
    }

    public void setDistancia_recorrida(Integer distancia_recorrida) {
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
        this.P_Punto_id = p_Punto_id;
    }
}
