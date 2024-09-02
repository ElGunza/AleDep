package aledep.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "SUBCATEGORIA")
public class Subcategoria implements Serializable {
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Subcategoria")
	private Integer idSubcategoria;

	@Column(name = "Nombre")
	private String nombre;

	@Column(name = "Descripcion")
	private String descripcion;

	@Column(name = "Tipo")
	private Integer tipo;

	public Integer getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(Integer idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Subcategoria(Integer idSubcategoria, String nombre, String descripcion, Integer tipo) {
		super();
		this.idSubcategoria = idSubcategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
	}

	public Subcategoria() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters

}
