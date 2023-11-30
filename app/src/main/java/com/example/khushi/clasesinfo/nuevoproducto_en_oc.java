package com.example.khushi.clasesinfo;

public class nuevoproducto_en_oc extends ordenDeCompraclase{

    private int id, idProducto, lotes, cantidad_de_productos;
    private String producto;
    public nuevoproducto_en_oc(int idOrdenCompra, String ordendeCompra) {
        super(idOrdenCompra, ordendeCompra);
    }

    public nuevoproducto_en_oc(int idOrdenCompra, String ordendeCompra,
                               int id, int idProducto, int lotes, int cantidad_de_productos,
                               String producto) {
        super(idOrdenCompra, ordendeCompra);
        this.id = id;
        this.idProducto = idProducto;
        this.lotes = lotes;
        this.cantidad_de_productos = cantidad_de_productos;
        this.producto = producto;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getLotes() {
        return lotes;
    }

    public void setLotes(int lotes) {
        this.lotes = lotes;
    }

    public int getCantidad_de_productos() {
        return cantidad_de_productos;
    }

    public void setCantidad_de_productos(int cantidad_de_productos) {
        this.cantidad_de_productos = cantidad_de_productos;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
