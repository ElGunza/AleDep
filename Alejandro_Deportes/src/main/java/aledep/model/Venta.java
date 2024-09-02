package aledep.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VENTAS")
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Venta")
    private Integer idVenta;

    @ManyToOne
    @JoinColumn(name = "ID_Cliente", nullable = true)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario", nullable = true)
    private Usuario usuario;

    @Column(name = "FechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Column(name = "PrecioTotal")
    private Double precioTotal;

    @ManyToOne
    @JoinColumn(name = "ID_MetPago", nullable = true)
    private MetodoPago metodoPago;

    @Column(name = "Activo")
    private Boolean activo;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VentaDetalle> detalles = new ArrayList<>();

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

	public List<VentaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<VentaDetalle> detalles) {
		this.detalles = detalles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Venta(Integer idVenta, Cliente cliente, Usuario usuario, Date fechaCreacion, Double precioTotal,
			MetodoPago metodoPago, Boolean activo, List<VentaDetalle> detalles) {
		super();
		this.idVenta = idVenta;
		this.cliente = cliente;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
		this.precioTotal = precioTotal;
		this.metodoPago = metodoPago;
		this.activo = activo;
		this.detalles = detalles;
	}

	public Venta() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters y Setters
    
    
    
}
