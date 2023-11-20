package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.khushi.clasesinfo.nuevoProducto;

import java.util.ArrayList;

public class agregar_operacion extends AppCompatActivity {
    ArrayList<nuevoProducto> listOperaciones;
    RecyclerView recycler;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_operacion);

    }
}