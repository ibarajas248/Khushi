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
import android.widget.EditText;
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
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoProducto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agregarProducto extends AppCompatActivity {

    private boolean isMethodRunning = true;

    private boolean visibilidadModificar= false;
    ArrayList<nuevoProducto> listDatos;
    RecyclerView recycler;
    RequestQueue queue;
    EditText producto;
    Button registrarProducto, modificarProducto, eliminarProducto;
    EditText precio;
    private Handler handler = new Handler();
    private Runnable runnable;

    boolean validacion = false;
    int idProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        recycler = findViewById(R.id.recyclerViewProductos);
        producto=(EditText)findViewById(R.id.editTextTextagregarproducto);
        registrarProducto=(Button)findViewById(R.id.registrarproductos);
        modificarProducto=(Button)findViewById(R.id.boton_modificarproducto);
        eliminarProducto=(Button)findViewById(R.id.button_eliminar_producto);

        precio=(EditText)findViewById(R.id.editTextprecio);

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listDatos = new ArrayList<nuevoProducto>();
        queue = Volley.newRequestQueue(this);
        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php");

        modificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarproducto("http://khushiconfecciones.com//app_khushi/editar_producto.php");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatos.clear();
                        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php");
                        recycler.setVisibility(View.VISIBLE);

                    }
                }, 3000); // 3000 milisegundos = 3 segundos


            }
        });

        registrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarproducto("http://khushiconfecciones.com//app_khushi/insertar_producto.php");
                listDatos.clear(); // Limpiar la lista existente
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatos.clear(); // Limpiar la lista existente
                        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php"); // Volver a cargar la lista desde el servidor
                    }
                }, 3000); // 3000 milisegundos = 3 segundos
            }
        });
        eliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto("http://khushiconfecciones.com//app_khushi/eliminar_producto.php");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatos.clear();
                        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php");
                        recycler.setVisibility(View.VISIBLE);

                    }
                }, 3000); // 3000 milisegundos = 3 segundos
            }
        });
    }
    private void agregarlista(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listDatos.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        String producto =(jsonObject.getString("producto"));

                        Float precio = Float.parseFloat(jsonObject.getString("precio"));

                        int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));

                       // String data4 = (data1+ data2+data3);

                        listDatos.add(new nuevoProducto(producto,id_producto,precio));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AdapterDatos adapter123 = new AdapterDatos(listDatos);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(getApplicationContext(),"seleccion "+listDatos.get(recycler.getChildAdapterPosition(v)).getId_producto(),Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(agregarProducto.this, mostrar_agregar_subparte.class);

                            intent.putExtra("id_producto",String.valueOf(listDatos.get(recycler.getChildAdapterPosition(v)).getId_producto()));
                            startActivity(intent);
                        }
                });
                adapter123.setOnItemLongClickListener(new AdapterDatos.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(nuevoProducto p) {

                        precio.setText(String.valueOf(p.getPrecio()));
                        producto.setText(p.getProducto());
                        idProducto=p.getId_producto();
                        modificarProducto.setVisibility(View.VISIBLE);
                        eliminarProducto.setVisibility(View.VISIBLE);
                        visibilidadModificar=true;
                        recycler.setVisibility(View.GONE);
                        registrarProducto.setVisibility(View.GONE);

                    }
                });


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

    private void  agregarproducto (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(agregarProducto.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(agregarProducto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();
                //parametros.put("id_producto", String.valueOf(generarid()));
                parametros.put("producto",producto.getText().toString());
                parametros.put("precio",precio.getText().toString());
                if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private int generarid(){
        int idproducto = (int)(Math.random()*500+1);
        buscaridvalidacion("http://khushiconfecciones.com//app_khushi/validar_producto.php"+String.valueOf(idproducto));
        //si el codigo ya se encuentra en la base de datos
        // entonces vuelve a llamar el metodo

        if (validacion==true){
            return generarid();
        }
        return idproducto;
    }
    private void buscaridvalidacion (String URL){

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonObject=response.getJSONObject(i);
                        validacion=true;





                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        //Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();


            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void eliminarProducto (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                limpiarFormulario();

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                Toast.makeText(agregarProducto.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_producto", String.valueOf(idProducto));


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    private void limpiarFormulario() {
        producto.setText("");
        precio.setText("");
    }


    public void onBackPressed() {
        super.onBackPressed();
        finish(); // Finaliza la actividad actual
    }



}