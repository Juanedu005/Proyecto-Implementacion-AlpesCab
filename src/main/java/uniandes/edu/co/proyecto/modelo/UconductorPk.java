package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UconductorPk implements Serializable {

    @Column(name = "id_conductor")
    private Integer id_conductor;

    @Column(name = "id_usuario")
    private Integer id_usuario;

    public UconductorPk() { }

    public UconductorPk(Integer id_conductor, Integer id_usuario) {
        this.id_conductor = id_conductor;
        this.id_usuario = id_usuario;
    }

    public Integer getId_conductor() { return id_conductor; }
    public void setId_conductor(Integer id_conductor) { this.id_conductor = id_conductor; }

    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UconductorPk)) return false;
        UconductorPk that = (UconductorPk) o;
        return Objects.equals(id_conductor, that.id_conductor) &&
               Objects.equals(id_usuario, that.id_usuario);
    }

    @Override public int hashCode() {
        return Objects.hash(id_conductor, id_usuario);
    }
}
