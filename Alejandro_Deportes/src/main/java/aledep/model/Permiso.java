package aledep.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERMISOS")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Permiso")
    private Integer idPermiso;

    @Column(name = "Nombre", length = 256, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", length = 256)
    private String descripcion;

    @Column(name = "Activo")
    private Boolean activo;

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.EAGER)
    private Set<Rol> roles = new HashSet<>();

    // Constructor vacío
    public Permiso() {}

    // Constructor con parámetros
    public Permiso(Integer idPermiso, String nombre, String descripcion, Boolean activo) {
        this.idPermiso = idPermiso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y Setters
    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
