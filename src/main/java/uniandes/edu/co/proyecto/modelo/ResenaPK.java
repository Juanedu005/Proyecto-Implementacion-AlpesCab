package uniandes.edu.co.proyecto.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ResenaPK implements Serializable {

    @Column(name = "User_idusuario")
    private Integer user_idusuario;

    @Column(name = "Ucond_idusuario")
    private Integer ucond_idusuario;

    public ResenaPK() { }

    public ResenaPK(Integer user_idusuario, Integer ucond_idusuario) {
        this.user_idusuario = user_idusuario;
        this.ucond_idusuario = ucond_idusuario;
    }

    public Integer getUser_idusuario() { return user_idusuario; }
    public void setUser_idusuario(Integer user_idusuario) { this.user_idusuario = user_idusuario; }

    public Integer getUcond_idusuario() { return ucond_idusuario; }
    public void setUcond_idusuario(Integer ucond_idusuario) { this.ucond_idusuario = ucond_idusuario; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResenaPK)) return false;
        ResenaPK that = (ResenaPK) o;
        return Objects.equals(user_idusuario, that.user_idusuario) &&
               Objects.equals(ucond_idusuario, that.ucond_idusuario);
    }

    @Override public int hashCode() {
        return Objects.hash(user_idusuario, ucond_idusuario);
    }
}
