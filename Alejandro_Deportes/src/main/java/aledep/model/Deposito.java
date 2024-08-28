package aledep.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DEPOSITOS")
public class Deposito implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Deposito")
    private Integer idDeposito;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Ubicacion")
    private Integer ubicacion;

    @Column(name = "Capacidad")
    private Integer capacidad; // Nuevo campo para la capacidad

    @Column(name = "Activo")
    private Boolean activo;

    @OneToMany(mappedBy = "deposito", fetch = FetchType.EAGER)
    private List<Producto> productos;

    // Getters y setters

    public Integer getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad; // Getter para la capacidad
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad; // Setter para la capacidad
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Deposito() {
        super();
    }
}
