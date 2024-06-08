package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
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
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_filtrado2;
import com.example.khushi.Fragments.FragmentModificar;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.operacionesFiltradas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Agregar_operaciones_a_producto extends AppCompatActivity implements SearchView.OnQueryTextListener, FragmentModificar.OnButtonClickListener {

    Switch switch_aparecer;

    boolean visible; //variable para mostrar opciones de agregado
    private Toolbar toolbar1;

    private boolean visibilidadModificar;
    RecyclerView recycler, recycler_operaciones_de_producto;
    //Adapter_operaciones_filtrado adapter123;
    Adapter_operaciones_filtrado2 adapter123;
    Adapter_operaciones_filtrado2 adapter1234;


    Adapter_operaciones_filtrado2 adapter12345;




    SearchView buscarOperacionesDB;
    RequestQueue queue;
    private int idproducto, idsubparte;
    ArrayList<operacionesFiltradas> listOperaciones, listOperacionesDeProducto;
    ArrayList<operacionesFiltradas> listOperaciones2;
    EditText nombreOperacion, cantidadOperaciones, maquina, precio;
    Button agregarOperacion;
    private boolean switchActivado = false;
    private int idProductoAgregado,precioGlobal;

    LinearLayout contenedor_recycler;
    private String ROL, idEmpleado;// recibe el intent

    FragmentModificar fragmentmodificar;

    String guardaPrecio;

    //buscar producto en una oc
    private String id_productoOC,id_oc;
    private List<String[]> productoOCList;  // Lista para almacenar pares de valores








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_operaciones_aproducto);

        //fragmentmodificar=new FragmentModificar();
        //getSupportFragmentManager().beginTransaction().add(R.id.contenedor_fragments,fragmentmodificar).commit();



        //llenar el toolbar-------
        toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Khushi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el botón de retroceso



        mostarAgregar();

        productoOCList = new ArrayList<>();


        recycler = (RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        buscarOperacionesDB=(SearchView)findViewById(R.id.searchoperacionesDB);

        contenedor_recycler=(LinearLayout)findViewById(R.id.contenedor_recycler);
        contenedor_recycler.setVisibility(View.GONE);


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
        ROL = intent.getStringExtra("ROL");
        idEmpleado = intent.getStringExtra("idEmpleado");

        listOperaciones = new ArrayList<operacionesFiltradas>();
        listOperacionesDeProducto = new ArrayList<operacionesFiltradas>();
        listOperaciones2 = new ArrayList<operacionesFiltradas>();

      agregarListaOperacion_producto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_filtrado.php");
        listaDosOperacionProducto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_asignadas_a_producto.php?id_producto="
                + String.valueOf(idproducto) + "&id_subparte=" + String.valueOf(idsubparte));

        roles();


        buscarOperacionesDB.setOnQueryTextListener(this);

        switch_aparecer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    contenedor_recycler.setVisibility(View.VISIBLE);
                    switchActivado = true;
                } else {
                    contenedor_recycler.setVisibility(View.GONE);
                    switchActivado = false;

                }

            }
        });


        agregarOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaPrecio=precio.getText().toString();
                if (switchActivado == true) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listOperacionesDeProducto.clear();
                             // Limpiar la lista existente
                            listaDosOperacionProducto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_asignadas_a_producto.php?id_producto="
                                    + String.valueOf(idproducto) + "&id_subparte=" + String.valueOf(idsubparte));

                        }
                    }, 3000); // 3000 milisegundos = 3 segundos


                    agregarOperacionesRecursivamente(listOperaciones2, 0);
                    boolean insercionRealizada = false;

                } else {
                    //verificar si el producto se encuentra vinculado a una oc

                    agregarOperacion("http://khushiconfecciones.com//app_khushi/agregar_operaciones.php");
                    //listOperaciones.clear(); // Limpiar la lista existente

                    //espera 3 segundos y luego busca el id del ultimo registro
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            obtenerUltimaOperacion();
                            nombreOperacion.setText("");
                            cantidadOperaciones.setText("");
                            maquina.setText("");
                            precio.setText("");
                        }
                    }, 3000); // 6000 milisegundos = 6 segundos

                }



                //EditText nombreOperacion, cantidadOperaciones, maquina, precio;



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        agregarListaOperacion_producto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_filtrado.php");

                    }
                }, 6000); // 6000 milisegundos = 6 segundos




            }


        });
    }

    private void mostarAgregar() {

        TextView textView = findViewById(R.id.texto_agregarOperacion);

        // Texto que deseas mostrar
        String texto = "Agregar una operacion nueva   ";


        // Calcula el nuevo tamaño del icono (por ejemplo, 24dp x 24dp)
        int nuevoAncho = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, getResources().getDisplayMetrics());
        int nuevoAlto = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, getResources().getDisplayMetrics());


        // Obtén el icono de la carpeta drawable
        Drawable icono = getResources().getDrawable(R.drawable.bajar);

        // Tintar la imagen
        Drawable tintedIcon = DrawableCompat.wrap(icono);
        DrawableCompat.setTint(tintedIcon, Color.BLUE);

        // Establece el nuevo tamaño del icono
        icono.setBounds(0, 0, nuevoAncho, nuevoAlto);

        // Crea un SpannableString con el texto y el icono al final
        SpannableString spannableString = new SpannableString(texto + " ");
        ImageSpan imageSpan = new ImageSpan(icono, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(imageSpan, texto.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // Establece el SpannableString en el TextView
        textView.setText(spannableString);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible==false){
                    LinearLayout layourAgregar= (LinearLayout) findViewById(R.id.LYagregar);
                    layourAgregar.setVisibility(View.GONE);
                    visible=true;
                }else{
                    LinearLayout layourAgregar= (LinearLayout) findViewById(R.id.LYagregar);
                    layourAgregar.setVisibility(View.VISIBLE);
                    visible=false;
                }

            }
        });

    }

    private void roles() {
        if (ROL.equalsIgnoreCase("ADMIN")){
            LinearLayout layoutAdmin=findViewById(R.id.LayoutparaAdmin);
            layoutAdmin.setVisibility(View.VISIBLE);
        }
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
                listOperaciones2.clear(); // Limpiar la lista existente


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
                        int id_asignacion_operacion= Integer.parseInt(jsonObject.getString("id_asignacion_operacion"));


                        // String data4 = (data1+ data2+data3);


                        listOperaciones2.add(new operacionesFiltradas(id_producto, id_subparte, id_operaciones,
                                producto, subparte, operaciones, maquina, cantidad, precio));


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                 adapter12345 = new Adapter_operaciones_filtrado2(listOperaciones2);
                adapter12345.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });


                recycler.setAdapter(adapter12345);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Limpiar la lista existente
                        listaDosOperacionProducto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_asignadas_a_producto.php?id_producto="
                                + String.valueOf(idproducto) + "&id_subparte=" + String.valueOf(idsubparte));

                    }
                }, 1000); // 3000 milisegundos = 3 segundos
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
                        int id_precio = Integer.parseInt(jsonObject.getString("id_precio"));
                        int id_asignacion_operacion = Integer.parseInt(jsonObject.getString("id_asignacion_operacion"));


                        // String data4 = (data1+ data2+data3);



                        listOperacionesDeProducto.add(new operacionesFiltradas(id_producto, id_subparte, id_operaciones,
                                producto, subparte, operaciones, maquina, cantidad, precio, id_precio,id_asignacion_operacion));


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                //Adapter_operaciones_filtrado2 adapter123 = new Adapter_operaciones_filtrado2(listOperacionesDeProducto,ROL);
                Adapter_operaciones_filtrado2 adapter123 = new Adapter_operaciones_filtrado2(listOperacionesDeProducto);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Agregar_operaciones_a_producto.this, "click corto ", Toast.LENGTH_SHORT).show();

                    }
                });
                adapter123.setOnItemLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Toast.makeText(Agregar_operaciones_a_producto.this, "click largo", Toast.LENGTH_SHORT).show();
                        // Obtener la posición del item clickeado
                        int position = recycler_operaciones_de_producto.getChildAdapterPosition(v);

                        // Obtener el objeto operacionesFiltradas en esa posición
                        operacionesFiltradas item = listOperacionesDeProducto.get(position);
                        // Ahora puedes acceder a los elementos del objeto item y hacer lo que necesites con ellos

                        String producto = item.getProducto();
                        String subparte = item.getSubparte();
                        String operaciones = item.getOperaciones();
                        String maquina = item.getMaquina();
                        float cantidad = item.getCantidad();
                        float precio = item.getPrecio();
                        int id_precio = item.getId_precio();
                        int id_asignacion_operacion = item.getId_asignacion_operacion();



                        fragmentmodificar= FragmentModificar.newInstance(producto, subparte, operaciones, maquina, cantidad, precio, id_precio,id_asignacion_operacion);
                        getSupportFragmentManager().beginTransaction().add(R.id.contenedor_fragments,fragmentmodificar).commit();




                        return false;
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
                //parametros.put("precio", "123");


                if (!switchActivado==true){
                    parametros.put("precio", guardaPrecio);
                } else if (switchActivado == true) {
                    parametros.put("precio", String.valueOf(precioGlobal));
                }

                //ggg



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


    private void agregarOperacionesRecursivamente(final List<operacionesFiltradas> operaciones, final int index) {
        if (index >= operaciones.size()) {
            // Cuando se procesen todas las operaciones, detén la recursión
            return;


        }

        operacionesFiltradas operacion = operaciones.get(index);
        if (operacion.isChecked()) {

            idProductoAgregado = operacion.getIdOperaciones();
            precioGlobal = (int) operacion.getPrecio();
            agregarPrecio("http://khushiconfecciones.com//app_khushi/insertar_precio_operacion.php");
            agregarOperacion_Producto("http://khushiconfecciones.com//app_khushi/insert_operacion_a_producto.php");

            // Inserta la operación actual
            // ...



            // Espera un tiempo antes de continuar con la siguiente operación
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    agregarOperacionesRecursivamente(operaciones, index + 1); // Llama a la función recursivamente para la siguiente operación
                }
            }, 5000); // Tiempo de espera antes de la siguiente inserción (ajusta según necesites)
        } else {
            // Si la operación actual no está marcada, pasa a la siguiente
            agregarOperacionesRecursivamente(operaciones, index + 1);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter12345.filtrado(s);

        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuPrincipal) {
            Intent intent = new Intent(Agregar_operaciones_a_producto.this, Home.class);
            // Agregar las banderas FLAG_CLEAR_TOP y FLAG_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);            startActivity(intent);
            startActivity(intent);
            finish(); // Cierra la actividad actual
            return true;  // Importante agregar esta línea para indicar que el evento ha sido manejado

        }else if (id == R.id.fragmento2) {

            this.logout();

        }else if (id == R.id.fragmento3) {

            Toast.makeText(this, "no diponible", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == android.R.id.home) {
            // Maneja el clic en el botón de retroceso
            onBackPressed(); // Esto ejecutará el comportamiento predeterminado de retroceder
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void logout() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }


    @Override
    public void onButtonClicked() {
        agregarListaOperacion_producto("http://khushiconfecciones.com//app_khushi/buscar_operaciones_filtrado.php");
    }
    private void buscarProductoenOC (String URL){
        // Crear una solicitud JSON (JsonArrayRequest) con un método GET
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonObject=response.getJSONObject(i);
                        // Extraer datos del objeto JSON y mostrarlos en los campos de texto

                        id_productoOC=(jsonObject.getString("id"));
                        id_oc=(jsonObject.getString("id_oc"));
                        String[] productoOC = {id_productoOC, id_oc};  // Crear un array con los dos valores
                        productoOCList.add(productoOC);  // Agregar el array a la lista



                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();


            }
        }
        );
        queue=Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }
}
