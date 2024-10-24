package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import androidx.appcompat.app.AlertDialog;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.AdaptadoresRecycler.Adapter_empleados;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.Filtro;
import com.example.khushi.clasesinfo.nuevoProducto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class empleados extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewEmpleados;
    private Adapter_empleados adapterEmpleados;
    private ArrayList<Empleado_clase> listaEmpleados;
    private RequestQueue requestQueue;
    SearchView searchView;
    Spinner spinner;
    private List<String> seleccionados;
    EditText editTextSearch;

    List<String> criterios; //array que contiene los filtros
    TextView filtros, filtrosApellidos, filtrosCorreo;





    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        // 1. Inicializar el RecyclerView
        recyclerViewEmpleados = findViewById(R.id.recyclerViewEmpleados);
        recyclerViewEmpleados.setLayoutManager(new LinearLayoutManager(this)); // Establecer el LayoutManager
        searchView = findViewById(R.id.buscar);
        searchView.setOnQueryTextListener(this);
        editTextSearch = findViewById(R.id.edtBuscar);
        spinner = findViewById(R.id.spinner3);
        seleccionados = new ArrayList<>();
        criterios = new ArrayList<>();//inicializo el array de
        filtros= findViewById(R.id.tvfiltros);
        filtrosApellidos= findViewById(R.id.Apellidos);
        filtrosCorreo= findViewById(R.id.Correo);


        // Define las opciones del Spinner
        String[] opciones = {"Seleccione una opción","Nombre", "Apellido", "Correo"};

        // Crea un ArrayAdapter usando las opciones y un diseño de layout de spinner
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);

        // Especifica el layout que se utilizará cuando se despliegue la lista de opciones
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        spinner.setAdapter(adapter_spinner);




        // 2. Crear la lista de empleados (esto sería normalmente de una base de datos o API)
        listaEmpleados = new ArrayList<>();
        /*listaEmpleados.add(new Empleado_clase("Juan", "Pérez"));
        listaEmpleados.add(new Empleado_clase("Ana", "López"));
        listaEmpleados.add(new Empleado_clase("Carlos", "Gómez"));
        listaEmpleados.add(new Empleado_clase("María", "Fernández"));*/

        // 3. Inicializar el adaptador con la lista de empleados
        adapterEmpleados = new Adapter_empleados(listaEmpleados);

        // 4. Asignar el adaptador al RecyclerView
        recyclerViewEmpleados.setAdapter(adapterEmpleados);
        // Inicializar la cola de solicitudes Volley
        requestQueue = Volley.newRequestQueue(this);
        obtenerEmpleados("http://khushiconfecciones.com//app_khushi/empleados_lista.php");
        editTextSearch.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawableEnd = editTextSearch.getCompoundDrawables()[2]; // DrawableEnd es el tercero (índice 2)
                    if (drawableEnd != null) {
                        if (event.getRawX() >= (editTextSearch.getRight() - drawableEnd.getBounds().width())) {
                            // Ejecuta la acción que desees
                            mostrarFiltros();
                            Toast.makeText(getApplicationContext(), "DrawableEnd presionado", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Aquí puedes implementar tu lógica de filtrado en tiempo real
                //aplicarFiltrado(s.toString());
                aplicarFiltrado();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No hacer nada aquí
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                String query = searchView.getQuery().toString();

                // Solo agregar al array auxiliar si no es la opción predeterminada
                if (!selectedOption.equals("Seleccione una opción")) {
                    //seleccionados.add(selectedOption);
                    List<String> criterios_dos = new ArrayList<>();
                    criterios_dos.add("Nombre");
                    criterios_dos.add("Apellido");

                    criterios.add(selectedOption);
                    Toast.makeText(empleados.this, selectedOption, Toast.LENGTH_SHORT).show();
                    filtros.setText(selectedOption);

                    //Toast.makeText(empleados.this, "Array auxiliar: " + seleccionados.toString(), Toast.LENGTH_SHORT).show();
                    //adapterEmpleados.filtrado(query, selectedOption);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Opcionalmente puedes manejar este caso

            }
        });


    }

    private void obtenerEmpleados(String URL) {
        //aLodingDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                listaEmpleados.clear(); // Limpiar la lista existente


                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        String id =(jsonObject.getString("id"));
                        String nombre =(jsonObject.getString("nombre"));
                        String Apellidos =(jsonObject.getString("Apellidos"));
                        String correo_electronico =(jsonObject.getString("correo_electronico"));


                        //String url="http://khushiconfecciones.com//app_khushi/imagenes/carpeta_imagenes/Intel_logo_(2006-2020).jpg";
                        listaEmpleados.add(new Empleado_clase(id,nombre,Apellidos,correo_electronico));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapterEmpleados = new Adapter_empleados(listaEmpleados);


                adapterEmpleados.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(empleados.this, "hola", Toast.LENGTH_SHORT).show();


                    }
                });


                recyclerViewEmpleados.setAdapter(adapterEmpleados);
                adapterEmpleados.notifyDataSetChanged(); // Notificar cambios en el adaptador
                //aLodingDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                //isMethodRunning = true; // reiniciar la bandera en
                TextView vacio=findViewById(R.id.vacio);
                vacio.setVisibility(View.VISIBLE);
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //aplicarFiltros(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapterEmpleados.filtrado(newText);
        //aplicarFiltros(newText);
        aplicarFiltrado();
        return false;

    }






    private void aplicarFiltrado() {
        //String textoBuscar = searchView.getQuery().toString();
        String textoBuscar = editTextSearch.getText().toString();



            //criterios.add("Nombre");



            //.add("Apellido");


            //criterios.add("Correo");

        adapterEmpleados.filtrado234(textoBuscar,criterios);


    }
    public void mostrarFiltros() {
        // Lista de filtros disponibles
        final String[] filtros = {"Nombre", "Apellido", "Correo", "Filtro 4"};
        // Array booleano para almacenar la selección de filtros
        final boolean[] filtrosSeleccionados = new boolean[filtros.length];
        // Lista para almacenar los filtros seleccionados
        final List<String> filtrosAplicados = new ArrayList<>();

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona los filtros");

        // Configurar la selección múltiple de checkboxes
        builder.setMultiChoiceItems(filtros, filtrosSeleccionados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Agregar o quitar el filtro de la lista según la selección
                if (isChecked) {
                    //filtrosAplicados.add(filtros[which]);
                    criterios.add(filtros[which]);





                } else {
                    //filtrosAplicados.remove(filtros[which]);
                    criterios.remove(filtros[which]);
                }
            }
        });

        // Botón "Aceptar" para procesar los filtros seleccionados
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Llamar al método que aplica los filtros seleccionados
                //aplicarFiltros(filtrosAplicados);
                aplicarFiltros(criterios);
            }
        });

        // Botón "Cancelar" para cerrar el diálogo sin aplicar cambios
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el diálogo
                dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        builder.create().show();
    }

    // Método para aplicar los filtros seleccionados
    private void aplicarFiltros(List<String> filtrosSeleccionados) {
        // Aquí puedes procesar los filtros aplicados
        if (!filtrosSeleccionados.isEmpty()) {
            for (String filtro : filtrosSeleccionados) {
                // Procesa cada filtro seleccionado, por ejemplo:
                Toast.makeText(this, "Filtro aplicado: " + filtro, Toast.LENGTH_SHORT).show();
                //aca agrego los filtros al buscardor
                if (filtro.equalsIgnoreCase("Nombre")) {
                    // Acción cuando se encuentra "nombre"

                    filtros.setVisibility(View.VISIBLE);


                }
                if (filtro.equalsIgnoreCase("Apellido")) {
                    // Acción cuando se encuentra "nombre"

                    filtrosApellidos.setVisibility(View.VISIBLE);

                }
                if (filtro.equalsIgnoreCase("Correo")) {
                    // Acción cuando se encuentra "nombre"

                    filtrosCorreo.setVisibility(View.VISIBLE);

                }


            }
        } else {
            Toast.makeText(this, "No se seleccionó ningún filtro", Toast.LENGTH_SHORT).show();
        }
    }





}