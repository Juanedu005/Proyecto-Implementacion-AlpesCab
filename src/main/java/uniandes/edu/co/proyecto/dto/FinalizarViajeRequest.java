package uniandes.edu.co.proyecto.dto;

import java.time.LocalDateTime;

public class FinalizarViajeRequest {
    private Integer servicioId;
    private Integer distanciaRecorrida;
    private LocalDateTime horaFin; 

    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

    public Integer getDistanciaRecorrida() { return distanciaRecorrida; }
    public void setDistanciaRecorrida(Integer distanciaRecorrida) { this.distanciaRecorrida = distanciaRecorrida; }

    public LocalDateTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalDateTime horaFin) { this.horaFin = horaFin; }
}

