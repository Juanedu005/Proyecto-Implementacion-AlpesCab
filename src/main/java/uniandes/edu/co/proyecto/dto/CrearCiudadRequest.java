package uniandes.edu.co.proyecto.dto;

public class CrearCiudadRequest {
    private String nombre;

    public CrearCiudadRequest() {}
    public CrearCiudadRequest(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
