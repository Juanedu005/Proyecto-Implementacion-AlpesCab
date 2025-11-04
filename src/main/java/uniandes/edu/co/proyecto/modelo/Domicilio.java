package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "DOMICILIO")
public class Domicilio {

    // PK real de la tabla
    @Id
    @Column(name = "SERVICIO_ID", nullable = false)
    private Integer servicioId;

    // PK compartida con SERVICIO (usa el mismo valor de ID)
    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "SERVICIO_ID", referencedColumnName = "ID")
    private Servicio servicio;

    @Column(name = "NOMBRE_RESTAURANTE", nullable = false)
    private String nombreRestaurante;

    @Column(name = "ORDEN", nullable = false)
    private String orden;

    public Domicilio() {}

    public Domicilio(Servicio servicio, String nombreRestaurante, String orden) {
        this.servicio = servicio;         // @MapsId tomará servicio.getId() como PK
        this.nombreRestaurante = nombreRestaurante;
        this.orden = orden;
    }

    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getOrden() { return orden; }
    public void setOrden(String orden) { this.orden = orden; }
}
