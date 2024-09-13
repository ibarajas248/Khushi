package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.AdapterOC;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.ordenDeCompraclase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class ordenDeCompra extends AppCompatActivity {

    RecyclerView recycler;
    RequestQueue queue;
    ArrayList<ordenDeCompraclase> listOC;
    boolean validacion = false;
    private boolean isMethodRunning = true;
    private Toolbar toolbar1;

    private EditText escribeOC;
    private boolean visibilidadModificar= false;

    private ordenDeCompraclase nuevaOC;

    private Button botonEditarOc, botonagregaroc, botoneliminarOC;
    private String ROL, idEmpleado;// recibe el intent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_de_compra);

        Intent intent = getIntent();
        ROL = intent.getStringExtra("Rol");
        idEmpleado= intent.getStringExtra("idEmpleado");

        rolesusuarios();



        toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Khushi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el botón de retroceso
        listOC = new ArrayList<ordenDeCompraclase>();

        recycler = findViewById(R.id.recyclerordedecompra);
        escribeOC=findViewById(R.id.edt_orden_de_compra);
        botonEditarOc=findViewById(R.id.btneditar_OC);
        botonagregaroc=findViewById(R.id.buttonagregaroc);
        botoneliminarOC=findViewById(R.id.btnEliminar);


        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        queue = Volley.newRequestQueue(this);
        lista_oc("http://khushiconfecciones.com/app_khushi/buscar_oc.php");

        botonagregaroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar_oc("http://khushiconfecciones.com//app_khushi/agregar_oc.php");
                /*listOC.clear(); // Limpiar la lista existente
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listOC.clear(); // Limpiar la lista existente
                        lista_oc("http://khushiconfecciones.com/app_khushi/buscar_oc.php");
                    }
                }, 3000); // 3000 milisegundos = 3 segundos


                 */

            }
        });
        botonEditarOc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                visibilidadModificar=true;

                editar_oc("http://khushiconfecciones.com//app_khushi/editar_oc.php");

                listOC.clear(); // Limpiar la lista existente


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listOC.clear(); // Limpiar la lista existente
                        lista_oc("http://khushiconfecciones.com/app_khushi/buscar_oc.php");
                    }
                }, 3000); // 3000 milisegundos = 3 segundos


                botonEditarOc.setVisibility(View.GONE);
                visibilidadModificar=false;
                botonagregaroc.setVisibility(View.VISIBLE);
                botoneliminarOC.setVisibility(View.GONE);
            }
        });

        botoneliminarOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ordenDeCompra.this);
                builder.setTitle("¿Desea eliminar la orden de compra?");
                builder.setMessage("Si elimina la OC perderá toda la información asociada, eso incluye productos y producción vinculada.");

                // Botón "Aceptar"
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar_OC("http://khushiconfecciones.com/app_khushi/eliminar_oc.php");
                        dialog.dismiss();

                    }
                });

                // Botón "Cancelar"
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción al hacer clic en "Cancelar"
                        dialog.dismiss();
                    }
                });

                // Mostrar el dialogo
                builder.show();

            }
        });

    }



    private void rolesusuarios() {

        LinearLayout espacioParaAdmin=findViewById(R.id.LayoutAgregarOC);
        if(!ROL.equalsIgnoreCase("ADMIN")){
            espacioParaAdmin.setVisibility(View.GONE);
        }
    }


    private void lista_oc(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listOC.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        String ordenCompra =(jsonObject.getString("ordendeCompra"));

                        int id_OC = Integer.parseInt(jsonObject.getString("idOrdenCompra"));

                        // String data4 = (data1+ data2+data3);

                        listOC.add(new ordenDeCompraclase(id_OC, ordenCompra));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AdapterOC adapter123 = new AdapterOC(listOC);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent= new Intent(ordenDeCompra.this, agregar_producto_oc.class);
                        intent.putExtra("id_oc",String.valueOf(listOC.get(recycler.getChildAdapterPosition(v)).getIdOrdenCompra()));
                        intent.putExtra("orden_de_compra",String.valueOf(listOC.get(recycler.getChildAdapterPosition(v)).getOrdendeCompra()));
                        intent.putExtra("Rol",String.valueOf(ROL));
                        intent.putExtra("idEmpleado",String.valueOf(idEmpleado));

                        startActivity(intent);



                    }
                });
                adapter123.setOnItemLongClickListener(new AdapterOC.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(ordenDeCompraclase OC) {

                        //aca hacer las acciones para el longclicklistener


                        escribeOC.setText(String.valueOf(OC.getOrdendeCompra()));
                        nuevaOC= new ordenDeCompraclase(OC.getIdOrdenCompra(),OC.getOrdendeCompra());
                        botonEditarOc.setVisibility(View.VISIBLE);
                        visibilidadModificar=true;
                        botonagregaroc.setVisibility(View.GONE);
                        botoneliminarOC.setVisibility(View.VISIBLE);
                        //Toast.makeText(ordenDeCompra.this, nuevaOC.getIdOrdenCompra()+nuevaOC.getOrdendeCompra(), Toast.LENGTH_SHORT).show();






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



    //debo terminar el metodo para agregar nuevas ordenes de compra
    private void  agregar_oc (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(ordenDeCompra.this, "orden de compra creada satisfactoriamente", Toast.LENGTH_SHORT).show();
                listOC.clear(); // Limpiar la lista existente
                lista_oc("http://khushiconfecciones.com/app_khushi/buscar_oc.php");
                escribeOC.setText("");



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(ordenDeCompra.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();

                parametros.put("ordendeCompra",escribeOC.getText().toString());



                if (visibilidadModificar==true){
                    parametros.put("idOrdenCompra",String.valueOf(nuevaOC.getIdOrdenCompra()));
                    Toast.makeText(ordenDeCompra.this, "idOrdenCompra: "+nuevaOC.getIdOrdenCompra(), Toast.LENGTH_SHORT).show();
                }



                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void  editar_oc (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(ordenDeCompra.this, "orden de compra editada correctamente ", Toast.LENGTH_SHORT).show();
                listOC.clear(); // Limpiar la lista existente
                lista_oc("http://khushiconfecciones.com/app_khushi/buscar_oc.php");
                escribeOC.setText("");



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(ordenDeCompra.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();

                parametros.put("ordendeCompra",escribeOC.getText().toString());


                parametros.put("idOrdenCompra",String.valueOf(nuevaOC.getIdOrdenCompra()));




                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuPrincipal) {
            Intent intent = new Intent(ordenDeCompra.this, Home.class);
            // Agregar las banderas FLAG_CLEAR_TOP y FLAG_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);            startActivity(intent);
            startActivity(intent);
            finish(); // Cierra la actividad actual
            return true;  // Importante agregar esta línea para indicar que el evento ha sido manejado

        } else if (id == R.id.fragmento2) {

            this.logout();

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


    private void eliminar_OC (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Toast.makeText(ordenDeCompra.this, "orden de compra eliminada satisfactoriamente ", Toast.LENGTH_SHORT).show();
                //limpiarFormulario();
                listOC.clear(); // Limpiar la lista existente
                lista_oc("http://khushiconfecciones.com/app_khushi/buscar_oc.php");
                escribeOC.setText("");
                botoneliminarOC.setVisibility(View.GONE);
                botonEditarOc.setVisibility(View.GONE);
                botonagregaroc.setVisibility(View.VISIBLE);
                visibilidadModificar=false;


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                Toast.makeText(ordenDeCompra.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                //parametros.put("idOrdenCompra",edtCodigo.getText().toString());
                parametros.put("idOrdenCompra",String.valueOf(nuevaOC.getIdOrdenCompra()));


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}