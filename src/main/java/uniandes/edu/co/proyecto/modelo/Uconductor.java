package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Uconductor")
public class Uconductor {

    @EmbeddedId
    private UconductorPk pk;

    public Uconductor() { }

    public Uconductor(UconductorPk pk) { this.pk = pk; }

    public UconductorPk getPk() { return pk; }
    public void setPk(UconductorPk pk) { this.pk = pk; }
}
