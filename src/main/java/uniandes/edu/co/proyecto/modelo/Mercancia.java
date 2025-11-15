package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "MERCANCIA")
public class Mercancia {


    @Id
    @Column(name = "SERVICIO_ID", nullable = false)
    private Integer servicioId;

    
    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "SERVICIO_ID", referencedColumnName = "ID")
    private Servicio servicio;

    @Column(name = "ELEMENTORECOGIDO", nullable = false)
    private String elementoRecogido;

    public Mercancia() {}

    public Mercancia(Servicio servicio, String elementoRecogido) {
        this.servicio = servicio;  
        this.elementoRecogido = elementoRecogido;
    }

    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public String getElementoRecogido() { return elementoRecogido; }
    public void setElementoRecogido(String elementoRecogido) { this.elementoRecogido = elementoRecogido; }
}
