package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Domicilio")
public class Domicilio {
    
    @Id
    @OneToOne
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id")
    private Servicio Servicio_id;

    private String nombre_restaurante; 
    private String orden; 

    public Domicilio(){;}

    public Domicilio(Servicio servicio_id, String nombre_restaurante, String orden) {
        Servicio_id = servicio_id;
        this.nombre_restaurante = nombre_restaurante;
        this.orden = orden;
    }

    public Servicio getServicio_id() {
        return Servicio_id;
    }

    public void setServicio_id(Servicio servicio_id) {
        Servicio_id = servicio_id;
    }

    public String getNombre_restaurante() {
        return nombre_restaurante;
    }

    public void setNombre_restaurante(String nombre_restaurante) {
        this.nombre_restaurante = nombre_restaurante;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    

}
