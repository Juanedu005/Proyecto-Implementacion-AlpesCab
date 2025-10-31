package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Mercancia")
public class Mercancia {
    
    @OneToOne
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id")
    private Servicio Servicio_id;

    private String ElementoRecogido; 

    public Mercancia(){;}

    public Mercancia(Servicio servicio_id, String elementoRecogido) {
        Servicio_id = servicio_id;
        ElementoRecogido = elementoRecogido;
    }

    public Servicio getServicio_id() {
        return Servicio_id;
    }

    public void setServicio_id(Servicio servicio_id) {
        Servicio_id = servicio_id;
    }

    public String getElementoRecogido() {
        return ElementoRecogido;
    }

    public void setElementoRecogido(String elementoRecogido) {
        ElementoRecogido = elementoRecogido;
    }

    




}
