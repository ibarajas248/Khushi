package com.example.khushi.clasesinfo;

public class nuevoProducto {
    private String producto;
    private int id_producto;
    private float precio;
    private String foto;

    public nuevoProducto(String producto, int id_producto, float precio) {
        this.producto = producto;
        this.id_producto = id_producto;
        this.precio = precio;
    }
    public nuevoProducto(String producto, int id_producto, float precio, String foto) {
        this.producto = producto;
        this.id_producto = id_producto;
        this.precio = precio;
        this.foto = foto;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public nuevoProducto(int idProducto) {
        this.id_producto = id_producto;
    }

    public nuevoProducto() {

    }


    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }


}
