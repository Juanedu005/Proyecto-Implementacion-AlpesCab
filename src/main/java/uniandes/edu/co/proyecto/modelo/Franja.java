package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "FRANJA")
public class Franja {

    @Id
    @Column(name = "ID_FRANJA")
    private Integer idFranja; // <- SIN @GeneratedValue (lo asigna el trigger)

    @Column(name = "HORA_INICIO")
    private LocalDateTime horaInicio;

    @Column(name = "HORA_FIN")
    private LocalDateTime horaFin;

    @Column(name = "OCUPADO")
    private Integer ocupado;

    @ManyToOne
    @JoinColumn(name = "VEHICULO_ID", referencedColumnName = "ID")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "UCOND_IDCOND", referencedColumnName = "ID_CONDUCTOR"),
        @JoinColumn(name = "UCOND_IDUSUARIO", referencedColumnName = "ID_USUARIO")
    })
    private Uconductor uconductor;

    public Franja() { }

    public Franja(Integer idFranja, LocalDateTime horaInicio, LocalDateTime horaFin,
                  Integer ocupado, Vehiculo vehiculo, Uconductor uconductor) {
        this.idFranja = idFranja;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.ocupado = ocupado;
        this.vehiculo = vehiculo;
        this.uconductor = uconductor;
    }

    public Integer getIdFranja() { return idFranja; }
    public void setIdFranja(Integer idFranja) { this.idFranja = idFranja; }

    public LocalDateTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalDateTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalDateTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalDateTime horaFin) { this.horaFin = horaFin; }

    public Integer getOcupado() { return ocupado; }
    public void setOcupado(Integer ocupado) { this.ocupado = ocupado; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public Uconductor getUconductor() { return uconductor; }
    public void setUconductor(Uconductor uconductor) { this.uconductor = uconductor; }
}
