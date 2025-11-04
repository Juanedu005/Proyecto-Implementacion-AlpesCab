package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RESENA") // opcional, pero suele ir en mayúsculas en Oracle
public class Resena {

    @EmbeddedId
    private ResenaPK pk;  // Asegúrate que pk tenga las columnas: Ucond_idcond, Ucond_idusuario, User_idusuario, User_idser

    private String comentario;
    private Integer puntuacion;

    // === RELACIÓN A Uconductor (usa MISMAS columnas que están en la PK) ===
    // → marcamos solo-lectura para evitar la duplicación de columnas
    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "UCOND_IDCOND",    referencedColumnName = "ID_CONDUCTOR", insertable = false, updatable = false),
        @JoinColumn(name = "UCOND_IDUSUARIO", referencedColumnName = "ID_USUARIO",   insertable = false, updatable = false)
    })
    private Uconductor uconductor;

    // === RELACIÓN A Uservicios (también comparte columnas con la PK) ===
    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "USER_IDUSUARIO", referencedColumnName = "ID_USUARIO",    insertable = false, updatable = false),
        @JoinColumn(name = "USER_IDSER",     referencedColumnName = "ID_SERVICIOS",  insertable = false, updatable = false)
    })
    private Uservicios uservicios; // OJO: mantén el mismo nombre en campo y setters/getters

    public Resena() { }

    public Resena(ResenaPK pk, String comentario, Integer puntuacion,
                  Uconductor uconductor, Uservicios uservicios) {
        this.pk = pk;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.uconductor = uconductor;
        this.uservicios = uservicios; // <-- asegúrate que el nombre del campo sea "uservicios" o cambia aquí a "uservicios"
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
    public void setUservicios(Uservicios uservicios) { this.uservicios = uservicios; } // <-- corrige a "this.uservicios" o renombra el campo a "uservicios"
}
