package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

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
    @JoinColumn(name = "ID_Producto", nullable = false)
    private Producto producto;

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
    private Float precioTotal;

    @Column(name = "Cantidad")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "ID_MetPago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "Activo")
    private Boolean activo;

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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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

	public Float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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

	public Compra(Integer idCompra, Proveedor proveedor, Producto producto, Usuario usuario, Date fechaCreacion,
			Date fechaEntrega, Float precioTotal, Integer cantidad, MetodoPago metodoPago, Boolean activo) {
		super();
		this.idCompra = idCompra;
		this.proveedor = proveedor;
		this.producto = producto;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
		this.fechaEntrega = fechaEntrega;
		this.precioTotal = precioTotal;
		this.cantidad = cantidad;
		this.metodoPago = metodoPago;
		this.activo = activo;
	}

	public Compra() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters y Setters
    
    
    
}
