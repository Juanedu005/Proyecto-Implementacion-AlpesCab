package uniandes.edu.co.proyecto.dto;

public record PuntoRespuestaDTO(
        Integer puntoId,
        String direccion,
        String latitud,
        String longitud,
        Integer ciudadId,
        Integer servicioId
) {}
