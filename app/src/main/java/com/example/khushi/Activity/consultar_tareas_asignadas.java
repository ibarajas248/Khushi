package com.example.khushi.Activity;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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
import com.example.khushi.AdaptadoresRecycler.Adapter_Operaciones_Habilitadas;
import com.example.khushi.AdaptadoresRecycler.Adapter_consulta_tareas_asignadas;
import com.example.khushi.AdaptadoresRecycler.Adapter_consultar_tareas;
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_lotes;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.Usuario;
import com.example.khushi.clasesinfo.nuevoProducto;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class consultar_tareas_asignadas extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ArrayList<Empleado_clase> listaEmpleados;
    ArrayList<operaciones_lotes_clase> listOperaciones;
    ArrayList<operaciones_lotes_clase> listOperacionesHabilitadas;
    RecyclerView recycler,recyclerHabilitado;
    Adapter_consulta_tareas_asignadas adapter123;
    Adapter_Operaciones_Habilitadas adapterHabilitada;

    //boton para completar una operacion
    Button botonCompletaOperacion;
    ArrayList<String> nombres;
    ArrayList<String>listEmpleados;
    RequestQueue queue;
    ArrayList<Integer> idsEmpleados;
    ArrayAdapter<String> adapter;// adapter que va a tomar el spinner
    Spinner spinnerEmpleado,spinnerFiltrar;
    int idParaUpdate;
//variable para almacenar el id de la tarea asignada
    private int Operacion_asignada;

    SearchView buscarOperacionesDB;

    HorizontalScrollView Contenedor_Recycler;
