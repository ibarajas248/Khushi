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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.AdaptadoresRecycler.AdapterSubParte;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaSubParte;
import com.example.khushi.clasesinfo.nuevoProducto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mostrar_agregar_subparte extends AppCompatActivity {

    private boolean visibilidadModificar= false;
    private String idproducto;
    Switch switch_aparecer;

    ArrayList<nuevaSubParte> listsubparte;
    ArrayList<nuevaSubParte> listSubparteSpinner;
    private EditText subparte;
    private Button registrarSubproducto, btnModificarSubparte, btnEliminarSeccion;
    RecyclerView recycler;
    RequestQueue queue;

    boolean validacion = false;
    private Handler handler = new Handler();
    private int id_subparte,id_subparte_para_comparar;
    Object lock = new Object();
    private Spinner spinnersubparte;
    private boolean switchActivado = false;

    private int idSubparteSeleccionada; //id cuando seleccion subpñarte del Spinner

    private String subparteSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_agregar_subparte);
        spinnersubparte=(Spinner) findViewById(R.id.spinner);

        subparte=(EditText)findViewById(R.id.mosagre_escribirsubpart);
        subparte.setEnabled(false);
        registrarSubproducto=(Button)findViewById(R.id.ma_subparte_agregar);
        btnModificarSubparte=(Button)findViewById(R.id.boton_modificar_subparte);
        btnEliminarSeccion=(Button)findViewById(R.id.btn_eliminar_subparte);
        switch_aparecer = (Switch) findViewById(R.id.switch2);


        recycler = findViewById(R.id.recyclerViewSubParte);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        Intent intent = getIntent();
        idproducto = intent.getStringExtra("id_producto");
        Toast.makeText(mostrar_agregar_subparte.this,idproducto,Toast.LENGTH_SHORT).show();
        listsubparte= new ArrayList<nuevaSubParte>();
        listSubparteSpinner= new ArrayList<nuevaSubParte>();
        queue = Volley.newRequestQueue(this);

        Spinnersubparte("http://khushiconfecciones.com/app_khushi/spinner_subparte.php"); // Volver a cargar la lista desde el servidor
        agregarlistaSubParte("http://khushiconfecciones.com//app_khushi/buscar_subparte.php?id_producto="+idproducto);

        switch_aparecer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Acción si el Switch está activado
                    switchActivado = true;
                    subparte.setEnabled(true);
                } else {
                    // Acción si el Switch está desactivado
                    switchActivado = false;
                    subparte.setEnabled(false);

                }

            }
        });

        btnModificarSubparte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            agregarSubparte("http://khushiconfecciones.com//app_khushi/editar_subparte.php");

            }
        });

        registrarSubproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchActivado==true) {
                    agregarSubparte("http://khushiconfecciones.com//app_khushi/agregar_subparte.php");
                    obtenerUltimaSubparte();


                    id_subparte_para_comparar = id_subparte;

                   /* while (id_subparte_para_comparar==id_subparte) {
                        obtenerUltimaSubparte();
                        }*/

                    listsubparte.clear(); // Limpiar la lista existente


                    //que pasaria si si el insert no se hace pero  obtenerUltimaSubparte(); si se ejecuta....arreglar esa excepcion...
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            obtenerUltimaSubparte();
                            Toast.makeText(mostrar_agregar_subparte.this, "id_subparte: " + id_subparte + "id_producto: " + idproducto, Toast.LENGTH_SHORT).show();
                            asociarProductoSubparte("http://khushiconfecciones.com//app_khushi/asociar_producto_subparte.php");
                            listsubparte.clear();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    agregarlistaSubParte("http://khushiconfecciones.com//app_khushi/buscar_subparte.php?id_producto=" + idproducto);

                                }
                            }, 3000); // 3000 milisegundos = 3 segundos

                        }
                    }, 3000); // 3000 milisegundos = 3 segundos


                }else{
                    asociarProductoSubparte("http://khushiconfecciones.com//app_khushi/asociar_producto_subparte.php");
                    listsubparte.clear(); // Limpiar la lista existente
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            agregarlistaSubParte("http://khushiconfecciones.com//app_khushi/buscar_subparte.php?id_producto=" + idproducto);

                        }
                    }, 3000); // 3000 milisegundos = 3 segundos

                }

            }//termina el onclick
        });//termina el setonclick

        btnEliminarSeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eliminarSeccion("http://khushiconfecciones.com//app_khushi/eliminar_subparte.php");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        agregarlistaSubParte("http://khushiconfecciones.com//app_khushi/buscar_subparte.php?id_producto="+idproducto);

                    }
                }, 6000); // 6000 milisegundos = 6 segundos

            }
        });


    }

    private void agregarlistaSubParte(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listsubparte.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        String subparte =(jsonObject.getString("subparte"));

                        int id_subparte = Integer.parseInt(jsonObject.getString("id_subparte"));

                        int id_producto = Integer.parseInt(jsonObject.getString("id_producto"));


                        // String data4 = (data1+ data2+data3);

                        listsubparte.add(new nuevaSubParte(id_subparte,id_producto,subparte));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AdapterSubParte adapter123 = new AdapterSubParte(listsubparte);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       //acciones para el click

                        Intent intent= new Intent(mostrar_agregar_subparte.this, Agregar_operaciones_a_producto.class);
                        intent.putExtra("id_producto",idproducto);
                        intent.putExtra("id_subparte",String.valueOf(listsubparte.get(recycler.getChildAdapterPosition(v)).getId_subparte()));
                        startActivity(intent);
                        finish();
                    }
                });

                adapter123.setOnItemLongClickListener(new AdapterSubParte.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(nuevaSubParte s) {

                        subparte.setText(s.getSubparte());
                        idproducto=String.valueOf(s.getId_producto());
                        id_subparte=s.getId_subparte();

                        btnModificarSubparte.setVisibility(View.VISIBLE);
                        visibilidadModificar=true;
                        registrarSubproducto.setVisibility(View.GONE);
                        btnEliminarSeccion.setVisibility(View.VISIBLE);

                        Toast.makeText(mostrar_agregar_subparte.this, subparte.getText().toString()+" "+String.valueOf(id_subparte)+" "+idproducto, Toast.LENGTH_SHORT).show();


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

    private void  agregarSubparte (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(mostrar_agregar_subparte.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(mostrar_agregar_subparte.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> parametros= new HashMap<String, String>();
                //parametros.put("id", idproducto);

                parametros.put("subparte", subparte.getText().toString());


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private int generarid(){
        int idSubparte = (int)(Math.random()*500+1);

        buscaridvalidacion("http://khushiconfecciones.com//app_khushi/validar_subparte.php?subparte="+String.valueOf(idSubparte));
        //si el codigo ya se encuentra en la base de datos
        // entonces vuelve a llamar el metodo

        if (validacion==true){
            return generarid();
        }
        return idSubparte;
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
    private void obtenerUltimaSubparte() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://khushiconfecciones.com//app_khushi/ultimo_id_subparte.php"; // Reemplaza con tu URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Busca el último registro y asigna el id
                            id_subparte = Integer.parseInt(response.getString("id"));
                            //Toast.makeText(mostrar_agregar_subparte.this, "hola"+id_subparte, Toast.LENGTH_SHORT).show();


                            //asociarProductoSubparte("http://khushiconfecciones.com//app_khushi/asociar_producto_subparte.php");


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
    private void  asociarProductoSubparte (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(mostrar_agregar_subparte.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> parametros= new HashMap<String, String>();


                if (switchActivado==true){
                    parametros.put("subparte", subparte.getText().toString());
                    parametros.put("id_producto", idproducto);
                    parametros.put("id_subparte", String.valueOf(id_subparte));
                }else{
                    parametros.put("subparte", subparteSeleccionada);
                    parametros.put("id_producto", idproducto);
                    parametros.put("id_subparte", String.valueOf(idSubparteSeleccionada));
                }

                /*
                if (visibilidadModificar=true){
                    parametros.put("id_subparte",String.valueOf(id_subparte));
                }else{
                    parametros.put("id_subparte",String.valueOf(generarid()));
                }*/








                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void Spinnersubparte (String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listsubparte.clear(); // Limpiar la lista existente
                listsubparte.clear(); // Limpiar la lista existente

                ArrayList<String> nombresSubpartes = new ArrayList<>(); // ArrayList para almacenar nombres de productos
                ArrayList<Integer> idSubpartes = new ArrayList<>();//Arraylist para almacenar nombres de subpartes


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        String subparte= jsonObject.getString("subparte");
                        int id = Integer.parseInt(jsonObject.getString("id"));

                        nombresSubpartes.add(subparte);

                        listSubparteSpinner.add(new nuevaSubParte(id,subparte));
                        // Agregar el ID del producto al ArrayList de IDs
                        idSubpartes.add(id);



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
               //AdapterDatos adapter123 = new AdapterDatos(listSubparteSpinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(mostrar_agregar_subparte.this, android.R.layout.simple_list_item_1, nombresSubpartes);
                spinnersubparte.setAdapter(adapter); // Establecer el adaptador en el Spinner


                spinnersubparte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Obtener el ID seleccionado usando la posición en el ArrayList de IDs
                        idSubparteSeleccionada = idSubpartes.get(position);
                        subparteSeleccionada=nombresSubpartes.get(position);
                        registrarSubproducto.setVisibility(View.VISIBLE);

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

        queue.add(jsonArrayRequest);



    }

    public void onBackPressed() {
        // No hacer nada (bloquear el botón de retroceso)

    }

    private void eliminarSeccion (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena



            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                Toast.makeText(mostrar_agregar_subparte.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_producto", String.valueOf(idproducto));
                parametros.put("id_subparte", String.valueOf(id_subparte));


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }




}