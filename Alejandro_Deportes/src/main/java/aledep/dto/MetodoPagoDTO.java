package aledep.dto;

public class MetodoPagoDTO {
	private Integer idMetodoPago;
	private String nombre;
	private Integer cantidadUsos;

	public Integer getIdMetodoPago() {
		return idMetodoPago;
	}

	public void setIdMetodoPago(Integer idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidadUsos() {
		return cantidadUsos;
	}

	public void setCantidadUsos(Integer cantidadUsos) {
		this.cantidadUsos = cantidadUsos;
	}

}
