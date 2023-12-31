package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.AdaptadoresRecycler.Adapter_producto_oc;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoProducto;
import com.example.khushi.clasesinfo.nuevoproducto_en_oc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agregar_producto_oc extends AppCompatActivity {

    RecyclerView recycler;
    private int idproductoSeleccionado, idoc, idPtoductoOC;
    String ordenDeCompra, producto;
    private Spinner spinnerproducto;
    ArrayList<nuevoProducto> listDatos;

    ArrayList<nuevoproducto_en_oc>listProductoOrdenCompra;

    RequestQueue requestQueue;

    ArrayList<String[]> dataArrayList;

    private EditText cantidadLote, cantidadProductos;
    Button agregarProductoOc;
    RequestQueue queue;
    private boolean isMethodRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto_oc);

        spinnerproducto=(Spinner) findViewById(R.id.spinnerselecproducto);
        cantidadLote=(EditText)findViewById(R.id.editTextnumero_lotes);
        cantidadProductos=(EditText)findViewById(R.id.editTextnumero_productos);
        agregarProductoOc=(Button)findViewById(R.id.btnasignar_boton_a_oc);
        recycler = findViewById(R.id.recyclerproducto_ordedecompra);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));




        listDatos = new ArrayList<nuevoProducto>();
        listProductoOrdenCompra= new ArrayList<nuevoproducto_en_oc>();
        requestQueue = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(this);
        dataArrayList = new ArrayList<>();


        Intent intent=getIntent();
        idoc=Integer.parseInt(intent.getStringExtra("id_oc"));
        ordenDeCompra=intent.getStringExtra("orden_de_compra");
        spinnerproducto("http://khushiconfecciones.com/app_khushi/recycler.php"); // Volver a cargar la lista desde el servidor

        agregarlistaProductoOc("http://khushiconfecciones.com//app_khushi/buscar_operaciones_oc.php?id_oc="+idoc);

        agregarProductoOc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarproducto_oc("http://khushiconfecciones.com//app_khushi/agregar_producto_oc.php");
                listDatos.clear(); // Limpiar la lista existente
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatos.clear(); // Limpiar la lista existente
                        agregarlistaProductoOc("http://khushiconfecciones.com//app_khushi/buscar_operaciones_oc.php?id_oc="+idoc);
                    }
                }, 5000); // 3000 milisegundos = 3 segundos
            }
        });
        //agregarlistaProductoOc("dskjk");


    }


    private void spinnerproducto (String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listDatos.clear(); // Limpiar la lista existente

                ArrayList<String> nombresProductos = new ArrayList<>(); // ArrayList para almacenar nombres de productos
                ArrayList<Integer> idsProductos = new ArrayList<>();


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        String producto= jsonObject.getString("producto");
                        Float precio = Float.parseFloat(jsonObject.getString("precio"));
                        int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));

                        nombresProductos.add(producto);

                        listDatos.add(new nuevoProducto(producto,id_producto,precio));
                        // Agregar el ID del producto al ArrayList de IDs
                        idsProductos.add(id_producto);



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AdapterDatos adapter123 = new AdapterDatos(listDatos);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(agregar_producto_oc.this, android.R.layout.simple_list_item_1, nombresProductos);
                spinnerproducto.setAdapter(adapter); // Establecer el adaptador en el Spinner


                spinnerproducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonArrayRequest);



    }

    private void  agregarproducto_oc (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(agregar_producto_oc.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(agregar_producto_oc.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_oc", String.valueOf(idoc));
                parametros.put("id_producto",String.valueOf(idproductoSeleccionado));
                parametros.put("ordenCompra",ordenDeCompra);
                parametros.put("producto",producto);
                parametros.put("lotes",cantidadLote.getText().toString());
                parametros.put("cantidad_de_productos",cantidadProductos.getText().toString());

                /*
                if (visibilidadModificar=true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void agregarlistaProductoOc(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                //listProductoOrdenCompra.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);


                        int id =Integer.parseInt(jsonObject.getString("id"));
                        idPtoductoOC=id;

                        int id_oc =Integer.parseInt(jsonObject.getString("id_oc"));
                        int id_producto =Integer.parseInt(jsonObject.getString("id_producto"));
                        String ordenCompra=jsonObject.getString("ordenCompra");
                        String producto=jsonObject.getString("producto");
                        int lotes =Integer.parseInt(jsonObject.getString("lotes"));
                        int cantidad_de_productos =Integer.parseInt(jsonObject.getString("cantidad_de_productos"));

                        listProductoOrdenCompra.add(new nuevoproducto_en_oc(id_oc,ordenCompra,id, id_producto,
                                lotes, cantidad_de_productos, producto));




                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Adapter_producto_oc adapter123 = new Adapter_producto_oc(listProductoOrdenCompra);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(getApplicationContext(),"seleccion "+listDatos.get(recycler.getChildAdapterPosition(v)).getId_producto(),Toast.LENGTH_SHORT).show();
                       Intent intent= new Intent(agregar_producto_oc.this, operaciones_lotes.class);

                        intent.putExtra("id",String.valueOf(idPtoductoOC));
                        startActivity(intent);
                    }
                });
               /* adapter123.setOnItemLongClickListener(new AdapterDatos.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(nuevoProducto p) {
                        // evento para el onitemlongclick...

                    }
                });*/


                recycler.setAdapter(adapter123);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                isMethodRunning = true; // reiniciar la bandera en c
            }
        });

        queue.add(jsonArrayRequest);
    }




}