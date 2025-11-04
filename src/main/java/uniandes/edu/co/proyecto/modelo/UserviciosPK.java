package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserviciosPK implements Serializable {

    @Column(name = "ID_USUARIO")
    private Integer id_usuario;

    @Column(name = "ID_SERVICIOS")
    private Integer id_servicios;

    public UserviciosPK() { }

    public UserviciosPK(Integer id_usuario, Integer id_servicios) {
        this.id_usuario = id_usuario;
        this.id_servicios = id_servicios;
    }

    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }

    public Integer getId_servicios() { return id_servicios; }
    public void setId_servicios(Integer id_servicios) { this.id_servicios = id_servicios; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserviciosPK)) return false;
        UserviciosPK that = (UserviciosPK) o; // <- aquí estaba el typo
        return Objects.equals(id_usuario, that.id_usuario)
            && Objects.equals(id_servicios, that.id_servicios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario, id_servicios);
    }
}
