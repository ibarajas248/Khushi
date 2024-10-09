package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.Adapter_empleados;
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_lotes_con_card;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class operaciones_lotes_cardview extends AppCompatActivity {
    private RecyclerView recyclerViewOperaciones;
    private Adapter_operaciones_lotes_con_card adapter_operaciones;
    private ArrayList<operaciones_lotes_clase> listaOperaciones;
    private RequestQueue requestQueue;
    public String finalVariableRecibida_idproducto_oc;
    String id_producto;
    String ROL,idEmpleado; //recupera de la activity anterior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_lotes_cardview);
        Intent intent = getIntent();
        String variableRecibida_idproducto_oc = null;
        if (intent != null) {
            variableRecibida_idproducto_oc = intent.getStringExtra("id");
            finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
            id_producto= (intent.getStringExtra("id_producto"));
            ROL=(intent.getStringExtra("ROL"));
            idEmpleado=(intent.getStringExtra("idEmpleado"));

        }

        // Inicializamos el RecyclerView y el Adapter
        recyclerViewOperaciones = findViewById(R.id.recycler_lotes_operaciones2);
        recyclerViewOperaciones.setLayoutManager(new LinearLayoutManager(this));

        // Lista de ejemplo para las operaciones
        listaOperaciones = new ArrayList<>();
        listaOperaciones.add(new operaciones_lotes_clase("Operación 1"));
        listaOperaciones.add(new operaciones_lotes_clase("Operación 2"));
        listaOperaciones.add(new operaciones_lotes_clase("Operación 3"));

        // Configuramos el Adapter
        adapter_operaciones = new Adapter_operaciones_lotes_con_card(listaOperaciones);
        recyclerViewOperaciones.setAdapter(adapter_operaciones);


    }





}