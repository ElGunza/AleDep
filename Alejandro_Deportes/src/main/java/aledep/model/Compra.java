package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "COMPRAS")
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Compra")
	private Integer idCompra;

	@ManyToOne
	@JoinColumn(name = "ID_Proveedor", nullable = false)
	private Proveedor proveedor;

	@ManyToOne
	@JoinColumn(name = "ID_Usuario", nullable = false)
	private Usuario usuario;

	@Column(name = "FechaCreacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;

	@Column(name = "FechaEntrega")
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;

	@Column(name = "PrecioTotal")
	private Double precioTotal;


	@ManyToOne
	@JoinColumn(name = "ID_MetPago", nullable = false)
	private MetodoPago metodoPago;

	@Column(name = "Activo")
	private Boolean activo;

	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompraDetalle> detalles = new ArrayList<>();

	public Integer getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public List<CompraDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<CompraDetalle> detalles) {
		this.detalles = detalles;
	}

	public Compra(Integer idCompra, Proveedor proveedor, Usuario usuario, Date fechaCreacion,
			Date fechaEntrega, Double precioTotal, MetodoPago metodoPago, Boolean activo,
			List<CompraDetalle> detalles) {
		super();
		this.idCompra = idCompra;
		this.proveedor = proveedor;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
		this.fechaEntrega = fechaEntrega;
		this.precioTotal = precioTotal;
		this.metodoPago = metodoPago;
		this.activo = activo;
		this.detalles = detalles;
	}

	

	public Compra() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters

}
