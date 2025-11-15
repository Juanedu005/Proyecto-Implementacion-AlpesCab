package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="Punto")
public class Punto {

    @Id
    @SequenceGenerator(
        name = "PUNTO_SEQ",
        sequenceName = "PUNTO_PUNTO_ID_SEQ",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUNTO_SEQ")
    private Integer Punto_id;

    private String direccion; 
    private String latitud; 
    private String longitud; 

    @ManyToOne(optional = true)
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id", nullable = true)
    private Servicio Servicio_id;

    @ManyToOne
    @JoinColumn(name="Ciudad_id", referencedColumnName = "id")
    private Ciudad Ciudad_id;

    public Punto(){;}

    public Punto(Integer punto_id, String direccion, String latitud, String longitud, Servicio servicio_id,
            Ciudad ciudad_id) {
        Punto_id = punto_id;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        Servicio_id = servicio_id;
        Ciudad_id = ciudad_id;
    }

    public Integer getPunto_id() {
        return Punto_id;
    }

    public void setPunto_id(Integer punto_id) {
        Punto_id = punto_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Servicio getServicio_id() {
        return Servicio_id;
    }

    public void setServicio_id(Servicio servicio_id) {
        Servicio_id = servicio_id;
    }

    public Ciudad getCiudad_id() {
        return Ciudad_id;
    }

    public void setCiudad_id(Ciudad ciudad_id) {
        Ciudad_id = ciudad_id;
    }

    
    
    
}
