package uniandes.edu.co.proyecto.dto;

import java.time.LocalDateTime;

public class FinalizarViajeResponse {
    private Integer servicioId;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private Integer distanciaRecorrida;
    private String estado;

    public FinalizarViajeResponse(Integer servicioId, LocalDateTime horaInicio, LocalDateTime horaFin, Integer distanciaRecorrida) {
        this.servicioId = servicioId;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.distanciaRecorrida = distanciaRecorrida;
        this.estado = "FINALIZADO";
    }

    public Integer getServicioId() { return servicioId; }
    public LocalDateTime getHoraInicio() { return horaInicio; }
    public LocalDateTime getHoraFin() { return horaFin; }
    public Integer getDistanciaRecorrida() { return distanciaRecorrida; }
    public String getEstado() { return estado; }
}

