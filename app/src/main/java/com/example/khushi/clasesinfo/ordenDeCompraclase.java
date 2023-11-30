package com.example.khushi.clasesinfo;

public class ordenDeCompraclase {

    private int idOrdenCompra;
    private String ordendeCompra;

    public ordenDeCompraclase(int idOrdenCompra, String ordendeCompra) {
        this.idOrdenCompra = idOrdenCompra;
        this.ordendeCompra = ordendeCompra;
    }

    public int getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(int idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getOrdendeCompra() {
        return ordendeCompra;
    }

    public void setOrdendeCompra(String ordendeCompra) {
        this.ordendeCompra = ordendeCompra;
    }
}
