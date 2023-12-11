package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.khushi.AdaptadoresRecycler.AdapterOperaciones;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Usuario;
import com.example.khushi.clasesinfo.nuevaOperacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agregar_operacion extends AppCompatActivity {
    private boolean isMethodRunning = true;
    Usuario miUsuario = MainActivity.usuarioValidado;
    ArrayList<nuevaOperacion> listOperaciones;
    RecyclerView recycler;
    RequestQueue queue;
    EditText nombreOperacion, cantidad, precio;
    Spinner maquina;
    ImageButton agregaroperacion;
    private String idproducto,idsubparte;
    private Button botonModificar;

    private int idoperacionGlobal;
    private boolean visibilidadModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agregar_operacion);

        nombreOperacion= (EditText)findViewById(R.id.edtnombre_operacion_operacion);
        cantidad=(EditText)findViewById(R.id.edtcantidad_operaciones);
        maquina=(Spinner)findViewById(R.id.spinner_maquina_operaciones);
        precio=(EditText)findViewById(R.id.edtprecio_operaciones);
        agregaroperacion=(ImageButton)findViewById(R.id.imageButtonmas) ;
        recycler=(RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        botonModificar=(Button)findViewById(R.id.buttonmodificar_operacione);
        visibilidadModificar=false;



        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        queue = Volley.newRequestQueue(this);


        listOperaciones= new ArrayList<nuevaOperacion>();

        String[]opciones={"CYC","Plana","Ribeteadora","Dos agujas","otro","no aplica"};
        ArrayAdapter <String> adapterspinner= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,opciones);
        maquina.setAdapter(adapterspinner);

        Intent intent = getIntent();
        idproducto = intent.getStringExtra("id_producto");
        idsubparte = intent.getStringExtra("id_subparte");
        Toast.makeText(this, idproducto + " - "+idsubparte, Toast.LENGTH_SHORT).show();
        agregarListaOperacion("http://khushiconfecciones.com//app_khushi/buscar_operacion.php?id_producto="+idproducto+"&id_subparte="+idsubparte);

        agregaroperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevaOperacion("http://khushiconfecciones.com//app_khushi/agregar_operaciones.php");
                listOperaciones.clear(); // Limpiar la lista existente
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listOperaciones.clear(); // Limpiar la lista existente
                        agregarListaOperacion("http://khushiconfecciones.com//app_khushi/buscar_operacion.php?id_producto="+idproducto+"&id_subparte="+idsubparte);
                    }
                }, 3000); // 3000 milisegundos = 3 segundos
            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevaOperacion("http://khushiconfecciones.com//app_khushi/editar_operaciones.php");


                listOperaciones.clear(); // Limpiar la lista existente
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listOperaciones.clear(); // Limpiar la lista existente
                        agregarListaOperacion("http://khushiconfecciones.com//app_khushi/buscar_operacion.php?id_producto="+idproducto+"&id_subparte="+idsubparte);
                    }
                }, 3000); // 3000 milisegundos = 3 segundos
            }
        });

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

                        //int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));
                        int id_operacion = Integer.parseInt(jsonObject.getString("id_operaciones"));
                        float cantidad = Float.parseFloat(jsonObject.getString("cantidad"));
                        String nombre_operacion =jsonObject.getString("operaciones");
                        String maquina =jsonObject.getString("maquina");




                        // String data4 = (data1+ data2+data3);


                        listOperaciones.add(new nuevaOperacion(id_subparte,Integer.parseInt(idproducto),id_operacion,cantidad,nombre_operacion,maquina));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AdapterOperaciones adapter123 = new AdapterOperaciones(listOperaciones);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //acciones para el click
                        visibilidadModificar=true;
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
                        // saveToGlobalVariables(idOperacion, nombreOperacion);

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

    private void  agregarNuevaOperacion (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(agregar_operacion.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(agregar_operacion.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_producto", idproducto);
                parametros.put("id_subparte",idsubparte);
                parametros.put("operaciones",nombreOperacion.getText().toString());
                parametros.put("maquina", maquina.getSelectedItem().toString());
                parametros.put("cantidad", cantidad.getText().toString());

                if (visibilidadModificar=true){
                    parametros.put("id_operaciones",String.valueOf(idoperacionGlobal));
                }


                //para el id cambiar la base de datos a autoincrementable
                //falta arreglar el precio


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}