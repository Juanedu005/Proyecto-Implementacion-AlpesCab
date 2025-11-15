package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SERVICIOPUNTOSFIN")
public class ServicioPuntosFin {
    
    @EmbeddedId
    private ServicioPuntosFinPK pk; 

    public ServicioPuntosFin(){;}

    public ServicioPuntosFin(ServicioPuntosFinPK pk) {
        this.pk = pk;
    }

    public ServicioPuntosFinPK getPk() {
        return pk;
    }

    public void setPk(ServicioPuntosFinPK pk) {
        this.pk = pk;
    }

    
}
