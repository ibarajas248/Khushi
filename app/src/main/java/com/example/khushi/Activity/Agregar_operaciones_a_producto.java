package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.AdapterOperaciones;
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_filtrado;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaOperacion;
import com.example.khushi.clasesinfo.operacionesFiltradas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Agregar_operaciones_a_producto extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Switch switch_aparecer;
    private boolean visibilidadModificar;
    RecyclerView recycler, recycler_operaciones_de_producto;
    Adapter_operaciones_filtrado adapter123;

    SearchView buscarOperacionesDB;
    RequestQueue queue;
    private int idproducto, idsubparte;
    ArrayList<operacionesFiltradas> listOperaciones, listOperacionesDeProducto;
    EditText nombreOperacion, cantidadOperaciones, maquina, precio;
    Button agregarOperacion;
    private boolean switchActivado = false;
    private int idProductoAgregado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_operaciones_aproducto);
        recycler = (RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        buscarOperacionesDB=(SearchView)findViewById(R.id.searchoperacionesDB);

        recycler_operaciones_de_producto = (RecyclerView) findViewById(R.id.recycleroperacionesproducto);
        recycler_operaciones_de_producto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        queue = Volley.newRequestQueue(this);
        nombreOperacion = (EditText) findViewById(R.id.edtnombre_operacion_operacion);
        cantidadOperaciones = (EditText) findViewById(R.id.edtcantidad_operaciones);
        maquina = (EditText) findViewById(R.id.edtmaquina);
        agregarOperacion = (Button) findViewById(R.id.boton_agregar_operacion);
        precio = (EditText) findViewById(R.id.edtprecio_operaciones);
        switch_aparecer = (Switch) findViewById(R.id.switch1);


        Intent intent = getIntent();
        idproducto = Integer.parseInt(intent.getStringExtra("id_producto"));
        idsubparte = Integer.parseInt(intent.getStringExtra("id_subparte"));

        listOperaciones = new ArrayList<operacionesFiltradas>();
        listOperacionesDeProducto = new ArrayList<operacionesFiltradas>();

      agregarListaOperacion_producto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_filtrado.php");
        listaDosOperacionProducto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_asignadas_a_producto.php?id_producto="
                + String.valueOf(idproducto) + "&id_subparte=" + String.valueOf(idsubparte));


        buscarOperacionesDB.setOnQueryTextListener(this);

        switch_aparecer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Acción si el Switch está activado
                    switchActivado = true;
                } else {
                    // Acción si el Switch está desactivado
                    switchActivado = false;

                }

            }
        });


        agregarOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchActivado == true) {
                    boolean insercionRealizada = false;

                    for (operacionesFiltradas operacion : listOperaciones) {
                        if (operacion.isChecked()) {
                            // Inserta el elemento en el RecyclerView de abajo
                            // Asumiendo que tienes un método para agregar elementos a la lista en Adapter_operaciones_filtrado
                            // Insertar en la lista del RecyclerView de abajo


                            agregarOperacionDeDB("http://khushiconfecciones.com//app_khushi/agregar_operaciones.php", operacion);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    obtenerUltimaOperacion();
                                    Toast.makeText(Agregar_operaciones_a_producto.this, String.valueOf(idProductoAgregado) +
                                            "hla", Toast.LENGTH_SHORT).show();
                                }
                            }, 6000); // 6000 milisegundos = 6 segundos



                        }
                    }

                    //aaa
                } else {
                    agregarOperacion("http://khushiconfecciones.com//app_khushi/agregar_operaciones.php");
                    //listOperaciones.clear(); // Limpiar la lista existente

                    //espera 3 segundos y luego busca el id del ultimo registro
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            obtenerUltimaOperacion();
                            Toast.makeText(Agregar_operaciones_a_producto.this, String.valueOf(idProductoAgregado) +
                                    "hla", Toast.LENGTH_SHORT).show();
                        }
                    }, 6000); // 6000 milisegundos = 6 segundos

                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        agregarListaOperacion_producto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_filtrado.php");

                    }
                }, 6000); // 6000 milisegundos = 6 segundos


            }


        });
    }

    private void realizarInserciones(final Queue<operacionesFiltradas> insercionesPendientes) {
        if (!insercionesPendientes.isEmpty()) {
            operacionesFiltradas operacion = insercionesPendientes.poll();

            // Insertar operación en la base de datos
            agregarOperacionDeDB("http://khushiconfecciones.com//app_khushi/agregar_operaciones.php", operacion);

            // Esperar a que la inserción se complete antes de realizar la siguiente
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    realizarInserciones(insercionesPendientes); // Llamada recursiva para la siguiente inserción
                }
            }, 6000); // Espera 6 segundos entre inserciones
        } else {
            // Todas las inserciones han sido completadas
            obtenerUltimaOperacion();
            Toast.makeText(Agregar_operaciones_a_producto.this, "Todas las inserciones han sido completadas", Toast.LENGTH_SHORT).show();
        }
    }




    private void agregarListaOperacion_producto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listOperaciones.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);

                        int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));
                        String producto = jsonObject.getString("producto");
                        int id_subparte = Integer.parseInt(jsonObject.getString("id_subparte"));
                        String subparte = jsonObject.getString("subparte");
                        int id_operaciones = Integer.parseInt(jsonObject.getString("id_operaciones"));
                        String operaciones = jsonObject.getString("operaciones");
                        float cantidad = Float.parseFloat(jsonObject.getString("cantidad"));
                        String maquina = jsonObject.getString("maquina");
                        float precio = Float.parseFloat(jsonObject.getString("precio"));


                        // String data4 = (data1+ data2+data3);


                        listOperaciones.add(new operacionesFiltradas(id_producto, id_subparte, id_operaciones,
                                producto, subparte, operaciones, maquina, cantidad, precio));


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                 adapter123 = new Adapter_operaciones_filtrado(listOperaciones);
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

    private void listaDosOperacionProducto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listOperacionesDeProducto.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);

                        int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));
                        String producto = jsonObject.getString("producto");
                        int id_subparte = Integer.parseInt(jsonObject.getString("id_subparte"));
                        String subparte = jsonObject.getString("subparte");
                        int id_operaciones = Integer.parseInt(jsonObject.getString("id_operaciones"));
                        String operaciones = jsonObject.getString("operaciones");
                        float cantidad = Float.parseFloat(jsonObject.getString("cantidad"));
                        String maquina = jsonObject.getString("maquina");
                        float precio = Float.parseFloat(jsonObject.getString("precio"));


                        // String data4 = (data1+ data2+data3);


                        listOperacionesDeProducto.add(new operacionesFiltradas(id_producto, id_subparte, id_operaciones,
                                producto, subparte, operaciones, maquina, cantidad, precio));


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Adapter_operaciones_filtrado adapter123 = new Adapter_operaciones_filtrado(listOperacionesDeProducto);
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


                recycler_operaciones_de_producto.setAdapter(adapter123);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonArrayRequest);
    }

    private void agregarOperacion(String URL) {
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(Agregar_operaciones_a_producto.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(Agregar_operaciones_a_producto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"


                Map<String, String> parametros = new HashMap<String, String>();
                // parametros.put("id_producto", String.valueOf(idproducto));
                //parametros.put("id_subparte",String.valueOf(idsubparte));
                parametros.put("operaciones", nombreOperacion.getText().toString());
                parametros.put("cantidad", cantidadOperaciones.getText().toString());
                parametros.put("maquina", maquina.getText().toString());

                /*if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void agregarPrecio(String URL) {
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(Agregar_operaciones_a_producto.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(Agregar_operaciones_a_producto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"


                Map<String, String> parametros = new HashMap<String, String>();
                // parametros.put("id_producto", String.valueOf(idproducto));
                //parametros.put("id_subparte",String.valueOf(idsubparte));
                parametros.put("id_operacion", String.valueOf(idProductoAgregado));
                parametros.put("id_subparte", String.valueOf(idsubparte));
                parametros.put("id_producto", String.valueOf(idproducto));
                parametros.put("precio", precio.getText().toString());



                /*if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void agregarOperacion_Producto(String URL) {
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(Agregar_operaciones_a_producto.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(Agregar_operaciones_a_producto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"

                Map<String, String> parametros = new HashMap<String, String>();
                // parametros.put("id_producto", String.valueOf(idproducto));
                //parametros.put("id_subparte",String.valueOf(idsubparte));
                parametros.put("id_operaciones", String.valueOf(idProductoAgregado));
                parametros.put("id_subparte", String.valueOf(idsubparte));
                parametros.put("id_producto", String.valueOf(idproducto));

                /*if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void agregarOperacionDeDB(String URL, operacionesFiltradas operacion) {

        operacionesFiltradas operacion1 = operacion;
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(Agregar_operaciones_a_producto.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(Agregar_operaciones_a_producto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"


                Map<String, String> parametros = new HashMap<String, String>();
                //parametros.put("id_producto", String.valueOf(idproducto));
                //parametros.put("id_subparte",String.valueOf(idsubparte));
                parametros.put("operaciones", operacion1.getOperaciones());
                parametros.put("cantidad", String.valueOf(operacion1.getCantidad()));
                parametros.put("maquina", operacion1.getMaquina());

                /*if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void agregarPrecioSwitchOn(String URL, operacionesFiltradas operacion) {

        operacionesFiltradas operacion1 = operacion;
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(Agregar_operaciones_a_producto.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(Agregar_operaciones_a_producto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"


                Map<String, String> parametros = new HashMap<String, String>();
                // parametros.put("id_producto", String.valueOf(idproducto));
                //parametros.put("id_subparte",String.valueOf(idsubparte));
                parametros.put("id_operacion", String.valueOf(operacion1.getIdOperaciones()));
                parametros.put("id_subparte", String.valueOf(operacion1.getIdSubparte()));
                parametros.put("id_producto", String.valueOf(operacion1.getIdProducto()));
                parametros.put("precio", String.valueOf(operacion1.getPrecio()));

                /*if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void obtenerUltimaOperacion() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://khushiconfecciones.com//app_khushi/buscar_ultima_operacion.php"; // Reemplaza con tu URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Busca el último registro y asigna el id
                            idProductoAgregado = Integer.parseInt(response.getString("id_operaciones"));
                            Toast.makeText(Agregar_operaciones_a_producto.this, response.getString("id_operaciones"), Toast.LENGTH_SHORT).show();

                            if (switchActivado == false) {
                                agregarPrecio("http://khushiconfecciones.com//app_khushi/insertar_precio_operacion.php");
                                agregarOperacion_Producto("http://khushiconfecciones.com//app_khushi/insert_operacion_a_producto.php");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                    }
                });

        queue.add(jsonObjectRequest);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
