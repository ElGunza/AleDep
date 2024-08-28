package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PROVEEDOR")
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Proveedor")
    private Integer idProveedor;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "CUIT_DNI")
    private String cuitDni;

    @Column(name = "TipoCUIT_DNI")
    private Integer tipoCuitDni;

    @Column(name = "Contacto")
    private String contacto;

    @OneToMany(mappedBy = "proveedor")
    private List<Producto> productos;

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuitDni() {
		return cuitDni;
	}

	public void setCuitDni(String cuitDni) {
		this.cuitDni = cuitDni;
	}

	public Integer getTipoCuitDni() {
		return tipoCuitDni;
	}

	public void setTipoCuitDni(Integer tipoCuitDni) {
		this.tipoCuitDni = tipoCuitDni;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Proveedor(Integer idProveedor, String nombre, String cuitDni, Integer tipoCuitDni, String contacto,
			List<Producto> productos) {
		super();
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.cuitDni = cuitDni;
		this.tipoCuitDni = tipoCuitDni;
		this.contacto = contacto;
		this.productos = productos;
	}

	public Proveedor() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters y Setters
    
    
    
}
