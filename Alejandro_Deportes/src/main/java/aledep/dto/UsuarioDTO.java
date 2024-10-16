package aledep.dto;

public class UsuarioDTO {
    private Integer idUsuario;
    private String nombre;
    private String email;
    private Boolean activo;
    private String rol; // Nombre del rol
    private Integer idRol; // ID del rol
    private Integer cantidadVentas = 0; // Cantidad de ventas realizadas, inicializada a 0
    private Double totalVentas = 0.0; // Valor total de las ventas realizadas, inicializada a 0.0

    // Getters y Setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(Integer cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public Double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Double totalVentas) {
        this.totalVentas = totalVentas;
    }
    
    public void incrementarVentas(double monto) {
        if (this.cantidadVentas == null) {
            this.cantidadVentas = 0;
        }
        if (this.totalVentas == null) {
            this.totalVentas = 0.0;
        }
        this.cantidadVentas++;
        this.totalVentas += monto;
    }

    public UsuarioDTO() {
        super();
    }

    public UsuarioDTO(String nombre) {
        super();
        this.nombre = nombre;
    }
}
