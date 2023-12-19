package com.example.khushi.clasesinfo;

public class nuevaSubParte extends nuevoProducto {

    private int id_subparte;
    private String subparte;

    public nuevaSubParte(int id_subparte, int id_producto, String subparte) {
        super(id_producto);
        this.id_subparte = id_subparte;
        this.subparte = subparte;
    }

    public nuevaSubParte(int id_subparte, int id_producto) {
        super(id_producto);
        this.id_subparte = id_subparte;

    }

    public nuevaSubParte(int id_subparte, String subparte) {
        super();
        this.id_subparte = id_subparte;
        this.subparte = (subparte);

    }

    public int getId_subparte() {
        return id_subparte;
    }


    public void setId_subparte(int id_subparte) {
        this.id_subparte = id_subparte;
    }


    public String getSubparte() {
        return subparte;
    }

    public void setSubparte(String subparte) {
        this.subparte = subparte;
    }


}
