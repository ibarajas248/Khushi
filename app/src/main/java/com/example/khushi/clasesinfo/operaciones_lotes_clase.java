package com.example.khushi.clasesinfo;

public class operaciones_lotes_clase {

    private String producto, subparte, operaciones,empleado;
    int id_lotes_operaciones, cantidad, id_operacione_subparte_producto;

    public operaciones_lotes_clase(String producto, String subparte, String operaciones, int id_lotes_operaciones, int cantidad, String empleado, int id_operacione_subparte_producto) {
        this.producto = producto;
        this.subparte = subparte;
        this.operaciones = operaciones;
        this.id_lotes_operaciones = id_lotes_operaciones;
        this.cantidad = cantidad;
        this.empleado = empleado;
        this.id_operacione_subparte_producto = id_operacione_subparte_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getSubparte() {
        return subparte;
    }

    public void setSubparte(String subparte) {
        this.subparte = subparte;
    }

    public String getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(String operaciones) {
        this.operaciones = operaciones;
    }

    public int getId_lotes_operaciones() {
        return id_lotes_operaciones;
    }

    public void setId_lotes_operaciones(int id_lotes_operaciones) {
        this.id_lotes_operaciones = id_lotes_operaciones;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public int getId_operacione_subparte_producto() {
        return id_operacione_subparte_producto;
    }

    public void setId_operacione_subparte_producto(int id_operacione_subparte_producto) {
        this.id_operacione_subparte_producto = id_operacione_subparte_producto;
    }
}
