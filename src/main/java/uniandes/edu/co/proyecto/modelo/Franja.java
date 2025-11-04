package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Franja")
public class Franja {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_franja")
    private Integer id_franja;

    @Column(name = "hora_inicio")
    private LocalDateTime hora_incio;

    @Column(name = "hora_fin")
    private LocalDateTime hora_fin;

    private Boolean ocupado;

    @ManyToOne
    @JoinColumn(name = "Vehiculo_id", referencedColumnName = "id")
    private Vehiculo vehiculo;

    /* Asociación compuesta correcta a Uconductor */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "Ucond_idcond",    referencedColumnName = "id_conductor"),
        @JoinColumn(name = "Ucond_idusuario", referencedColumnName = "id_usuario")
    })
    private Uconductor uconductor;

    public Franja() { }

    public Franja(Integer id_franja, LocalDateTime hora_incio, LocalDateTime hora_fin,
                  Boolean ocupado, Vehiculo vehiculo, Uconductor uconductor) {
        this.id_franja = id_franja;
        this.hora_incio = hora_incio;
        this.hora_fin = hora_fin;
        this.ocupado = ocupado;
        this.vehiculo = vehiculo;
        this.uconductor = uconductor;
    }

    public Integer getId_franja() { return id_franja; }
    public void setId_franja(Integer id_franja) { this.id_franja = id_franja; }

    public LocalDateTime getHora_incio() { return hora_incio; }
    public void setHora_incio(LocalDateTime hora_incio) { this.hora_incio = hora_incio; }

    public LocalDateTime getHora_fin() { return hora_fin; }
    public void setHora_fin(LocalDateTime hora_fin) { this.hora_fin = hora_fin; }

    public Boolean getOcupado() { return ocupado; }
    public void setOcupado(Boolean ocupado) { this.ocupado = ocupado; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public Uconductor getUconductor() { return uconductor; }
    public void setUconductor(Uconductor uconductor) { this.uconductor = uconductor; }
}
