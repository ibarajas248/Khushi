package com.example.khushi.clasesinfo;

public class operacionesFiltradas {
    private boolean isChecked;
    private int idProducto, idSubparte,idOperaciones,id_precio,id_asignacion_operacion;
    private String producto,subparte,operaciones, maquina;
    private float cantidad, precio;



    public operacionesFiltradas(int idProducto, int idSubparte, int idOperaciones, String producto, String subparte, String operaciones, String maquina, float cantidad, float precio) {
        this.idProducto = idProducto;
        this.idSubparte = idSubparte;
        this.idOperaciones = idOperaciones;
        this.producto = producto;
        this.subparte = subparte;
        this.operaciones = operaciones;
        this.maquina = maquina;
        this.cantidad = cantidad;
        this.precio= precio;
    }
    public operacionesFiltradas(int idProducto, int idSubparte, int idOperaciones, String producto, String subparte, String operaciones, String maquina, float cantidad, float precio,int id_precio,int id_asignacion_operacion) {
        this.idProducto = idProducto;
        this.idSubparte = idSubparte;
        this.idOperaciones = idOperaciones;
        this.producto = producto;
        this.subparte = subparte;
        this.operaciones = operaciones;
        this.maquina = maquina;
        this.cantidad = cantidad;
        this.precio= precio;
        this.id_precio=id_precio;
        this.id_asignacion_operacion=id_asignacion_operacion;
    }

    public int getId_asignacion_operacion() {
        return id_asignacion_operacion;
    }

    public void setId_asignacion_operacion(int id_asignacion_operacion) {
        this.id_asignacion_operacion = id_asignacion_operacion;
    }

    public int getId_precio() {
        return id_precio;
    }

    public void setId_precio(int id_precio) {
        this.id_precio = id_precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdSubparte() {
        return idSubparte;
    }

    public void setIdSubparte(int idSubparte) {
        this.idSubparte = idSubparte;
    }

    public int getIdOperaciones() {
        return idOperaciones;
    }

    public void setIdOperaciones(int idOperaciones) {
        this.idOperaciones = idOperaciones;
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

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
