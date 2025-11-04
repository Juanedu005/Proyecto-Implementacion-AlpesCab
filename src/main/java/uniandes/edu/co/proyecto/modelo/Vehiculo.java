package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vehiculo")
public class Vehiculo {

    @Id
    @Column(name = "ID")
    private Integer id;

    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String placa;
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "Ciudad_id", referencedColumnName = "id")
    private Ciudad ciudad;

    /* Asociación compuesta correcta a Uconductor */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "Ucond_idcond",    referencedColumnName = "id_conductor"),
        @JoinColumn(name = "Ucond_idusuario", referencedColumnName = "id_usuario")
    })
    private Uconductor uconductor;

    public Vehiculo() { }

    public Vehiculo(Integer id, String tipo, String marca, String modelo, String color,
                    String placa, Integer capacidad, Ciudad ciudad, Uconductor uconductor) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.capacidad = capacidad;
        this.ciudad = ciudad;
        this.uconductor = uconductor;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }

    public Uconductor getUconductor() { return uconductor; }
    public void setUconductor(Uconductor uconductor) { this.uconductor = uconductor; }
}
