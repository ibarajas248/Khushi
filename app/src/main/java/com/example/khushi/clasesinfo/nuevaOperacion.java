package com.example.khushi.clasesinfo;

public class nuevaOperacion extends nuevaSubParte {

    private int id_operacion;
    private float cantidad;
    private String nombreOperacion, maquina;


    public nuevaOperacion(int id_subparte, int id_producto, int id_operacion, float cantidad, String nombreOperacion, String maquina) {
        super(id_subparte, id_producto);
        this.id_operacion = id_operacion;
        this.cantidad = cantidad;
        this.nombreOperacion = nombreOperacion;
        this.maquina = maquina;
    }

    public int getId_operacion() {
        return id_operacion;
    }

    public void setId_operacion(int id_operacion) {
        this.id_operacion = id_operacion;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }
}