//variable para tarjeta cuando se selecciona una operacion
    LinearLayout linearLayout;
    TextView productTextView, sectionTextView, operationTextView, quantityTextView, nameTextView, lastNameTextView;

    //------------
    private String ROL, idEmpleado, operaciones_completadas; //variable que recibe del intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tareas_asignadas);


        //valores que recibe del anterior intent
        Intent intent = getIntent();
        ROL = intent.getStringExtra("Rol");
        idEmpleado= intent.getStringExtra("idEmpleado");
        operaciones_completadas=intent.getStringExtra("operaciones_completadas");


        //---------fin de valores recibidos en el intent

        // inicializo el searchview---
        buscarOperacionesDB=(SearchView)findViewById(R.id.searchoperacionesDB);
        buscarOperacionesDB.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        //-----------

        spinnerFiltrar=(Spinner)findViewById(R.id.spinnerFiltrar);
        spinnerEmpleado = findViewById(R.id.spinnerFiltrar_empleado);
        botonCompletaOperacion=(Button)findViewById(R.id.boton_completar_operacion);

        recycler = (RecyclerView) findViewById(R.id.recyclerviewoperaciones);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerHabilitado=(RecyclerView)findViewById(R.id.recyclerviewoperacionesHabilitadas);
        recyclerHabilitado.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        queue = Volley.newRequestQueue(this);
        Handler handler = new Handler();
        listOperaciones = new ArrayList<operaciones_lotes_clase>();
        listOperacionesHabilitadas = new ArrayList<operaciones_lotes_clase>();


        // array para opciones del Spinner
        String[]opcionesFiltrado={"seleccione", "Sin Asignar", "Tareas Asignadas"};

        //Arrayadapter del Spinner

        ArrayAdapter<String> adapterSpinner= new ArrayAdapter<String>(this,
                R.layout.spinner_filtrar_en_lotes_operaciones, opcionesFiltrado);

        spinnerFiltrar.setAdapter(adapterSpinner);




        if (ROL.equalsIgnoreCase("OPERARIO")){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Llama al primer método aquí

                  if (operaciones_completadas.equalsIgnoreCase("no")){
                      agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_todas_tareas_asignadas.php?empleado="+idEmpleado);
                      agregarListaOperacion_Habilitada("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_habilitadas.php?empleado="+idEmpleado);

                  }else if(operaciones_completadas.equalsIgnoreCase("si")){
                      agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_asignadas_completadas.php?empleado="+idEmpleado);

                  }


            }
        }, 1000); // Retraso de 5000 milisegundos (5 segundos)

    }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Llama al primer método aquí
                    if (operaciones_completadas.equalsIgnoreCase("no")) {
                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_todas_tareas_asignadas.php");
                        agregarListaOperacion_Habilitada("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_operaciones_habilitadas.php");


                    }else if(operaciones_completadas.equalsIgnoreCase("si")){
                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_asignadas_completadas.php");
                    };

                }
            }, 1000); // Retraso de 5000 milisegundos (5 segundos)

        }




        //Arrays para extraer informacion del spinner empleados
        listaEmpleados = new ArrayList<>();
        nombres = new ArrayList<>();
        idsEmpleados = new ArrayList<>();
        listEmpleados = new ArrayList<>();


        //Scroll del contenedor de operaciones

        Contenedor_Recycler= (HorizontalScrollView) findViewById(R.id.ContenedorRecycler);

        // ------------ inicializo los texview de la tarjeta de la operacion realizada
        linearLayout = findViewById(R.id.llContainer);
        productTextView = findViewById(R.id.tvProduct);
        sectionTextView = findViewById(R.id.tvSection);
        operationTextView = findViewById(R.id.tvOperation);
        quantityTextView = findViewById(R.id.tvQuantity);
        nameTextView = findViewById(R.id.tvName);
        lastNameTextView = findViewById(R.id.tvLastName);


        //-----------------------------------------------------------------------------


        llenarListaSpinner("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_empleados_spinner.php");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nombres);
        spinnerEmpleado.setAdapter(spinnerAdapter);

        spinnerEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT).show();
                Log.d("ItemSelected", "Elemento seleccionado en posición " + position);
                Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonCompletaOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mostrarAlertDialog();

            }
        });
    }




    public void agregarListaOperacion_Habilitada(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listOperacionesHabilitadas.clear(); // Limpiar la lista existente



                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);

                        String producto = jsonObject.getString("producto");
                        String subparte = jsonObject.getString("subparte");
                        String operaciones = jsonObject.getString("operaciones");
                        int id_lotes_operaciones= Integer.parseInt(jsonObject.getString("id_lotes_operaciones"));
                        int cantidad= Integer.parseInt(jsonObject.getString("cantidad"));
                        int id_producto_oc = (Integer.parseInt(jsonObject.getString("id_producto_orden_compra")));
                        String StringEmpleado=jsonObject.getString("empleado");
                        String nombreEmpleado=jsonObject.getString("nombre");
                        String apellidoEmpleado=jsonObject.getString("Apellidos");
                        int lote= Integer.parseInt(jsonObject.getString("lote"));
                        String completado= jsonObject.getString("completado");
                        String habilitado=jsonObject.getString("habilitado");
                        String fecha=jsonObject.getString("fecha");




                        String empleado=StringEmpleado;


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

                        listOperacionesHabilitadas.add(new operaciones_lotes_clase(producto,subparte,operaciones,id_lotes_operaciones,cantidad,empleado,id_operaciones_subparte_producto,nombreEmpleado,apellidoEmpleado,lote,id_producto_oc,completado,habilitado,fecha));



                        //hacer que el Spinner se llene con una consulta sql a la tabla de empleados...llamar un metedo
                        listEmpleados.add(String.valueOf(empleado));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapterHabilitada = new Adapter_Operaciones_Habilitadas(listOperacionesHabilitadas);

                adapterHabilitada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(consultar_tareas_asignadas.this, "clcik", Toast.LENGTH_SHORT).show();
                        //mostrarAlertDialog();




                    }
                });

                adapterHabilitada.setOnItemLongClickListener(new Adapter_Operaciones_Habilitadas.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(operaciones_lotes_clase asignacion) {
                        //asignacion.getNombre(); --- asi es como obtengo los valores estos los debo pasar al metodo
                        mostrarAlertDialog();
                    }
                });





                recyclerHabilitado.setAdapter(adapterHabilitada);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonArrayRequest);
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

                for (int i = 0; i < nombres.size(); i++) {
                    Log.d("ImprimirEnLog", "Elemento " + i + ": " + nombres.get(i)+"/////"+idsEmpleados.get(i));
                }
                for (int i = 0; i < idsEmpleados.size(); i++) {
                    Log.d("ImprimirEnLog", "Elemento " + i + ": " + idsEmpleados.get(i));
                }
                Log.d("RespuestaJSON", response.toString());

                //AdapterDatos adapter123 = new AdapterDatos(listEmpleados);

                adapter = new ArrayAdapter<>(consultar_tareas_asignadas.this, android.R.layout.simple_list_item_1, nombres);


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
                        int id_producto_oc = (Integer.parseInt(jsonObject.getString("id_producto_orden_compra")));
                        String StringEmpleado=jsonObject.getString("empleado");
                        String nombreEmpleado=jsonObject.getString("nombre");
                        String apellidoEmpleado=jsonObject.getString("Apellidos");
                        int lote= Integer.parseInt(jsonObject.getString("lote"));
                        String completado= jsonObject.getString("completado");



                        String empleado=StringEmpleado;


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

                        listOperaciones.add(new operaciones_lotes_clase(producto,subparte,operaciones,id_lotes_operaciones,cantidad,empleado,id_operaciones_subparte_producto,nombreEmpleado,apellidoEmpleado,lote,id_producto_oc,completado));



                        //hacer que el Spinner se llene con una consulta sql a la tabla de empleados...llamar un metedo
                        listEmpleados.add(String.valueOf(empleado));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter123 = new Adapter_consulta_tareas_asignadas(listOperaciones);

                adapter123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(consultar_tareas_asignadas.this, "clcik", Toast.LENGTH_SHORT).show();


                    }
                });

                adapter123.setOnItemLongClickListener(new Adapter_consulta_tareas_asignadas.OnItemLongClickListener(){

                    @Override
                    public void onItemLongClick(operaciones_lotes_clase asignacion) {
                       idParaUpdate= asignacion.getId_lotes_operaciones();

                        botonCompletaOperacion.setVisibility(View.VISIBLE);

                        //asigna los valores a la tarjeta
                        productTextView.setText(asignacion.getProducto());
                        sectionTextView.setText(asignacion.getSubparte());
                        operationTextView.setText(asignacion.getOperaciones());
                        quantityTextView.setText(String.valueOf(asignacion.getCantidad()));
                        nameTextView.setText(asignacion.getNombre());
                        lastNameTextView.setText(asignacion.getApellido());
                        //-------
                        //el recycler desaparece
                        recycler.setVisibility(View.GONE);
                        Contenedor_Recycler.setVisibility(View.GONE);
                        // la tarjeta se hace visible
                        linearLayout.setVisibility(View.VISIBLE);

                        //guardo el valor del id de la opracion adignada
                        Operacion_asignada= asignacion.getId_lotes_operaciones();




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


    private void  tareaCompletada (String URL, int id_lotes_operaciones){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);
                Toast.makeText(consultar_tareas_asignadas.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud

               Toast.makeText(consultar_tareas_asignadas.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
               Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_lotes_operaciones", String.valueOf(id_lotes_operaciones));

                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void  operacionCompletada (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(consultar_tareas_asignadas.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(consultar_tareas_asignadas.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"







                Map<String, String> parametros= new HashMap<String, String>();
               //envio la solicitud
                parametros.put("id_lotes_operaciones", String.valueOf(Operacion_asignada));



                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void mostrarAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Desea marcar como completada esta operación?");
        builder.setMessage("Está acción no se podrá modificar ");

        // Agregar botón positivo (por ejemplo, "Aceptar")
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar cuando se hace clic en el botón "Aceptar"

                //el recycler aparece

                Contenedor_Recycler.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.VISIBLE);
                // la tarjeta se hace invisible

                //vuelvo a cargar el Recycler
                linearLayout.setVisibility(View.GONE);
                botonCompletaOperacion.setVisibility(View.GONE);

                //esta  accion va despues del alertdialog
                tareaCompletada("http://khushiconfecciones.com//app_khushi/consultas_lotes/marcar_tarea_completada.php",idParaUpdate);
                operacionCompletada("http://khushiconfecciones.com//app_khushi/consultas_lotes/agregar_operacion_completada.php");
                botonCompletaOperacion.setVisibility(View.GONE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Llama al primer método aquí
                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_todas_tareas_asignadas.php");

                    }
                }, 3000); // Retraso de 5000 milisegundos (5 segundos)


                dialog.dismiss(); // Cierra el diálogo
            }
        });

        // Agregar botón negativo (por ejemplo, "Cancelar")
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar cuando se hace clic en el botón "Cancelar"

                //mostrar nuevamente el recycler, ocultar el boton y mostrar un  toast de que la op se hizo satisfactoriamente
                //tambien volver a cargar la lista de operaciones asignadas

                //el recycler aparece

                Contenedor_Recycler.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.VISIBLE);
                // la tarjeta se hace invisible

                //vuelvo a cargar el Recycler
                linearLayout.setVisibility(View.GONE);
                botonCompletaOperacion.setVisibility(View.GONE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Llama al primer método aquí
                        agregarListaOperacion_Lote("http://khushiconfecciones.com//app_khushi/consultas_lotes/buscar_todas_tareas_asignadas.php");

                    }
                }, 1000); // Retraso de 5000 milisegundos (5 segundos)

                dialog.dismiss(); // Cierra el diálogo
            }
        });

        // Mostrar el diálogo
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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