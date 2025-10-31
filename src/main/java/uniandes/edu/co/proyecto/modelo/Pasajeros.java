package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pasajeros")
public class Pasajeros {

    @OneToOne
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id")
    private Servicio Servicio_id; 

    private String nivel;

    public Pasajeros(){;}

    public Pasajeros(Servicio servicio_id, String nivel) {
        Servicio_id = servicio_id;
        this.nivel = nivel;
    }

    public Servicio getServicio_id() {
        return Servicio_id;
    }

    public void setServicio_id(Servicio servicio_id) {
        Servicio_id = servicio_id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    
    
    
}
