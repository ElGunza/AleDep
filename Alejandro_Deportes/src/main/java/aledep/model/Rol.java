package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES")
public class Rol implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Rol")
    private Integer idRol;

    @Column(name = "Nombre", length = 255, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", length = 255)
    private String descripcion;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "ROL_PERMISO",
        joinColumns = @JoinColumn(name = "ID_Rol"),
        inverseJoinColumns = @JoinColumn(name = "ID_Permiso")
    )
    private Set<Permiso> permisos = new HashSet<>();

    // Constructor vacío
    public Rol() {}

    // Constructor con parámetros
    public Rol(Integer idRol, String nombre, String descripcion) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
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

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public void addPermiso(Permiso permiso) {
        this.permisos.add(permiso);
        permiso.getRoles().add(this);
    }

    public void removePermiso(Permiso permiso) {
        this.permisos.remove(permiso);
        permiso.getRoles().remove(this);
    }
}
