package com.example.khushi.Activity;

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
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_filtrado;
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_lotes;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoProducto;
import com.example.khushi.clasesinfo.nuevoproducto_en_oc;
import com.example.khushi.clasesinfo.operacionesFiltradas;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class operaciones_lotes extends AppCompatActivity {

    ArrayList<operaciones_lotes_clase> listOperaciones;
    RecyclerView recycler;
    RequestQueue queue;
    Adapter_operaciones_lotes adapter123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_lotes);
        queue = Volley.newRequestQueue(this);
        listOperaciones=new ArrayList<operaciones_lotes_clase>();

        Intent intent = getIntent();

        String variableRecibida_idproducto_oc = null;
        if (intent != null) {
            variableRecibida_idproducto_oc = intent.getStringExtra("id");
            // Utiliza la variable recibida como necesites
        }


        recycler = (RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));





       agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra=" + variableRecibida_idproducto_oc);

    }

    private void agregarListaOperacion_Lote(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listOperaciones.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);

                        String producto = jsonObject.getString("producto");
                        String subparte = jsonObject.getString("subparte");
                        String operaciones = jsonObject.getString("operaciones");
                        int id_lotes_operaciones= Integer.parseInt(jsonObject.getString("id_lotes_operaciones"));
                        int cantidad= Integer.parseInt(jsonObject.getString("cantidad"));
                        String StringEmpleado=jsonObject.getString("empleado");

                        int empleado;


                        // si el valor es null pone -1

                        if (StringEmpleado.isEmpty()) {
                            // Si la cadena está vacía, asigna un valor predeterminado (por ejemplo, -1)
                            empleado = -1; // o cualquier otro valor predeterminado que desees
                        } else {
                            // Intenta convertir la cadena a un entero
                            try {
                                empleado = Integer.parseInt(StringEmpleado);
                            } catch (NumberFormatException e) {
                                // Si hay un error al convertir, maneja la excepción aquí
                                // Por ejemplo, asigna un valor predeterminado o muestra un mensaje de error
                                empleado = -1; // Valor predeterminado en caso de error
                                e.printStackTrace(); // Imprime la traza de error para ver qué ocurrió
                            }
                        }
                        int id_operaciones_subparte_producto=Integer.parseInt(jsonObject.getString("id_operaciones_subparte_producto"));

                        listOperaciones.add(new operaciones_lotes_clase(producto,subparte,operaciones,id_lotes_operaciones,cantidad,empleado,id_operaciones_subparte_producto));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter123 = new Adapter_operaciones_lotes(listOperaciones);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //acciones para el click
                        /*visibilidadModificar=true;
                        botonModificar.setVisibility(View.VISIBLE);

                        int position = recycler.getChildAdapterPosition(v);
                        // Obtén el elemento seleccionado del ArrayList usando la posición
                        nuevaOperacion operacionSeleccionada = listOperaciones.get(position);

                        // Ahora puedes acceder a los valores de la operación seleccionada y guardarlos o realizar acciones con ellos

                        //nombreOperacion = operacionSeleccionada.getNombreOperacion();
                        nombreOperacion.setText(String.valueOf(operacionSeleccionada.getNombreOperacion()));
                        cantidad.setText(String.valueOf(operacionSeleccionada.getCantidad()));
                        idoperacionGlobal=operacionSeleccionada.getId_operacion();


                        // Y así sucesivamente con otros valores que desees guardar o utilizar

                        // Ejemplo: Guardar los valores en variables globales o hacer otra acción
                        // saveToGlobalVariables(idOperacion, nombreOperacion);*/

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


