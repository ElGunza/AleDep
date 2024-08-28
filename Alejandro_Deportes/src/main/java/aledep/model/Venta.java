package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "VENTAS")
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_Producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "FechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Column(name = "Cantidad")
    private Float cantidad;

    @Column(name = "PrecioTotal")
    private Float precioTotal;

    @ManyToOne
    @JoinColumn(name = "ID_MetPago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "Activo")
    private Boolean activo;

	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public Float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Float precioTotal) {
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

	public Venta(Integer idVenta, Cliente cliente, Producto producto, Usuario usuario, Date fechaCreacion,
			Float cantidad, Float precioTotal, MetodoPago metodoPago, Boolean activo) {
		super();
		this.idVenta = idVenta;
		this.cliente = cliente;
		this.producto = producto;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
		this.metodoPago = metodoPago;
		this.activo = activo;
	}

	public Venta() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters y Setters
    
    
    
}
