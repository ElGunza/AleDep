package aledep.dto;

public class ProveedorDTO {
    private Integer idProveedor;
    private String nombre;
    private String cuitDni;
    private String tipoCuitDni;
    private String contacto;
    private Boolean activo;

    // Getters y Setters
    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuitDni() {
        return cuitDni;
    }

    public void setCuitDni(String cuitDni) {
        this.cuitDni = cuitDni;
    }

    public String getTipoCuitDni() {
        return tipoCuitDni;
    }

    public void setTipoCuitDni(String tipoCuitDni) {
        this.tipoCuitDni = tipoCuitDni;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
