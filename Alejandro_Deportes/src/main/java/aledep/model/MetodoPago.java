package aledep.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "METODOS_PAGO")
public class MetodoPago implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MetPago")
    private Integer idMetPago;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

	public Integer getIdMetPago() {
		return idMetPago;
	}

	public void setIdMetPago(Integer idMetPago) {
		this.idMetPago = idMetPago;
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

	public MetodoPago() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters y Setters
    
    
}
