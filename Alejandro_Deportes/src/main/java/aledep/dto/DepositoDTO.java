package aledep.dto;

import aledep.model.Deposito;

public class DepositoDTO {

    private Integer idDeposito;
    private String nombre;
    private String descripcion;
    private Integer ubicacion;
    private Integer capacidad;
    private Boolean activo;

    // Constructor que convierte un Deposito en un DepositoDTO
    public DepositoDTO(Deposito deposito) {
        this.idDeposito = deposito.getIdDeposito();
        this.nombre = deposito.getNombre();
        this.descripcion = deposito.getDescripcion();
        this.ubicacion = deposito.getUbicacion();
        this.capacidad = deposito.getCapacidad();
        this.activo = deposito.getActivo();
    }

    // Getters y Setters
    public Integer getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
