package uniandes.edu.co.proyecto.modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Uservicios")
public class Uservicios {

    @EmbeddedId
    private UserviciosPK pk;

    private String nombre_tc;
    private Long numero_tc;
    private Date fecha_vencimiento;
    private Integer cv;

  
    @MapsId("id_usuario")
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;


    //@Column(name = "ID_SERVICIOS")
    //private Integer idServicios;

    public Uservicios() { }

    public Uservicios(UserviciosPK pk, String nombre_tc, Long numero_tc, Date fecha_vencimiento, Integer cv) {
        this.pk = pk;
        this.nombre_tc = nombre_tc;
        this.numero_tc = numero_tc;
        this.fecha_vencimiento = fecha_vencimiento;
        this.cv = cv;
    }

    public UserviciosPK getPk() {
        return pk;
    }

    public void setPk(UserviciosPK pk) {
        this.pk = pk;
    }

    public String getNombre_tc() {
        return nombre_tc;
    }

    public void setNombre_tc(String nombre_tc) {
        this.nombre_tc = nombre_tc;
    }

    public Long getNumero_tc() {
        return numero_tc;
    }

    public void setNumero_tc(Long numero_tc) {
        this.numero_tc = numero_tc;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public Integer getCv() {
        return cv;
    }

    public void setCv(Integer cv) {
        this.cv = cv;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //public Integer getServicio() {
    //    return idServicios;
    //}

    //public void setServicio(Integer servicio) {
    //    this.idServicios = servicio;
    //}
}
