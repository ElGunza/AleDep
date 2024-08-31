package aledep.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CATEGORIA")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Categoria")
    private Integer idCategoria;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Activo")
    private Boolean activo;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    // Getters y Setters

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
