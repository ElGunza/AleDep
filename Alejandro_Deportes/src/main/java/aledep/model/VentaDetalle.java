package aledep.model;

import javax.persistence.*;

@Entity
@Table(name = "VENTA_DETALLE")
public class VentaDetalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVentaDetalle", updatable = false, nullable = false)
	private Integer idVentaDetalle;


	@ManyToOne
	@JoinColumn(name = "ID_Venta", nullable = false)
	private Venta venta;

	@ManyToOne
	@JoinColumn(name = "ID_Producto", nullable = false)
	private Producto producto;

	@Column(name = "Cantidad")
	private Float cantidad;

	@Column(name = "PrecioUnitario")
	private Double precioUnitario;

	public Integer getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(Integer idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public VentaDetalle(Integer idVentaDetalle, Venta venta, Producto producto, Float cantidad, Double precioUnitario) {
		super();
		this.idVentaDetalle = idVentaDetalle;
		this.venta = venta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public VentaDetalle() {
		super();
	}

}
