package aledep.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CONFIGURACION_EMPRESA")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Config")
	private Integer idConfig;

	@Column(name = "Nombre", length = 255)
	private String nombre;

	@Column(name = "RazonSocial", length = 255)
	private String razonSocial;

	@Column(name = "CUIT", length = 11)
	private String cuit;

	@Column(name = "Direccion", length = 255)
	private String direccion;

	@Column(name = "Telefono", length = 13)
	private String telefono;

	@Column(name = "Email", length = 255)
	private String email;

	@Column(name = "LogoPath", length = 255)
	private String logoPath;

	@Column(name = "MonedaLegal", length = 3)
	private String monedaLegal;

	@Column(name = "AccessHistory")
	private Boolean accessHistory;

	@Column(name = "ModificationHistory")
	private Boolean modificationHistory;

	@Column(name = "DeletionHistory")
	private Boolean deletionHistory;

	@Column(name = "AlertaMinimoStock")
	private Float alertaMinimoStock;

	// Getters y Setters
	public Integer getIdConfig() {
		return idConfig;
	}

	public void setIdConfig(Integer idConfig) {
		this.idConfig = idConfig;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
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

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getMonedaLegal() {
		return monedaLegal;
	}

	public void setMonedaLegal(String monedaLegal) {
		this.monedaLegal = monedaLegal;
	}

	public Boolean getAccessHistory() {
		return accessHistory;
	}

	public void setAccessHistory(Boolean accessHistory) {
		this.accessHistory = accessHistory;
	}

	public Boolean getModificationHistory() {
		return modificationHistory;
	}

	public void setModificationHistory(Boolean modificationHistory) {
		this.modificationHistory = modificationHistory;
	}

	public Boolean getDeletionHistory() {
		return deletionHistory;
	}

	public void setDeletionHistory(Boolean deletionHistory) {
		this.deletionHistory = deletionHistory;
	}

	public Float getAlertaMinimoStock() {
		return alertaMinimoStock;
	}

	public void setAlertaMinimoStock(Float alertaMinimoStock) {
		this.alertaMinimoStock = alertaMinimoStock;
	}

	public Empresa(Integer idConfig, String nombre, String razonSocial, String cuit, String direccion, String telefono,
			String email, String logoPath, String monedaLegal, Boolean accessHistory, Boolean modificationHistory,
			Boolean deletionHistory, Float alertaMinimoStock) {
		super();
		this.idConfig = idConfig;
		this.nombre = nombre;
		this.razonSocial = razonSocial;
		this.cuit = cuit;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.logoPath = logoPath;
		this.monedaLegal = monedaLegal;
		this.accessHistory = accessHistory;
		this.modificationHistory = modificationHistory;
		this.deletionHistory = deletionHistory;
		this.alertaMinimoStock = alertaMinimoStock;
	}

	public Empresa() {
		super();
	}

}
