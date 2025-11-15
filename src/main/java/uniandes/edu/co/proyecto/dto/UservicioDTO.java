package uniandes.edu.co.proyecto.dto;

public class UservicioDTO {

    private Integer idUsuario;
    private String nombreTc;
    private Integer numeroTc;
    private String fechaVencimiento;
    private Integer cv;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreTc() { return nombreTc; }
    public void setNombreTc(String nombreTc) { this.nombreTc = nombreTc; }

    public Integer getNumeroTc() { return numeroTc; }
    public void setNumeroTc(Integer numeroTc) { this.numeroTc = numeroTc; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Integer getCv() { return cv; }
    public void setCv(Integer cv) { this.cv = cv; }
}
