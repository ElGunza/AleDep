package aledep.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Integer idUsuario;

    @ManyToOne
    @JoinColumn(name = "ID_Rol", nullable = false)
    private Rol rol;

    @Column(name = "Name")
    private String nombre;

    @Column(name = "Password")
    private String password;

    @Column(name = "Email")
    private String email;

    @Column(name = "Activo")
    private Boolean activo;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Usuario(Integer idUsuario, Rol rol, String nombre, String password, String email, Boolean activo) {
		super();
		this.idUsuario = idUsuario;
		this.rol = rol;
		this.nombre = nombre;
		this.password = password;
		this.email = email;
		this.activo = activo;
	}

	public Usuario() {
		super();
	}

    // Getters y Setters
    
    
}
