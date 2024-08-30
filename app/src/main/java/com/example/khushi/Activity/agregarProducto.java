package com.example.khushi.Activity;

import static android.content.Intent.getIntent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoProducto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agregarProducto extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private boolean isMethodRunning = true;
    private boolean desplegaropcion= false;
    AdapterDatos adapter123;
    private boolean visibilidadModificar= false;
    LinearLayout LayoutIngresaProducto;
    ArrayList<nuevoProducto> listDatos;
    RecyclerView recycler;
    RequestQueue queue;
    ImageButton desplegar;
    private Toolbar toolbar1;
    EditText producto;
    Button registrarProducto, modificarProducto, eliminarProducto;
    EditText precio;
    private Handler handler = new Handler();
    private Runnable runnable;

    boolean validacion = false;
    int idProducto;

    private String ROL, idEmpleado;// recibe el intent
    SearchView searchView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

       //llenar el toolbar-------
        toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Khushi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el botón de retroceso

        //final de llenar toolbar



        recycler = findViewById(R.id.recyclerViewProductos);
        producto=(EditText)findViewById(R.id.editTextTextagregarproducto);
        registrarProducto=(Button)findViewById(R.id.registrarproductos);
        modificarProducto=(Button)findViewById(R.id.boton_modificarproducto);
        eliminarProducto=(Button)findViewById(R.id.button_eliminar_producto);
        desplegar=(ImageButton)findViewById(R.id.desplegar);
        LayoutIngresaProducto=(LinearLayout)findViewById(R.id.LayoutIngresaProducto);
        searchView = findViewById(R.id.buscar);
        searchView.setOnQueryTextListener(this);



        precio=(EditText)findViewById(R.id.editTextprecio);

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listDatos = new ArrayList<nuevoProducto>();
        queue = Volley.newRequestQueue(this);
        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php");

        //recibe variables del anterior intent
        Intent intent = getIntent();
        ROL = intent.getStringExtra("Rol");
        idEmpleado= intent.getStringExtra("idEmpleado");

        roles_de_usuario();




        desplegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desplegaropcion=!desplegaropcion;
                if (desplegaropcion==true){
                    LayoutIngresaProducto.setVisibility(View.VISIBLE);

                }else {
                    LayoutIngresaProducto.setVisibility(View.GONE);
                }

            }
        });


        modificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarProducto.setEnabled(false);
                agregarproducto("http://khushiconfecciones.com//app_khushi/editar_producto.php");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        modificarProducto.setVisibility(View.GONE);
                        eliminarProducto.setVisibility(View.GONE);
                        modificarProducto.setEnabled(true);
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
                // Inhabilitar el botón al hacer clic
                registrarProducto.setEnabled(false);
                agregarproducto("http://khushiconfecciones.com//app_khushi/insertar_producto.php");


                listDatos.clear(); // Limpiar la lista existente
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registrarProducto.setEnabled(true);
                        listDatos.clear(); // Limpiar la lista existente
                        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php"); // Volver a cargar la lista desde el servidor
                    }
                }, 3000); // 3000 milisegundos = 3 segundos
            }
        });
        eliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto.setEnabled(false);
                eliminarProducto("http://khushiconfecciones.com//app_khushi/eliminar_producto.php");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        modificarProducto.setVisibility(View.GONE);
                        eliminarProducto.setVisibility(View.GONE);
                        eliminarProducto.setEnabled(true);
                        listDatos.clear();
                        agregarlista("http://khushiconfecciones.com//app_khushi/recycler.php");
                        recycler.setVisibility(View.VISIBLE);


                    }
                }, 3000); // 3000 milisegundos = 3 segundos
            }
        });
    }

    private void roles_de_usuario() {
        if (ROL.equalsIgnoreCase("SUPERVISOR")|| ROL.equalsIgnoreCase("OPERARIO") ){
            LinearLayout layoutIngresaProducto=findViewById(R.id.LayoutIngresaProducto);
            layoutIngresaProducto.setVisibility(View.GONE);
            registrarProducto.setVisibility(View.GONE);

        }
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
                        String url =(jsonObject.getString("url_foto"));


                       // String data4 = (data1+ data2+data3);


                        //String url="http://khushiconfecciones.com//app_khushi/imagenes/carpeta_imagenes/Intel_logo_(2006-2020).jpg";
                        listDatos.add(new nuevoProducto(producto,id_producto,precio,url));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter123 = new AdapterDatos(listDatos,ROL);


                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                   // Toast.makeText(getApplicationContext(),"seleccion "+listDatos.get(recycler.getChildAdapterPosition(v)).getId_producto(),Toast.LENGTH_SHORT).show();
                        if (ROL.equalsIgnoreCase("ADMIN")){
                            Intent intent= new Intent(agregarProducto.this, mostrar_agregar_subparte.class);
                            intent.putExtra("Rol",String.valueOf(ROL));
                            intent.putExtra("idEmpleado",String.valueOf(idEmpleado));
                            intent.putExtra("id_producto",String.valueOf(listDatos.get(recycler.getChildAdapterPosition(v)).getId_producto()));
                            startActivity(intent);
                        }else{
                            Toast.makeText(agregarProducto.this, "No disponible", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                adapter123.setOnItemLongClickListener(new AdapterDatos.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(nuevoProducto p) {

                        desplegaropcion=true;

                        LayoutIngresaProducto.setVisibility(View.VISIBLE);





                        if (ROL.equalsIgnoreCase("ADMIN")){
                            precio.setText(String.valueOf(p.getPrecio()));
                            producto.setText(p.getProducto());
                            idProducto=p.getId_producto();
                            modificarProducto.setVisibility(View.VISIBLE);
                            eliminarProducto.setVisibility(View.VISIBLE);
                            visibilidadModificar=true;
                            //recycler.setVisibility(View.GONE);
                            registrarProducto.setVisibility(View.GONE);

                        }


                    }
                });


                recycler.setAdapter(adapter123);
                adapter123.notifyDataSetChanged(); // Notificar cambios en el adaptador
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                isMethodRunning = true; // reiniciar la bandera en
                TextView vacio=findViewById(R.id.vacio);
                vacio.setVisibility(View.VISIBLE);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuPrincipal) {
            Intent intent = new Intent(agregarProducto.this, Home.class);
            // Agregar las banderas FLAG_CLEAR_TOP y FLAG_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);            startActivity(intent);
            startActivity(intent);
            finish(); // Cierra la actividad actual
            return true;  // Importante agregar esta línea para indicar que el evento ha sido manejado

        } else if (id == R.id.fragmento2) {

            this.logout();

        }else if (id == R.id.fragmento3) {

            Toast.makeText(this, "no diponible", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == android.R.id.home) {
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter123.filtrado(newText);
        return false;
    }
}