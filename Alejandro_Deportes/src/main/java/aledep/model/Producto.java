package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRODUCTOS")
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Producto")
	private Integer idProducto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_Proveedor", referencedColumnName = "ID_Proveedor")
	private Proveedor proveedor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_Categoria", referencedColumnName = "ID_Categoria")
	private Categoria categoria;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_Deposito", referencedColumnName = "ID_Deposito")
	private Deposito deposito;

	@Column(name = "Codigo", nullable = false, unique = true)
	private String codigo;

	@Column(name = "Nombre", nullable = false)
	private String nombre;

	@Column(name = "Estilo")
	private String estilo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Marca", referencedColumnName = "ID_Marca")
	private Marca marca;

	@Column(name = "FechaCreacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;

	@Column(name = "Cantidad", nullable = false)
	private Integer cantidad;

	@Column(name = "CantidadMax")
	private Integer cantidadMax;

	@Column(name = "CantidadMin")
	private Integer cantidadMin;

	@Column(name = "PrecioCompra", nullable = true)
	private Double precioCompra;

	@Column(name = "PrecioVenta", nullable = false)
	private Double precioVenta;

	@Column(name = "Talle")
	private String talle;

	@Column(name = "Colores")
	private String colores;

	@Column(name = "ImageProduct")
	private String imageProduct;

	@Column(name = "CodigoBarras")
	private String codigoBarras;

	@Column(name = "EnlaceProducto")
	private String enlaceProducto;

	@Column(name = "Activo", nullable = false)
	private Boolean activo;

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getCantidadMax() {
		return cantidadMax;
	}

	public void setCantidadMax(Integer cantidadMax) {
		this.cantidadMax = cantidadMax;
	}

	public Integer getCantidadMin() {
		return cantidadMin;
	}

	public void setCantidadMin(Integer cantidadMin) {
		this.cantidadMin = cantidadMin;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public String getColores() {
		return colores;
	}

	public void setColores(String colores) {
		this.colores = colores;
	}

	public String getImageProduct() {
		return imageProduct;
	}

	public void setImageProduct(String imageProduct) {
		this.imageProduct = imageProduct;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getEnlaceProducto() {
		return enlaceProducto;
	}

	public void setEnlaceProducto(String enlaceProducto) {
		this.enlaceProducto = enlaceProducto;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Producto(Integer idProducto, String codigo, String nombre, Integer cantidad, Double precioCompra,
			Double precioVenta) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
	}

	public Producto(Integer idProducto, Proveedor proveedor, Categoria categoria, Deposito deposito, String codigo,
			String nombre, String estilo, Marca marca, Date fechaCreacion, Integer cantidad, Integer cantidadMax,
			Integer cantidadMin, Double precioCompra, Double precioVenta, String talle, String colores,
			String imageProduct, String codigoBarras, String enlaceProducto, Boolean activo) {
		super();
		this.idProducto = idProducto;
		this.proveedor = proveedor;
		this.categoria = categoria;
		this.deposito = deposito;
		this.codigo = codigo;
		this.nombre = nombre;
		this.estilo = estilo;
		this.marca = marca;
		this.fechaCreacion = fechaCreacion;
		this.cantidad = cantidad;
		this.cantidadMax = cantidadMax;
		this.cantidadMin = cantidadMin;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.talle = talle;
		this.colores = colores;
		this.imageProduct = imageProduct;
		this.codigoBarras = codigoBarras;
		this.enlaceProducto = enlaceProducto;
		this.activo = activo;
	}

	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto(String codigo, String categoria, String nombre, String marca, String talle, Integer cantidad,
			Double precioVenta) {
		this.codigo = codigo;
		this.categoria = new Categoria(); // Inicializar la categoría con el nombre correspondiente
		this.categoria.setNombre(categoria); // Se asume que solo tienes el nombre de la categoría
		this.nombre = nombre;
		this.marca = new Marca(); // Inicializar la marca con el nombre correspondiente
		this.marca.setNombre(marca);
		this.talle = talle;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
	}

	// Getters y Setters
}
