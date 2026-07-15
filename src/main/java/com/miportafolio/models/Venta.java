package com.miportafolio.models;

import java.util.Date;

public class Venta {
    private int id;
    private int idProducto;
    private int cantidad;
    private double total;
    private Date fecha;

    public Venta() {}

    public Venta(int idProducto, int cantidad, double total) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
