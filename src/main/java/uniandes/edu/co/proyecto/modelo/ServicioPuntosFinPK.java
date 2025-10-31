package uniandes.edu.co.proyecto.modelo;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ServicioPuntosFinPK {

    @ManyToOne
    @JoinColumn(name = "Servicio_id", referencedColumnName = "id")
    private Servicio Servicio_id;

    @ManyToOne
    @JoinColumn(name = "P_Punto_id", referencedColumnName = "Punto_id")
    private Punto P_Punto_id; 

    public ServicioPuntosFinPK() {
        super();
    }

    public ServicioPuntosFinPK(Servicio servicio_id, Punto p_Punto_id) {
        super();
        Servicio_id = servicio_id;
        P_Punto_id = p_Punto_id;
    }

    public Servicio getServicio_id() {
        return Servicio_id;
    }

    public void setServicio_id(Servicio servicio_id) {
        Servicio_id = servicio_id;
    }

    public Punto getP_Punto_id() {
        return P_Punto_id;
    }

    public void setP_Punto_id(Punto p_Punto_id) {
        P_Punto_id = p_Punto_id;
    }
    

}
