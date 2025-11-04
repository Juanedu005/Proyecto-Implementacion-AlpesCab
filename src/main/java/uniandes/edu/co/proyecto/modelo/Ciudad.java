package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CIUDAD")
@SequenceGenerator(
    name = "CIUDAD_SEQ",
    sequenceName = "CIUDAD_ID_SEQ", // tu script crea Ciudad_id_SEQ -> Oracle lo guarda como CIUDAD_ID_SEQ
    allocationSize = 1
)
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CIUDAD_SEQ")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    public Ciudad() {}
    public Ciudad(Integer id, String nombre) { this.id = id; this.nombre = nombre; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}


    

