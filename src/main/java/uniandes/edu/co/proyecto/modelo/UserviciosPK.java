package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Embeddable
public class UserviciosPK implements Serializable {
    
    @OneToOne
    @JoinColumn(name="id_usuario", referencedColumnName = "id")
    private Usuario id_usuario;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id_servicios; 


    public UserviciosPK(){
        super();
    }


    public UserviciosPK(Usuario id_usuario, Integer id_servicios) {
        super();
        this.id_usuario = id_usuario;
        this.id_servicios = id_servicios;
    }


    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }


    public void setId_servicios(Integer id_servicios) {
        this.id_servicios = id_servicios;
    }


    public Usuario getId_usuario() {
        return id_usuario;
    }


    public Integer getId_servicios() {
        return id_servicios;
    }
    
}
