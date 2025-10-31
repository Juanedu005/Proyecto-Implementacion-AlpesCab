package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;


import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Embeddable
public class UconductorPk implements Serializable {
    @OneToOne
    @JoinColumn(name="id_usuario", referencedColumnName = "id")
    private Usuario id_usuario;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id_conductor; 

        public UconductorPk() {
            super();
    }

    
    public UconductorPk(Usuario id_usuario, Integer id_conductor) {
            super();
            this.id_usuario = id_usuario;
            this.id_conductor = id_conductor;
        }

    
    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }


    public void setId_conductor(Integer id_conductor) {
        this.id_conductor = id_conductor;
    }


    

    public Usuario getId_usuario() {
        return id_usuario;
    }


    public Integer getId_conductos() {
        return id_conductor;
    }



}
