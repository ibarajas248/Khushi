package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.AdaptadoresRecycler.AdapterOperaciones;
import com.example.khushi.AdaptadoresRecycler.AdapterSubParte;
import com.example.khushi.clasesinfo.nuevaOperacion;
import com.example.khushi.clasesinfo.nuevaSubParte;
import com.example.khushi.clasesinfo.nuevoProducto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class agregar_operacion extends AppCompatActivity {
    private boolean isMethodRunning = true;
    ArrayList<nuevaOperacion> listOperaciones;
    RecyclerView recycler;
    RequestQueue queue;
    private String idproducto,idsubparte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agregar_operacion);
        recycler=(RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        queue = Volley.newRequestQueue(this);

        listOperaciones= new ArrayList<nuevaOperacion>();

        Intent intent = getIntent();
        idproducto = intent.getStringExtra("id_producto");
        idsubparte = intent.getStringExtra("id_Subparte");
        agregarListaOperacion("http://khushiconfecciones.com//app_khushi/buscar_operacion.php?id_producto="+idproducto+"&id_subparte="+idsubparte);

    }

    private void agregarListaOperacion(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listOperaciones.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);

                        int id_subparte = Integer.parseInt(jsonObject.getString("id_subparte"));

                        int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));
                        int id_operacion = Integer.parseInt(jsonObject.getString("id_operaciones"));
                        float cantidad = Float.parseFloat(jsonObject.getString("cantidad"));
                        String nombre_operacion =jsonObject.getString("operaciones");
                        String maquina =jsonObject.getString("maquina");


                        // String data4 = (data1+ data2+data3);


                        listOperaciones.add(new nuevaOperacion(id_subparte,id_producto,id_operacion,cantidad,nombre_operacion,maquina));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AdapterOperaciones adapter123 = new AdapterOperaciones(listOperaciones);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //acciones para el click
                    }
                });

                recycler.setAdapter(adapter123);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonArrayRequest);
    }
}