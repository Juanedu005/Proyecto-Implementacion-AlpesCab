package uniandes.edu.co.proyecto.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    //Id no se declara en el constructor porque es automaticamente generado
    private Integer id; 
    private String nombre;
    private Integer cedula; 
    private String email; 

    public Usuario(String nombre, Integer cedula, String email){
        this.nombre=nombre; 
        this.cedula=cedula; 
        this.email=email;
    }

    public Usuario()
    {;}

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCedula() {
        return cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    
}
