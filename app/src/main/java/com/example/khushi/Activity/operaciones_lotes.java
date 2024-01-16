package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_filtrado;
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_lotes;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
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
    ArrayList<String>listEmpleados;
    ArrayList<Empleado_clase> listaEmpleados;

    ArrayList<String> nombres;

    ArrayList<Integer> idsEmpleados;

    RecyclerView recycler;
    RequestQueue queue;

    ArrayAdapter<String> adapter;// adapter que va a tomar el spinner
    Adapter_operaciones_lotes adapter123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_lotes);
        queue = Volley.newRequestQueue(this);
        listOperaciones=new ArrayList<operaciones_lotes_clase>();


        listaEmpleados = new ArrayList<>();
        nombres = new ArrayList<>();
        idsEmpleados = new ArrayList<>();
        listEmpleados = new ArrayList<>();

        Intent intent = getIntent();

        String variableRecibida_idproducto_oc = null;
        if (intent != null) {
            variableRecibida_idproducto_oc = intent.getStringExtra("id");
            // Utiliza la variable recibida como necesites
        }


        recycler = (RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //metodo para llenar las listas del spinner...


        nombres.add("seleccione un Operario");
        idsEmpleados.add(0);
        llenarListaSpinner("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_empleados_spinner.php");


// En tu método donde deseas llamar a los métodos después de un retraso de 5 segundos
        Handler handler = new Handler();
        String finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Llama al primer método aquí
                agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra=" + finalVariableRecibida_idproducto_oc);

            }
        }, 5000); // Retraso de 5000 milisegundos (5 segundos)







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

                        String empleado=StringEmpleado;


                        // si el valor es null pone -1

                        if (StringEmpleado.isEmpty()) {
                            // Si la cadena está vacía, asigna un valor predeterminado (por ejemplo, -1)
                            empleado = "no asignado"; // o cualquier otro valor predeterminado que desees
                        } else {
                            // Intenta convertir la cadena a un entero
                            try {
                                empleado=String.valueOf(empleado);
                            } catch (NumberFormatException e) {
                                // Si hay un error al convertir, maneja la excepción aquí
                                // Por ejemplo, asigna un valor predeterminado o muestra un mensaje de error
                                empleado = "error"; // Valor predeterminado en caso de error
                                e.printStackTrace(); // Imprime la traza de error para ver qué ocurrió
                            }
                        }
                        int id_operaciones_subparte_producto=Integer.parseInt(jsonObject.getString("id_operaciones_subparte_producto"));

                        listOperaciones.add(new operaciones_lotes_clase(producto,subparte,operaciones,id_lotes_operaciones,cantidad,empleado,id_operaciones_subparte_producto));

                        //hacer que el Spinner se llene con una consulta sql a la tabla de empleados...llamar un metedo
                        listEmpleados.add(String.valueOf(empleado));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter123 = new Adapter_operaciones_lotes(listOperaciones,nombres,idsEmpleados,listaEmpleados);
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




    private void llenarListaSpinner (String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listaEmpleados.clear(); // Limpiar la lista existente

                //nombres = new ArrayList<>(); // ArrayList para almacenar nombres de productos
                //idsEmpleados = new ArrayList<>();


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        int id= Integer.parseInt(jsonObject.getString("id"));
                        String nombre= jsonObject.getString("nombre");
                        String apellidos=jsonObject.getString("Apellidos");

                        nombres.add(nombre +" "+ apellidos);
                        idsEmpleados.add(id);
                        listaEmpleados.add(new Empleado_clase(String.valueOf(id),nombre,apellidos));





                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                //AdapterDatos adapter123 = new AdapterDatos(listEmpleados);

                adapter = new ArrayAdapter<>(operaciones_lotes.this, android.R.layout.simple_list_item_1, nombres);


                //spinnerproducto.setAdapter(adapter); // Establecer el adaptador en el Spinner


               /* spinnerproducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Obtener el ID seleccionado usando la posición en el ArrayList de IDs
                        idproductoSeleccionado = idsProductos.get(position);
                        producto=nombresProductos.get(position);
                        Toast.makeText(agregar_producto_oc.this,producto, Toast.LENGTH_SHORT).show();
                        // Guardar el ID en una variable o realizar alguna acción con él
                        // Ejemplo: guardar el ID en una variable global
                        // idSeleccionadoGlobal = idSeleccionado;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Método requerido pero no se utiliza en este caso
                    }
                });*/

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


