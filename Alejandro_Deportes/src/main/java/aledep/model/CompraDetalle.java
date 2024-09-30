package aledep.model;

import javax.persistence.*;

@Entity
@Table(name = "COMPRA_DETALLE")
public class CompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompraDetalle", updatable = false, nullable = false)
    private Integer idCompraDetalle;

    @ManyToOne
    @JoinColumn(name = "ID_Compra", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "ID_Producto", nullable = false)
    private Producto producto;

    @Column(name = "Cantidad")
    private Float cantidad;

    @Column(name = "PrecioUnitario")
    private Double precioUnitario;

    // Getters y Setters

    public Integer getIdCompraDetalle() {
        return idCompraDetalle;
    }

    public void setIdCompraDetalle(Integer idCompraDetalle) {
        this.idCompraDetalle = idCompraDetalle;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
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

    // Constructor vacío
    public CompraDetalle() {
    }

    // Constructor con parámetros
    public CompraDetalle(Integer idCompraDetalle, Compra compra, Producto producto, Float cantidad, Double precioUnitario) {
        this.idCompraDetalle = idCompraDetalle;
        this.compra = compra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
}
