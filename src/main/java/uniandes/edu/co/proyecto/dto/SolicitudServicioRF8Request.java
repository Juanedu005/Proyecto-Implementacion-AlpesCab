package uniandes.edu.co.proyecto.dto;

public class SolicitudServicioRF8Request {
    private Integer idUsuarioServicio;

    private String tipoServicio;
    private String nivelPasajero;
    private Integer idCiudad;
    private Integer idPuntoOrigen; 
    private Integer idPuntoDestino; 
    private Integer distanciaKm;

    private boolean forzarError;


    public Integer getIdUsuarioServicio() { return idUsuarioServicio; }
    public void setIdUsuarioServicio(Integer idUsuarioServicio) { this.idUsuarioServicio = idUsuarioServicio; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getNivelPasajero() { return nivelPasajero; }
    public void setNivelPasajero(String nivelPasajero) { this.nivelPasajero = nivelPasajero; }

    public Integer getIdCiudad() { return idCiudad; }
    public void setIdCiudad(Integer idCiudad) { this.idCiudad = idCiudad; }

    public Integer getIdPuntoOrigen() { return idPuntoOrigen; }
    public void setIdPuntoOrigen(Integer idPuntoOrigen) { this.idPuntoOrigen = idPuntoOrigen; }

    public Integer getIdPuntoDestino() { return idPuntoDestino; }
    public void setIdPuntoDestino(Integer idPuntoDestino) { this.idPuntoDestino = idPuntoDestino; }

    public Integer getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Integer distanciaKm) { this.distanciaKm = distanciaKm; }

    public boolean isForzarError() { return forzarError; }
    public void setForzarError(boolean forzarError) { this.forzarError = forzarError; }
}
