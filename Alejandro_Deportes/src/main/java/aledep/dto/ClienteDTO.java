package aledep.dto;

public class ClienteDTO {
    private Integer idCliente;
    private String cuitDni;
    private Integer tipoCuitDni;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String preferencias;
    private String fechaIngreso;
    private String fechaUltimaCompra;
    private Integer cantidadCompras;
    private Float deuda;
    private Boolean activo;

    // Getters y Setters

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

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaUltimaCompra() {
        return fechaUltimaCompra;
    }

    public void setFechaUltimaCompra(String fechaUltimaCompra) {
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
}
