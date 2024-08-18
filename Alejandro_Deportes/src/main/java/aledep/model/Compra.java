package aledep.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COMPRAS")
public class Compra {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID_Compra")
	private Integer idCompra;

	@OneToOne
	@JoinColumn(name = "ID_Proveedor", nullable = false)
	private Proveedor idProveedor;

	@OneToMany
	@JoinColumn(name = "ID_Producto", nullable = false)
	private Producto idProducto;

	@OneToOne
	@JoinColumn(name = "ID_Usuario", nullable = false)
	private Usuario idUsuario;

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

	@Column(name = "ID_MetPago", nullable = false)
	private Integer idMetPago;

	@Column(name = "Activo")
	private Boolean activo;

	// Constructor, Getters y Setters los puedes agregar aquí
}
