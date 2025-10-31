package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ciudad")
public class Ciudad {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id; 

    private String nombre;

    public Ciudad(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    } 

    public Ciudad(){;}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    

    
}
