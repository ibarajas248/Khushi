package com.example.khushi.clasesinfo;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.agregarProducto;
import com.example.khushi.mostrar_agregar_subparte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class nuevoProducto {
    private String producto;
    private int id_producto;
    private float precio;

    public nuevoProducto(String producto, int id_producto, float precio) {
        this.producto = producto;
        this.id_producto = id_producto;
        this.precio = precio;
    }

    public nuevoProducto(int idProducto) {
        this.id_producto = id_producto;
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
