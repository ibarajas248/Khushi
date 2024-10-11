package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        // 1. Inicializar el RecyclerView
        recyclerViewEmpleados = findViewById(R.id.recyclerViewEmpleados);
        recyclerViewEmpleados.setLayoutManager(new LinearLayoutManager(this)); // Establecer el LayoutManager
        searchView = findViewById(R.id.buscar);
        searchView.setOnQueryTextListener(this);
        spinner = findViewById(R.id.spinner3);
        seleccionados = new ArrayList<>();

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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                String query = searchView.getQuery().toString();

                // Solo agregar al array auxiliar si no es la opción predeterminada
                if (!selectedOption.equals("Seleccione una opción")) {
                    //seleccionados.add(selectedOption);
                    List<String> criterios = new ArrayList<>();
                    criterios.add("Nombre");
                    criterios.add("Apellido");

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
        aplicarFiltros(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapterEmpleados.filtrado(newText);
        //aplicarFiltros(newText);
        aplicarFiltrado();
        return false;

    }





    private void aplicarFiltros(String query) {
        List<Filtro> filtrosSeleccionados = new ArrayList<>();

        // Si el texto ingresado no está vacío, creamos filtros para cada campo que se quiera buscar
        if (!query.isEmpty()) {
            // Asumiendo que quieres buscar por todos los campos: nombre, apellido y correo
            filtrosSeleccionados.add(new Filtro("nombre", query));
            filtrosSeleccionados.add(new Filtro("apellido", query));
            filtrosSeleccionados.add(new Filtro("correo", query));
        }

        // Aplicar los filtros en el adaptador
        adapterEmpleados.filtrado(filtrosSeleccionados);
    }
    private void aplicarFiltrado() {
        String textoBuscar = searchView.getQuery().toString();
        List<String> criterios = new ArrayList<>();


            criterios.add("Nombre");



            criterios.add("Apellido");


            criterios.add("Correo");

        adapterEmpleados.filtrado234(textoBuscar,criterios);


    }


}