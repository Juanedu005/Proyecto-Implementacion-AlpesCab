package uniandes.edu.co.proyecto.dto;

public class SolicitudDTO {
    private Integer usuarioId;
    private Integer puntoOrigenId;
    private Integer puntoDestinoId;
    private String nivel;
    private Integer distancia;
    private boolean forzarError;

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public Integer getPuntoOrigenId() { return puntoOrigenId; }
    public void setPuntoOrigenId(Integer puntoOrigenId) { this.puntoOrigenId = puntoOrigenId; }

    public Integer getPuntoDestinoId() { return puntoDestinoId; }
    public void setPuntoDestinoId(Integer puntoDestinoId) { this.puntoDestinoId = puntoDestinoId; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Integer getDistancia() { return distancia; }
    public void setDistancia(Integer distancia) { this.distancia = distancia; }

    public boolean isForzarError() { return forzarError; }
    public void setForzarError(boolean forzarError) { this.forzarError = forzarError; }
}
