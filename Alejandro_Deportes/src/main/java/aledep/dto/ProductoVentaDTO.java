package aledep.dto;

public class ProductoVentaDTO {
    private String nombreProducto;
    private Float cantidadVendida;
    private Double precioVenta;
    private Integer stockActual;
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public Float getCantidadVendida() {
		return cantidadVendida;
	}
	public void setCantidadVendida(Float cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}
	public Double getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}
	public Integer getStockActual() {
		return stockActual;
	}
	public void setStockActual(Integer stockActual) {
		this.stockActual = stockActual;
	}

    
}
