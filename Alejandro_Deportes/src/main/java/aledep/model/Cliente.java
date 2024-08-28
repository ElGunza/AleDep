package aledep.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CLIENTES")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cliente")
    private Integer idCliente;

    @Column(name = "CUIT_DNI", length = 255, nullable = false)
    private String cuitDni;

    @Column(name = "TipoCUIT_DNI", nullable = false)
    private Integer tipoCuitDni;

    @Column(name = "Nombre", length = 255, nullable = false)
    private String nombre;

    @Column(name = "Direccion", length = 255)
    private String direccion;

    @Column(name = "Telefono", length = 13)
    private String telefono;

    @Column(name = "Email", length = 256)
    private String email;

    @Column(name = "Preferencias", length = 256)
    private String preferencias;

    @Column(name = "FechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;

    @Column(name = "FechaUltimaCompra")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimaCompra;

    @Column(name = "CantidadCompras")
    private Integer cantidadCompras;

    @Column(name = "Deuda")
    private Float deuda;

    @Column(name = "Activo")
    private Boolean activo;

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(String preferencias) {
		this.preferencias = preferencias;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaUltimaCompra() {
		return fechaUltimaCompra;
	}

	public void setFechaUltimaCompra(Date fechaUltimaCompra) {
		this.fechaUltimaCompra = fechaUltimaCompra;
	}

	public Integer getCantidadCompras() {
		return cantidadCompras;
	}

	public void setCantidadCompras(Integer cantidadCompras) {
		this.cantidadCompras = cantidadCompras;
	}

	public Float getDeuda() {
		return deuda;
	}

	public void setDeuda(Float deuda) {
		this.deuda = deuda;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Cliente(Integer idCliente, String cuitDni, Integer tipoCuitDni, String nombre, String direccion,
			String telefono, String email, String preferencias, Date fechaIngreso, Date fechaUltimaCompra,
			Integer cantidadCompras, Float deuda, Boolean activo) {
		super();
		this.idCliente = idCliente;
		this.cuitDni = cuitDni;
		this.tipoCuitDni = tipoCuitDni;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.preferencias = preferencias;
		this.fechaIngreso = fechaIngreso;
		this.fechaUltimaCompra = fechaUltimaCompra;
		this.cantidadCompras = cantidadCompras;
		this.deuda = deuda;
		this.activo = activo;
	}

	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
