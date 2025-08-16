package com.ejemplo.proyecto.model;

public class Producto {
    private int idProducto;
    private String nombre;
    private int cantidad;
    private int estatus;

    public Producto() {
    }

    public Producto(int idProducto, String nombre,int cantidad,int estatus){
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.estatus = estatus;
    }

    // Getters y Setters
    public int getIdProducto() {return idProducto;}
    public void setIdProducto(int idProducto) {this.idProducto = idProducto;}
    
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public int getEstatus() {return estatus;}
    public void setEstatus(int estatus) {this.estatus = estatus;}
}
