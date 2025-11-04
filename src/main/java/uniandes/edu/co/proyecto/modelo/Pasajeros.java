package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "PASAJEROS")
public class Pasajeros {

    @Id
    @Column(name = "SERVICIO_ID", nullable = false)
    private Integer servicioId;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "SERVICIO_ID", referencedColumnName = "ID")
    private Servicio servicio;

    @Column(name = "NIVEL", nullable = false)
    private String nivel;

    public Pasajeros() {}

    public Pasajeros(Servicio servicio, String nivel) {
        this.servicio = servicio;
        this.nivel = nivel;
    }

    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}
