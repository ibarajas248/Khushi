package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
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
    RecyclerView recyclercard;

    private Toolbar toolbar1;
    HorizontalScrollView scrollListPproductosOC;
    private int idproductoSeleccionado, idoc, idPtoductoOC;
    String ordenDeCompra, producto;
    private Spinner spinnerproducto;
    ArrayList<nuevoProducto> listDatos;

    ArrayList<nuevoproducto_en_oc>listProductoOrdenCompra;

    RequestQueue requestQueue;

    ArrayList<String[]> dataArrayList;

    Boolean encabezado= true;

    private EditText cantidadLote, cantidadProductos;
    Button agregarProductoOc;
    RequestQueue queue;

    private String ROL, idEmpleado;// recibe el intent
    private boolean isMethodRunning = true;
    int id_producto;
    LinearLayout encabezadolayout;
    ImageButton lista_productos_oc,botonCardView;
    ArrayList<String> nombresProductos;
    ArrayList<Integer> idsProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto_oc);


        Intent intent=getIntent();
        idoc=Integer.parseInt(intent.getStringExtra("id_oc"));
        ordenDeCompra=intent.getStringExtra("orden_de_compra");
        ROL = intent.getStringExtra("Rol");
        idEmpleado= intent.getStringExtra("idEmpleado");



        //llenar el toolbar-------
        toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el botón de retroceso


        //final de llenar toolbar



        permisosRol();

        spinnerproducto=(Spinner) findViewById(R.id.spinnerselecproducto);
        cantidadLote=(EditText)findViewById(R.id.editTextnumero_lotes);
        cantidadProductos=(EditText)findViewById(R.id.editTextnumero_productos);
        agregarProductoOc=(Button)findViewById(R.id.btnasignar_boton_a_oc);
        recycler = findViewById(R.id.recyclerproducto_ordedecompra);
        recyclercard=findViewById(R.id.recyclerproducto_ordendecompra_dos);
        encabezadolayout=(LinearLayout)findViewById(R.id.encabezado);
        //encabezadolayout.setVisibility(View.GONE);
        lista_productos_oc= findViewById(R.id.barramenuhorizontal);
        botonCardView=findViewById(R.id.cuadricula);
        scrollListPproductosOC=findViewById(R.id.Scroll_lista);
        scrollListPproductosOC.setVisibility(View.GONE);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclercard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        listDatos = new ArrayList<nuevoProducto>();
        listProductoOrdenCompra= new ArrayList<nuevoproducto_en_oc>();
        requestQueue = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(this);
        dataArrayList = new ArrayList<>();




        spinnerproducto("http://khushiconfecciones.com/app_khushi/recycler.php"); // Volver a cargar la lista desde el servidor

        agregarlistaProductoOc("http://khushiconfecciones.com//app_khushi/buscar_operaciones_oc.php?id_oc="+idoc);
        agregarlistaProductoOcCardview("http://khushiconfecciones.com//app_khushi/buscar_operaciones_oc.php?id_oc="+idoc);


        lista_productos_oc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollListPproductosOC.setVisibility(View.VISIBLE);
                lista_productos_oc.setVisibility(View.GONE);
                botonCardView.setVisibility(View.VISIBLE);

            }
        });

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

    private void permisosRol() {

        LinearLayout elementosAdmin=findViewById(R.id.infoAdmin);
        if (!ROL.equalsIgnoreCase("ADMIN")){
            elementosAdmin.setVisibility(View.GONE);
        }
    }


    private void spinnerproducto (String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listDatos.clear(); // Limpiar la lista existente

                 nombresProductos = new ArrayList<>(); // ArrayList para almacenar nombres de productos
                 idsProductos = new ArrayList<>();


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
                // Crear el adaptador con el diseño personalizado
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(agregar_producto_oc.this, R.layout.spinner_filtrar_en_lotes_operaciones, nombresProductos);
                // Establecer el diseño del dropdown personalizado
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinnerproducto.setAdapter(adapter); // Establecer el adaptador en el Spinner
                //configurarSpinnerConBuscador();


                spinnerproducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Obtener el ID seleccionado usando la posición en el ArrayList de IDs
                        idproductoSeleccionado = idsProductos.get(position);
                        producto=nombresProductos.get(position);

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

                listProductoOrdenCompra.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);


                        int id =Integer.parseInt(jsonObject.getString("id"));
                        idPtoductoOC=id;

                        int id_oc =Integer.parseInt(jsonObject.getString("id_oc"));
                        id_producto =Integer.parseInt(jsonObject.getString("id_producto"));
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
                Adapter_producto_oc adapter123 = new Adapter_producto_oc(listProductoOrdenCompra,encabezado);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!encabezado){

                        }else{

                        }
                        int position = recycler.getChildAdapterPosition(v);

                        if (position != RecyclerView.NO_POSITION) {
                            nuevoproducto_en_oc clickedItem = listProductoOrdenCompra.get(position);

                            // Acceder al id del elemento clickeado
                            int idProductoOC = clickedItem.getId();

                            // Pasar el id a la siguiente actividad mediante Intent
                            Intent intent = new Intent(agregar_producto_oc.this, operaciones_lotes.class);
                            intent.putExtra("id", String.valueOf(idProductoOC));
                            intent.putExtra("id_producto", String.valueOf(id_producto));
                            intent.putExtra("ROL", String.valueOf(ROL));
                            intent.putExtra("idEmpleado", String.valueOf(idEmpleado));

                            startActivity(intent);
                        }

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
    private void agregarlistaProductoOcCardview(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                if (encabezado=false){
                    //encabezadolayout.setVisibility(View.GONE);
                    lista_productos_oc.setVisibility(View.GONE);

                }
                JSONObject jsonObject = null;

                listProductoOrdenCompra.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);


                        int id =Integer.parseInt(jsonObject.getString("id"));
                        idPtoductoOC=id;

                        int id_oc =Integer.parseInt(jsonObject.getString("id_oc"));
                        id_producto =Integer.parseInt(jsonObject.getString("id_producto"));
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
                Adapter_producto_oc adapter123 = new Adapter_producto_oc(listProductoOrdenCompra,encabezado);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!encabezado){

                        }else{

                        }
                        int position = recyclercard.getChildAdapterPosition(v);

                        if (position != RecyclerView.NO_POSITION) {
                            nuevoproducto_en_oc clickedItem = listProductoOrdenCompra.get(position);

                            // Acceder al id del elemento clickeado
                            int idProductoOC = clickedItem.getId();

                            // Pasar el id a la siguiente actividad mediante Intent
                            Intent intent = new Intent(agregar_producto_oc.this, operaciones_lotes.class);
                            intent.putExtra("id", String.valueOf(idProductoOC));
                            intent.putExtra("id_producto", String.valueOf(id_producto));
                            intent.putExtra("ROL", String.valueOf(ROL));
                            intent.putExtra("idEmpleado", String.valueOf(idEmpleado));

                            startActivity(intent);
                        }

                    }
                });



                recyclercard.setAdapter(adapter123);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuPrincipal) {
            Intent intent = new Intent(agregar_producto_oc.this, Home.class);
            // Agregar las banderas FLAG_CLEAR_TOP y FLAG_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);            startActivity(intent);
            startActivity(intent);
            finish(); // Cierra la actividad actual
            return true;  // Importante agregar esta línea para indicar que el evento ha sido manejado

        } else if (id == R.id.fragmento2) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.fragmento3) {

            Toast.makeText(this, "no diponible", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == android.R.id.home) {
            // Maneja el clic en el botón de retroceso
            onBackPressed(); // Esto ejecutará el comportamiento predeterminado de retroceder
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void configurarSpinnerConBuscador() {
        // Mostrar el diálogo con buscador al hacer clic en el Spinner
        spinnerproducto.setOnTouchListener((v, event) -> {
            mostrarDialogoConBuscador();
            return true;  // Evita que el Spinner se expanda normalmente
        });
    }

    private void mostrarDialogoConBuscador() {
        // Crear el AlertDialog.Builder
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        // Inflar el layout personalizado
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_searchable_spinner, null);
        builder.setView(dialogView);

        // Referencias a los elementos del layout personalizado
        EditText editTextBuscar = dialogView.findViewById(R.id.editTextBuscar);
        ListView listViewOpciones = dialogView.findViewById(R.id.listViewOpciones);

        // Crear y configurar el adaptador para el ListView
        final ArrayAdapter<String> adaptadorListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresProductos);
        listViewOpciones.setAdapter(adaptadorListView);

        // Filtrar la lista mientras el usuario escribe en el campo de búsqueda
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adaptadorListView.getFilter().filter(charSequence);  // Filtrar las opciones del ListView

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Crear el AlertDialog
        final AlertDialog dialog = builder.create();

        // Manejar la selección de una opción en el ListView
        listViewOpciones.setOnItemClickListener((parent, view, position, id) -> {

           /* @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el ID seleccionado usando la posición en el ArrayList de IDs
                idproductoSeleccionado = idsProductos.get(position);
                producto=nombresProductos.get(position);

                // Guardar el ID en una variable o realizar alguna acción con él
                // Ejemplo: guardar el ID en una variable global
                // idSeleccionadoGlobal = idSeleccionado;

                */


            String opcionSeleccionada = adaptadorListView.getItem(position);
            idproductoSeleccionado = idsProductos.get(position);




            if (opcionSeleccionada != null) {
                int posicionEnSpinner = nombresProductos.indexOf(opcionSeleccionada);

                spinnerproducto.setSelection(posicionEnSpinner);  // Seleccionar la opción en el Spinner
            }
            dialog.dismiss();  // Cerrar el diálogo al seleccionar una opción
        });

        // Mostrar el diálogo
        dialog.show();
    }


    private void mostrarDialogoConBusqueda(final ArrayList<String> nombresProductos, final ArrayList<Integer> idsProductos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_spinner, null);
        builder.setView(dialogView);

        final SearchView searchView = dialogView.findViewById(R.id.searchView);
        final ListView listView = dialogView.findViewById(R.id.listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresProductos);
        listView.setAdapter(adapter);

        // Filtrar la lista basada en el texto ingresado en el SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Configurar el evento de selección en la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String productoSeleccionado = adapter.getItem(position);
                int idSeleccionado = idsProductos.get(nombresProductos.indexOf(productoSeleccionado));

                // Guardar el ID y producto seleccionados
                idproductoSeleccionado = idSeleccionado;
                producto = productoSeleccionado;

                // Cerrar el diálogo
                //dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}