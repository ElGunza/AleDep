package aledep.dto;

import java.util.Date;
import java.util.List;

public class VentaDTO {
    private Integer idVenta;
    private String cliente;
    private Integer idCliente;
    private String metodoPago;
    private Integer idMetodoPago;
    private String usuario;
    private Integer idUsuario;
    private Date fechaCreacion;
    private String fechaCreacionStr; // Nuevo campo para manejar la fecha como String
    private Double precioTotal;
    private List<VentaDetalleDTO> detalles;
    private List<Integer> idsVentas;

	public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCreacionStr() {
        return fechaCreacionStr;
    }

    public void setFechaCreacionStr(String fechaCreacionStr) {
        this.fechaCreacionStr = fechaCreacionStr;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public List<VentaDetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<VentaDetalleDTO> detalles) {
        this.detalles = detalles;
    }
    
    public VentaDTO() {}

    public VentaDTO(String fechaCreacionStr, double precioTotal, List<VentaDetalleDTO> detalles) {
        this.fechaCreacionStr = fechaCreacionStr;
        this.precioTotal = precioTotal;
        this.detalles = detalles;
    }

	public VentaDTO(String fechaCreacionStr, List<Integer> idsVentas, Double precioTotal) {
		super();
		this.fechaCreacionStr = fechaCreacionStr;
		this.setIdsVentas(idsVentas);
		this.precioTotal = precioTotal;
	}


	public List<Integer> getIdsVentas() {
		return idsVentas;
	}

	public void setIdsVentas(List<Integer> idsVentas) {
		this.idsVentas = idsVentas;
	}
    
    
    
}
