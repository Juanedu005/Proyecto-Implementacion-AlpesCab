package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Resena")
public class Resena {

    @EmbeddedId
    private ResenaPK pk;

    private String comentario;
    private Integer puntuacion;

    /* FK compuesta a Uconductor: orden EXACTO como en UconductorPk (id_conductor, id_usuario) */
    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "Ucond_idcond",    referencedColumnName = "id_conductor"),
        @JoinColumn(name = "Ucond_idusuario", referencedColumnName = "id_usuario")
    })
    private Uconductor uconductor;

    /* FK compuesta a Uservicios: orden EXACTO como en UserviciosPK (ID_USUARIO, ID_SERVICIOS) */
    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "User_idusuario", referencedColumnName = "ID_USUARIO"),
        @JoinColumn(name = "User_idser",     referencedColumnName = "ID_SERVICIOS")
    })
    private Uservicios uservicios;

    public Resena() { }

    public Resena(ResenaPK pk, String comentario, Integer puntuacion,
                  Uconductor uconductor, Uservicios uservicios) {
        this.pk = pk;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.uconductor = uconductor;
        this.uservicios = uservicios;
    }

    public ResenaPK getPk() { return pk; }
    public void setPk(ResenaPK pk) { this.pk = pk; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

    public Uconductor getUconductor() { return uconductor; }
    public void setUconductor(Uconductor uconductor) { this.uconductor = uconductor; }

    public Uservicios getUservicios() { return uservicios; }
    public void setUservicios(Uservicios uservicios) { this.uservicios = uservicios; }
}
