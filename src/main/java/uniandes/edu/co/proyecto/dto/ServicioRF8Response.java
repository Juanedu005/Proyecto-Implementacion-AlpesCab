package uniandes.edu.co.proyecto.dto;

import java.time.LocalDateTime;

public record ServicioRF8Response(
        Integer id,
        Integer tarifaFija,
        Integer distanciaKm,
        LocalDateTime horaInicio,
        LocalDateTime horaFin,
        Integer puntoOrigenId,
        Integer puntoDestinoId,
        Integer ciudadId
) {}
