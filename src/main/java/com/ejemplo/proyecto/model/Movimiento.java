package com.ejemplo.proyecto.model;

import java.time.LocalDateTime;

public class Movimiento {
    private int idMovimiento;
    private int idProducto;
    private int tipo; // entrada o salida
    private int cantidad;
    private int idUsuario;
    private LocalDateTime fechaHora;

    // Campos opcionales para mostrar información extra (no necesariamente en DB)
    private String nombreProducto;
    private String nombreUsuario;

    public Movimiento() {
    }

    public Movimiento(int idMovimiento, int idProducto, int tipo, int cantidad, int idUsuario, LocalDateTime fechaHora) {
        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public int getId() {
        return idMovimiento;
    }

    public void setId(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getidProducto() {
        return idProducto;
    }

    public void setidProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getidUsuario() {
        return idUsuario;
    }

    public void setidUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
