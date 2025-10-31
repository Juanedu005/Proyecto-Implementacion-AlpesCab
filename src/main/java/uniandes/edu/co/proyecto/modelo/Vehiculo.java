package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="Vehiculo")

public class Vehiculo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    

    private String tipo; 
    private String marca; 
    private String modelo; 
    private String color; 
    private String placa; 
    private Integer capacidad; 

    @ManyToOne
    @JoinColumn(name="Ciudad_id", referencedColumnName = "id")
    private Ciudad Ciudad_id; 

    @ManyToOne
    @JoinColumn(name="Ucond_idcond", referencedColumnName = "id_conductor")
    private Uconductor Ucond_idcond;

    
    @ManyToOne
    @JoinColumn(name="Ucond_idusuario", referencedColumnName = "id_conductor")
    private Uconductor Ucond_idusuario;

    


    public Vehiculo(Integer id, String tipo, String marca, String modelo, String color, String placa, Integer capacidad,
            Ciudad ciudad_id, Uconductor ucond_idcond, Uconductor ucond_idusuario) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.capacidad = capacidad;
        Ciudad_id = ciudad_id;
        Ucond_idcond = ucond_idcond;
        Ucond_idusuario = ucond_idusuario;
    }

    public Vehiculo(){;}


    public void setId(Integer id) {
        this.id = id;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }


    public void setCiudad_id(Ciudad ciudad_id) {
        Ciudad_id = ciudad_id;
    }


    public void setUcond_idcond(Uconductor ucond_idcond) {
        Ucond_idcond = ucond_idcond;
    }


    public void setUcond_idusuario(Uconductor ucond_idusuario) {
        Ucond_idusuario = ucond_idusuario;
    }


    public Integer getId() {
        return id;
    }


    public String getTipo() {
        return tipo;
    }


    public String getMarca() {
        return marca;
    }


    public String getModelo() {
        return modelo;
    }


    public String getColor() {
        return color;
    }


    public String getPlaca() {
        return placa;
    }


    public Integer getCapacidad() {
        return capacidad;
    }


    public Ciudad getCiudad_id() {
        return Ciudad_id;
    }


    public Uconductor getUcond_idcond() {
        return Ucond_idcond;
    }


    public Uconductor getUcond_idusuario() {
        return Ucond_idusuario;
    }


    

    



    
    
    

    
}
