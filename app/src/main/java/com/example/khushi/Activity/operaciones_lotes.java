package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_lotes;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class operaciones_lotes extends AppCompatActivity  implements SearchView.OnQueryTextListener{

    ArrayList<operaciones_lotes_clase> listOperaciones;
    ArrayList<String>listEmpleados;
    ArrayList<Empleado_clase> listaEmpleados;

    ArrayList<String> nombres;

    ArrayList<Integer> idsEmpleados;

    RecyclerView recycler;
    RequestQueue queue;
    LinearLayout linearLayoutFicha,contenedorRecycler,LayoutImageButtondividirManual, LayoutImageButtonEditar ;
    ArrayAdapter<String> adapter;// adapter que va a tomar el spinner
    Adapter_operaciones_lotes adapter123;
    public String finalVariableRecibida_idproducto_oc;
    private Spinner spinnerFiltrar ;
    private Spinner spinnerFiltrarDinamico;
    private EditText cantidadSublotesEditText;
    private Toolbar toolbar1;


    SearchView buscarOperacionesDB;
    private LinearLayout contenedorEditTexts,LayoutImageButtondividirAutomatico;
    ScrollView scrollView;
    //editText para modificar cantidad de nuevos lotes
    private EditText cantLote1,cantLote2,cantLote3,cantLote4,cantLote5,cantLote6,cantLote7,cantLote8,cantLote9,cantLote10;






    ImageButton botonimagendividir,botonimagendividirAutomatico,botonImagenEditar;
    Button botonDividirLote, botonEditarCantidad,botonDividirManual,botonPartirLote,botonDividirManualConfirmar, botonPartirPartesIguales;


    TextView productTextView, sectionTextView, operationTextView, quantityTextView, nameTextView, lastNameTextView;


    int idParaUpdate; //variable para almacenar el id de la tarea asignada


    // variables que necesito para partir lote manual

    //  fin de variables que necesito para partir lote manual


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones_lotes);

        cantLote1 = (EditText) findViewById(R.id.cantidadSublotes1);
        cantLote2 = (EditText) findViewById(R.id.cantidadSublotes2);
        cantLote3 = (EditText) findViewById(R.id.cantidadSublotes3);
        cantLote4 = (EditText) findViewById(R.id.cantidadSublotes4);
        cantLote5 = (EditText) findViewById(R.id.cantidadSublotes5);
        cantLote6 = (EditText) findViewById(R.id.cantidadSublotes6);
        cantLote7 = (EditText) findViewById(R.id.cantidadSublotes7);
        cantLote8 = (EditText) findViewById(R.id.cantidadSublotes8);
        cantLote9 = (EditText) findViewById(R.id.cantidadSublotes9);
        cantLote10 = (EditText) findViewById(R.id.cantidadSublotes10);


        String[]opcionesFiltrado={"Todos los filtros","Habilitado", "producto", "Seccion","Operaciones","Cantidad","Nombre","Apellido","Lote","completado"};

        spinnerFiltrarDinamico=(Spinner)findViewById(R.id.spinnerFiltrosDinamicos);

        ArrayAdapter<String> adapterSpinnerDinamico= new ArrayAdapter<String>(this,
                R.layout.spinner_filtrar_en_lotes_operaciones, opcionesFiltrado);

        spinnerFiltrarDinamico.setAdapter(adapterSpinnerDinamico);

        botonimagendividir = (ImageButton) findViewById(R.id.imageButtondividir);//nuevo boton dividir
        botonimagendividirAutomatico=(ImageButton)findViewById(R.id.imageButtondividirAutomatico);
        botonImagenEditar=(ImageButton)findViewById(R.id.imageButtonEditar);//boton imagen editar

        toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Khushi");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el botón de retroceso

        botonDividirManualConfirmar=(Button)findViewById(R.id.botonDividirManualConfirmar); //confirma enviar cantidades
        botonPartirPartesIguales=(Button)findViewById(R.id.boton_dividir_automatico);

        buscarOperacionesDB = (SearchView) findViewById(R.id.searchoperacionesDB);
        buscarOperacionesDB.setOnQueryTextListener((SearchView.OnQueryTextListener) this);


        //layout que contiene el boton de dividir manualmente una operacion de un lote
        LayoutImageButtondividirManual=(LinearLayout)findViewById(R.id.LayoutImageButtondividir);
        LayoutImageButtondividirAutomatico=(LinearLayout)findViewById(R.id.LayoutImageButtondividirAutomatico);
        LayoutImageButtonEditar=(LinearLayout)findViewById(R.id.LayoutImageButtonEditar);

        contenedorRecycler = findViewById(R.id.contenedorRecycler); //contenedor del recycler

        linearLayoutFicha = findViewById(R.id.llContainer); //ficha de seleccion

        spinnerFiltrar = (Spinner) findViewById(R.id.spinnerFiltrar);
        cantidadSublotesEditText = findViewById(R.id.cantidadSublotes);
        contenedorEditTexts = findViewById(R.id.linnear_operacion_lote);

        scrollView = findViewById(R.id.scroll_operacion_lote);

        //Botones
        botonDividirLote = (Button) findViewById(R.id.boton_dividir_lote);
        botonEditarCantidad = (Button) findViewById(R.id.botonEditarCantidad);
        botonPartirLote=(Button)findViewById(R.id.partir_lote); //subdivide el lote con las cantidades agregadas manualmente


        // este boton me permite dividir el lote y ajustar manualmente las cantidades.

        botonDividirManual=(Button)findViewById(R.id.botonDividirManual);



        // array para opciones del Spinner
        String[]opcionesFiltradoDinamico={"seleccione", "Sin Asignar", "Tareas Asignadas"};

        //Arrayadapter del Spinner

        ArrayAdapter<String> adapterSpinner= new ArrayAdapter<String>(this,
                R.layout.spinner_filtrar_en_lotes_operaciones, opcionesFiltradoDinamico);

        spinnerFiltrar.setAdapter(adapterSpinner);


        queue = Volley.newRequestQueue(this);
        listOperaciones = new ArrayList<operaciones_lotes_clase>();

        // ------------ inicializo los texview de la tarjeta de la operacion realizada

        productTextView = findViewById(R.id.tvProduct);
        sectionTextView = findViewById(R.id.tvSection);
        operationTextView = findViewById(R.id.tvOperation);
        quantityTextView = findViewById(R.id.tvQuantity);
        nameTextView = findViewById(R.id.tvName);
        lastNameTextView = findViewById(R.id.tvLastName);


        //-----------------------------------------------------------------------------



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
        finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Llama al primer método aquí
                agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc);


            }
        }, 100); // Retraso de 5000 milisegundos (5 segundos)


        //spinnersubparte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          spinnerFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  String opcionSeleccionada = parent.getItemAtPosition(position).toString();

                  // Ejemplo de condición
                  if ("Sin Asignar".equals(opcionSeleccionada)) {
                      handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              // Llama al primer método aquí
                              agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote_filtro.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc+"&empleado=null");


                          }
                      }, 3000); // Retraso de 5000 milisegundos (5 segundos)
                  } else if ("Tareas Asignadas".equals(opcionSeleccionada)) {
                      handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote_filtro.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc+"&empleado=no null");



                          }
                      }, 3000); // Retraso de 5000 milisegundos (5 segundos)
                  }

              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {


              }
          });










    }



   /* private void eliminarOperacion (String URL,){
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

                Toast.makeText(operaciones_lotes.this, error.toString(),Toast.LENGTH_SHORT).show();
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

    }*/
    public void agregarListaOperacion_Lote(String URL) {
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
                        //falta implementar el id_producto_oc al adapter
                        int id_producto_oc = (Integer.parseInt(jsonObject.getString("id_producto_orden_compra")));
                        String empleado=StringEmpleado;
                        int lote= Integer.parseInt(jsonObject.getString("lote"));
                        String completado= jsonObject.getString("completado");
                        String nombre= jsonObject.getString("nombre");
                        String Apellidos= jsonObject.getString("Apellidos");







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


                        //String producto, String subparte, String operaciones, String empleado, String nombre, String apellido, int id_lotes_operaciones, int cantidad, int id_operacione_subparte_producto, int lotes, int id_producto_oc
                        listOperaciones.add(new operaciones_lotes_clase(producto,subparte,operaciones,id_lotes_operaciones,cantidad,empleado,id_operaciones_subparte_producto, lote, id_producto_oc,completado,nombre,Apellidos));

                        //hacer que el Spinner se llene con una consulta sql a la tabla de empleados...llamar un metedo
                        listEmpleados.add(String.valueOf(empleado));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter123 = new Adapter_operaciones_lotes(operaciones_lotes.this,listOperaciones,nombres,idsEmpleados,listaEmpleados);
                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // este metodo se configuró en el adapter
                    }
                });

                adapter123.setOnItemLongClickListener(new Adapter_operaciones_lotes.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(operaciones_lotes_clase asignacion) {

                        idParaUpdate= asignacion.getId_lotes_operaciones();


                        linearLayoutFicha.setVisibility(View.VISIBLE);
                        //botonDividirLote.setVisibility(View.VISIBLE);
                        //botonEditarCantidad.setVisibility(View.VISIBLE);
                        botonimagendividir.setVisibility(View.VISIBLE);

                        //layout que contiene el boton de copiar
                        LinearLayout layoutBotonCopiar=(findViewById(R.id.LayoutImageButtonCopiar));
                        layoutBotonCopiar.setVisibility(View.VISIBLE);

                        LayoutImageButtondividirManual.setVisibility(View.VISIBLE);
                        LayoutImageButtondividirAutomatico.setVisibility(View.VISIBLE);
                        LayoutImageButtonEditar.setVisibility(View.VISIBLE);

                        //idParaUpdate= asignacion.getId_lotes_operaciones();

                        //asigna los valores a la tarjeta
                        productTextView.setText(asignacion.getProducto());
                        sectionTextView.setText(asignacion.getSubparte());
                        operationTextView.setText(asignacion.getOperaciones());
                        quantityTextView.setText(String.valueOf(asignacion.getCantidad()));
                        nameTextView.setText(asignacion.getNombre());
                        lastNameTextView.setText(asignacion.getApellido());



                        //-------


                        int id_producto_subparte_operacion= asignacion.getId_operacione_subparte_producto();


                        //esconde el recycler
                        spinnerFiltrar.setVisibility(View.GONE);
                        buscarOperacionesDB.setVisibility(View.GONE);
                        contenedorRecycler.setVisibility(View.GONE);
                        spinnerFiltrarDinamico.setVisibility(View.GONE);
                       // recycler.setVisibility(View.GONE);


                        ImageButton botonImagenCopiar=(ImageButton) findViewById(R.id.imageButtonCopiar);

                        botonImagenCopiar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cantidadSublotesEditText.setVisibility(View.VISIBLE);
                                cantidadSublotesEditText.setText("");
                                cantidadSublotesEditText.setHint("cantidad");
                                Button botonCopiar=(Button)findViewById(R.id.boton_copiar);
                                botonCopiar.setVisibility(View.VISIBLE);
                                LayoutImageButtonEditar.setVisibility(View.GONE);
                                LayoutImageButtondividirManual.setVisibility(View.GONE);
                                LayoutImageButtondividirAutomatico.setVisibility(View.GONE);
                                layoutBotonCopiar.setVisibility(View.GONE);
                                linearLayoutFicha.setVisibility(View.GONE);
                                spinnerFiltrarDinamico.setVisibility(View.GONE);

                                botonCopiar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {





                                        dividirOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operaciones_lotes.php",
                                        asignacion.getId_operacione_subparte_producto(),asignacion.getId_producto_oc(),
                                        Integer.parseInt(cantidadSublotesEditText.getText().toString()),asignacion.getLotes());


                                        botonCopiar.setVisibility(View.GONE);
                                        cantidadSublotesEditText.setVisibility(View.GONE);

                                        buscarOperacionesDB.setVisibility(View.GONE);
                                        contenedorRecycler.setVisibility(View.GONE);

                                        spinnerFiltrar.setVisibility(View.VISIBLE);
                                        buscarOperacionesDB.setVisibility(View.VISIBLE);
                                        contenedorRecycler.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.VISIBLE);
                                        spinnerFiltrarDinamico.setVisibility(View.VISIBLE);


                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Llama al primer método aquí
                                                agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc);


                                            }
                                        }, 1000); // Retraso de 5000 milisegundos (5 segundos)




                                    }
                                });


                            }
                        });
                        botonImagenCopiar.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Toast.makeText(operaciones_lotes.this, "Copiar", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });

                        botonDividirLote.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                int id_producto_subparte_operacion= asignacion.getId_operacione_subparte_producto();// variable local


                                int cantidad_lote= asignacion.getCantidad();
                                int lote= asignacion.getLotes();
                                int id_producto_oc=asignacion.getId_producto_oc();
                                int id_lotes_operaciones=asignacion.getId_lotes_operaciones();


                            //String URL, int id_lotes_operaciones, int id_producto_subparte_operacion,int id_producto_oc,int cantidad, int lote
                                eliminarOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/eliminar_operacion_lote.php");
                                String cantidadSublotesString = cantidadSublotesEditText.getText().toString();



                                if (!cantidadSublotesString.isEmpty()) {
                                    int cantidadLotesIterar = Integer.parseInt(cantidadSublotesString);
                                    int modulo=cantidad_lote%cantidadLotesIterar;
                                    for (int i = 0; i < cantidadLotesIterar; i++) {
                                       if (modulo !=0){
                                           dividirOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operaciones_lotes.php", id_producto_subparte_operacion, id_producto_oc, cantidad_lote/cantidadLotesIterar+modulo, lote);
                                           modulo=0;
                                       }else{
                                           dividirOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operaciones_lotes.php", id_producto_subparte_operacion, id_producto_oc, cantidad_lote/cantidadLotesIterar, lote);

                                       }
                                        //hace aparecer el recycler

                                        spinnerFiltrar.setVisibility(View.VISIBLE);
                                        buscarOperacionesDB.setVisibility(View.VISIBLE);
                                        contenedorRecycler.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.VISIBLE);

                                        //----------------------------------

                                        //-------desaparece la tarjeta-------------------//

                                        cantidadSublotesEditText.setVisibility(View.GONE);
                                        linearLayoutFicha.setVisibility(View.GONE);
                                        botonDividirLote.setVisibility(View.GONE);
                                        botonEditarCantidad.setVisibility(View.GONE);



                                        Toast.makeText(operaciones_lotes.this, "Lote dividido exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Manejar el caso en que la cadena esté vacía
                                    Toast.makeText(operaciones_lotes.this, "La cantidad de sublotes no puede estar vacía", Toast.LENGTH_SHORT).show();
                                }


                                Handler handler = new Handler();
                                //finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Llama al primer método aquí
                                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc);


                                    }
                                }, 1000); // Retraso de 5000 milisegundos (5 segundos)


                            }
                        });

                        botonEditarCantidad.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int id_producto_subparte_operacion= asignacion.getId_operacione_subparte_producto();
                                int cantidad_lote= Integer.parseInt(cantidadSublotesEditText.getText().toString());
                                int lote= asignacion.getLotes();
                                int id_producto_oc=asignacion.getId_producto_oc();
                                int id_lotes_operaciones=asignacion.getId_lotes_operaciones();

                                //String URL, int id_lotes_operaciones, int id_producto_subparte_operacion,int id_producto_oc,int cantidad, int lote
                                //eliminarOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/eliminar_operacion_lote.php");
                                String cantidadSublotesString = cantidadSublotesEditText.getText().toString();



                                if (!cantidadSublotesString.isEmpty()) {
                                    editarCantidadOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operaciones_lotes.php",id_lotes_operaciones, cantidad_lote);


                                    //hace aparecer el recycler

                                    spinnerFiltrar.setVisibility(View.VISIBLE);
                                    buscarOperacionesDB.setVisibility(View.VISIBLE);
                                    contenedorRecycler.setVisibility(View.VISIBLE);
                                    recycler.setVisibility(View.VISIBLE);

                                    //----------------------------------

                                    Toast.makeText(operaciones_lotes.this, "cantidad modificada exitosamente", Toast.LENGTH_LONG).show();

                                    //desaparece la tarjeta

                                    cantidadSublotesEditText.setVisibility(View.GONE);
                                    linearLayoutFicha.setVisibility(View.GONE);
                                    botonDividirLote.setVisibility(View.GONE);
                                    botonEditarCantidad.setVisibility(View.GONE);

                                } else {
                                    // Manejar el caso en que la cadena esté vacía
                                    Toast.makeText(operaciones_lotes.this, "La cantidad no puede estar vacía", Toast.LENGTH_SHORT).show();
                                }

                                Handler handler = new Handler();
                                //finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Llama al primer método aquí
                                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc);


                                    }
                                }, 1500); // Retraso de 5000 milisegundos (5 segundos)


                            }


                        });

                        botonimagendividir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cantidadSublotesEditText.setHint("numero a dividir lote");
                                cantidadSublotesEditText.setVisibility(View.VISIBLE);
                                botonDividirManual.setVisibility(View.VISIBLE);
                                layoutBotonCopiar.setVisibility(View.GONE);
                                LayoutImageButtonEditar.setVisibility(View.GONE);
                                botonDividirLote.setVisibility(View.GONE);
                                LayoutImageButtondividirManual.setVisibility(View.GONE);
                                botonEditarCantidad.setVisibility(View.GONE);
                                LayoutImageButtondividirAutomatico.setVisibility(View.GONE);


                            }
                        });
                        botonimagendividir.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Toast.makeText(operaciones_lotes.this, "Dividir manualmente", Toast.LENGTH_LONG).show();
                                return false;
                            }
                        });







                        botonDividirManual.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //desaparencen los botones que ya no necesito
                                cantidadSublotesEditText.setVisibility(View.GONE);
                                botonDividirManual.setVisibility(View.GONE);
                                botonDividirLote.setVisibility(View.GONE);
                                botonEditarCantidad.setVisibility(View.GONE);
                                botonimagendividir.setVisibility(View.GONE);


                                //aparecer el boton para agregar las particiones del lote
                                botonDividirManualConfirmar.setVisibility(View.VISIBLE);

                                cantidadSublotesEditText.setVisibility(View.VISIBLE);//caja de texto donde ingreso la info
                                int cantidadsublotes= Integer.parseInt(cantidadSublotesEditText.getText().toString());

                                // creo un Array que contenga los  edittext que deseo mostrar
                                EditText[] cantLoteArray = new EditText[]{cantLote1, cantLote2, cantLote3, cantLote4, cantLote5, cantLote6, cantLote7, cantLote8, cantLote9, cantLote10};

                                if (cantidadsublotes<=10){
                                    for (int i = 0; i<cantidadsublotes;i++){
                                        cantLoteArray[i].setVisibility(View.VISIBLE);

                                    }

                                    cantidadSublotesEditText.setVisibility(View.GONE);
                                    botonDividirManualConfirmar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                             //eliminarOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/eliminar_operacion_lote.php");

                                            for (int i = 0; i<cantidadsublotes;i++){
                                              //  cantLoteArray[i].setVisibility(View.VISIBLE);


                                                int cantidad_lote= Integer.parseInt(cantLoteArray[i].getText().toString());//agarro la cantidad del editText


                                                int id_producto_subparte_operacion= asignacion.getId_operacione_subparte_producto();
                                                int lote= asignacion.getLotes();
                                                int id_producto_oc=asignacion.getId_producto_oc();
                                                int id_lotes_operaciones= asignacion.getId_lotes_operaciones();
                                                int cantidad=asignacion.getCantidad();
                                                String fecha= asignacion.getFecha();




                                                //asignacion.getId_lotes_operaciones()
                                               if (i!=0){

                                                   String URL= "http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operaciones_lotes.php";
                                                   dividirOperacion(URL, id_producto_subparte_operacion, id_producto_oc, cantidad_lote, lote);




                                                }else if (i==0){
                                                   String URL= "http://khushiconfecciones.com//app_khushi/consultas_lotes/editar_cantidad.php";
                                                   int cantidadasignada= Integer.parseInt(cantLoteArray[0].getText().toString());
                                                   editarCantidadOperacion(URL,id_lotes_operaciones,cantidadasignada);


                                               }


                                            }
                                            for (int j = 0; j<cantidadsublotes;j++) {
                                                cantLoteArray[j].setVisibility(View.GONE);
                                            }
                                            botonDividirManualConfirmar.setVisibility(View.GONE);
                                            cantidadSublotesEditText.setVisibility(View.GONE);
                                            linearLayoutFicha.setVisibility(View.GONE);

                                               //recycler.setVisibility(View.VISIBLE);
                                            contenedorRecycler.setVisibility(View.VISIBLE);
                                            spinnerFiltrar.setVisibility(View.VISIBLE);
                                            buscarOperacionesDB.setVisibility(View.VISIBLE);
                                            spinnerFiltrarDinamico.setVisibility(View.VISIBLE);






                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // Llama al primer método aquí
                                                    agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote_filtro.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc+"&empleado=null");


                                                }
                                            }, 3000); // Retraso de 5000 milisegundos (5 segundos)








                                        }
                                    });

                                }else{
                                    Toast.makeText(operaciones_lotes.this, "No puede agregar mas de 10 lotes, intente con otra cantidad", Toast.LENGTH_SHORT).show();
                                }



                            }
                        });
                        botonimagendividirAutomatico.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cantidadSublotesEditText.setHint("Numero a dividir lote");
                                cantidadSublotesEditText.setVisibility(View.VISIBLE);
                                botonPartirPartesIguales.setVisibility(View.VISIBLE);
                                LayoutImageButtondividirAutomatico.setVisibility(View.GONE);

                                //ele3mentos a ocultar
                                LayoutImageButtonEditar.setVisibility(View.GONE);
                                botonEditarCantidad.setVisibility(View.GONE);
                                botonDividirLote.setVisibility(View.GONE);
                                LayoutImageButtondividirManual.setVisibility(View.GONE);
                                layoutBotonCopiar.setVisibility(View.GONE);
                                spinnerFiltrarDinamico.setVisibility(View.GONE);


                            }
                        });
                        botonimagendividirAutomatico.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Toast.makeText(operaciones_lotes.this, "Dividir automatico", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });
                        botonPartirPartesIguales.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id_producto_subparte_operacion= asignacion.getId_operacione_subparte_producto();// variable local


                                int cantidad_lote= asignacion.getCantidad();
                                int lote= asignacion.getLotes();
                                int id_producto_oc=asignacion.getId_producto_oc();
                                int id_lotes_operaciones= asignacion.getId_lotes_operaciones();


                                //String URL, int id_lotes_operaciones, int id_producto_subparte_operacion,int id_producto_oc,int cantidad, int lote
                                //eliminarOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/eliminar_operacion_lote.php");
                                String cantidadSublotesString = cantidadSublotesEditText.getText().toString();

                                if (!cantidadSublotesString.isEmpty()) {
                                    int cantidadLotesIterar = Integer.parseInt(cantidadSublotesString);
                                    int modulo=cantidad_lote%cantidadLotesIterar;
                                    for (int i = 0; i < cantidadLotesIterar; i++) {
                                        if (modulo !=0){



                                            if (i==0){
                                                editarCantidadOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/editar_cantidad.php",id_lotes_operaciones,cantidad_lote/cantidadLotesIterar+modulo);
                                                modulo=0;

                                            }

                                        }else{
                                            if (i==0){
                                                editarCantidadOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/editar_cantidad.php",id_lotes_operaciones,cantidad_lote/cantidadLotesIterar);
                                            }else {
                                                dividirOperacion("http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operaciones_lotes.php", id_producto_subparte_operacion, id_producto_oc, cantidad_lote/cantidadLotesIterar, lote);
                                            }


                                        }
                                        //hace aparecer el recycler

                                        spinnerFiltrar.setVisibility(View.VISIBLE);
                                        buscarOperacionesDB.setVisibility(View.VISIBLE);
                                        contenedorRecycler.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.VISIBLE);
                                        spinnerFiltrarDinamico.setVisibility(View.VISIBLE);

                                        //----------------------------------

                                        //-------desaparece la tarjeta-------------------//

                                        cantidadSublotesEditText.setVisibility(View.GONE);
                                        linearLayoutFicha.setVisibility(View.GONE);
                                        botonDividirLote.setVisibility(View.GONE);
                                        botonEditarCantidad.setVisibility(View.GONE);
                                        botonPartirPartesIguales.setVisibility(View.GONE);



                                        Toast.makeText(operaciones_lotes.this, "Lote dividido exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Manejar el caso en que la cadena esté vacía
                                    Toast.makeText(operaciones_lotes.this, "La cantidad de sublotes no puede estar vacía", Toast.LENGTH_SHORT).show();
                                }


                                Handler handler = new Handler();
                                //finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Llama al primer método aquí
                                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc);


                                    }
                                }, 1000); // Retraso de 5000 milisegundos (5 segundos)
                            }
                        });
                        botonImagenEditar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LayoutImageButtonEditar.setVisibility(View.GONE);
                                LayoutImageButtonEditar.setVisibility(View.GONE);
                                botonDividirLote.setVisibility(View.GONE);
                                LayoutImageButtondividirManual.setVisibility(View.GONE);
                                LayoutImageButtondividirAutomatico.setVisibility(View.GONE);
                                botonDividirManual.setVisibility(View.GONE);
                                layoutBotonCopiar.setVisibility(View.GONE);
                                spinnerFiltrarDinamico.setVisibility(View.GONE);

                                cantidadSublotesEditText.setHint("Cantidad");
                                cantidadSublotesEditText.setVisibility(View.VISIBLE);
                                //botonEditarCantidad.setVisibility(View.VISIBLE);

                                Button botoneditCant=findViewById(R.id.editarcant);
                                botoneditCant.setVisibility(View.VISIBLE);
                                botoneditCant.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String URL= "http://khushiconfecciones.com//app_khushi/consultas_lotes/editar_cantidad.php";
                                        int cantidadasignada= Integer.parseInt(cantidadSublotesEditText.getText().toString());
                                        editarCantidadOperacion(URL,asignacion.getId_lotes_operaciones(),cantidadasignada);



                                        Handler handler = new Handler();

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Llama al primer método aquí
                                                agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_por_lote.php?id_producto_orden_compra="+finalVariableRecibida_idproducto_oc);


                                            }
                                        }, 1000); // Retraso de 5000 milisegundos (5 segundos)

                                        botoneditCant.setVisibility(View.GONE);
                                        cantidadSublotesEditText.setVisibility(View.GONE);


                                        //esconde el recycler
                                        spinnerFiltrar.setVisibility(View.VISIBLE);
                                        buscarOperacionesDB.setVisibility(View.VISIBLE);
                                        contenedorRecycler.setVisibility(View.VISIBLE);
                                        spinnerFiltrarDinamico.setVisibility(View.VISIBLE);
                                        linearLayoutFicha.setVisibility(View.GONE);
                                        // recycler.setVisibility(View.GONE);







                                    }
                                });



                            }
                        });
                        botonImagenEditar.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Toast.makeText(operaciones_lotes.this, "Editar cantidad", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });






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

    private void editarCantidadOperacion(String URL, int idLotesOperaciones, int cantidad) {


            // Crear una solicitud de cadena (StringRequest) con un método POST
            //holidfd
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

                   // Toast.makeText(operaciones_lotes.this, error.toString(),Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                    // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                    //String URL, int idLotesOperaciones, int cantidad

                    Map<String, String> parametros= new HashMap<String, String>();
                    parametros.put("id_lotes_operaciones", String.valueOf(idLotesOperaciones));
                    parametros.put("cantidad", String.valueOf(cantidad));



                    return parametros;
                }
            };

            // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



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


    public void  agregarEmpleado (String URL, int idEmpleado, String Empleado, String id_operacion){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", URL+ " "+Empleado+id_operacion);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                //Toast.makeText(agregarProducto.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();


                parametros.put("empleado", String.valueOf(idEmpleado));

                parametros.put("id_lotes_operaciones", String.valueOf(id_operacion));





                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);

    }

    private void eliminarOperacion (String URL){
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

                Toast.makeText(operaciones_lotes.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_lotes_operaciones", String.valueOf(idParaUpdate));


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    private void  agregarOperacion (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(operaciones_lotes.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(operaciones_lotes.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();
               /* productTextView.setText(asignacion.getProducto());
                sectionTextView.setText(asignacion.getSubparte());
                operationTextView.setText(asignacion.getOperaciones());
                quantityTextView.setText(String.valueOf(asignacion.getCantidad()));
                nameTextView.setText(asignacion.getNombre());
                lastNameTextView.setText(asignacion.getApellido());*/

               // parametros.put("id_producto_subparte_operacion",producto.getText().toString())



                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void dividirOperacion (String URL, int id_producto_subparte_operacion, int id_producto_oc, int cantidad, int lote){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        //holidfd
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Toast.makeText(operaciones_lotes.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                Toast.makeText(operaciones_lotes.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"

                //int id_lotes_operaciones, int id_producto_subparte_operacion,int id_producto_oc,int cantidad, int lote

                Map<String, String> parametros= new HashMap<String, String>();

               // parametros.put("id_lotes_operaciones", String.valueOf(idParaUpdate));
                parametros.put("id_producto_subparte_operacion",String.valueOf(id_producto_subparte_operacion));
                parametros.put("id_producto_oc",String.valueOf(id_producto_oc));
                parametros.put("cantidad", String.valueOf(cantidad));
                parametros.put("lotes", String.valueOf(lote));

                Log.i("d",String.valueOf(id_producto_subparte_operacion)+"/"+String.valueOf(id_producto_oc)+"/"+String.valueOf(cantidad)+"/"+String.valueOf(lote));



                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public boolean onQueryTextChange(String newText) {


        String seleccion= spinnerFiltrarDinamico.getSelectedItem().toString();
        if (seleccion.equals("Todos los filtros")){
            adapter123.filtrado(newText);
        }if (seleccion.equals("Todos los filtros")){
            adapter123.filtrado(newText);
        }else if (seleccion.equals("Habilitado")){
            adapter123.filtradoHabilitado(newText);
        }else if(seleccion.equals("producto")){
            adapter123.filtradoProducto(newText);
        }else if (seleccion.equals("Seccion")){
            adapter123.filtradoSeccion(newText);
        }else if (seleccion.equals("Operaciones")){
            adapter123.filtradoOperacion(newText);
        } else if (seleccion.equals("Cantidad")) {
            adapter123.filtradoCantidad(newText);
        }else if (seleccion.equals("Nombre")) {
            adapter123.filtradoNombre(newText);
        }else if (seleccion.equals("Apellido")) {
            adapter123.filtradoApellido(newText);
        }else if (seleccion.equals("Lote")) {
            adapter123.filtradoLote(newText);
        }
        else if (seleccion.equals("completado")) {
            adapter123.filtradoCompletado(newText);
        }



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
            Intent intent = new Intent(operaciones_lotes.this, Home.class);
            // Agregar las banderas FLAG_CLEAR_TOP y FLAG_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);            startActivity(intent);
            startActivity(intent);
            finish(); // Cierra la actividad actual
            return true;  // Importante agregar esta línea para indicar que el evento ha sido manejado

        } else if (id == R.id.fragmento2) {
            // Lanzar la Activity correspondiente al fragmento2
            //Intent intentFragmento2 = new Intent(this, Home.class);
            //startActivity(intentFragmento2);
            Toast.makeText(this, "no disponible", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.fragmento3) {
            // Lanzar la Activity correspondiente al fragmento2
            //Intent intentFragmento2 = new Intent(this, Home.class);
            //startActivity(intentFragmento2);
            Toast.makeText(this, "no disponible", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}


