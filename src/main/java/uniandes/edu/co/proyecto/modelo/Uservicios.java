package uniandes.edu.co.proyecto.modelo;



import java.sql.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="Uservicios")
public class Uservicios {


    @EmbeddedId
    private UserviciosPK pk; 

    private String nombre_tc; 
    private Integer numero_tc;
    private Date fecha_vencimiento;
    private Integer cv; 


    public Uservicios(){;}


    public Uservicios(UserviciosPK pk, String nombre_tc, Integer numero_tc, Date fecha_vencimiento, Integer cv) {
        super();
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


    public Integer getNumero_tc() {
        return numero_tc;
    }


    public void setNumero_tc(Integer numero_tc) {
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


    
}
