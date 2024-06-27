package com.example.khushi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.khushi.Activity.Agregar_operaciones_a_producto;
import com.example.khushi.Activity.mostrar_agregar_subparte;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaSubParte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_agregar_operacion_desde_op#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_agregar_operacion_desde_op extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_id_producto="id_producto";
    private static final String ARG_producto="producto";




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RequestQueue queue;
    private int idSubparteSeleccionada; //id cuando seleccion subpñarte del Spinner

    private String subparteSeleccionada;
    private ArrayList<nuevaSubParte> listsubparte = new ArrayList<>();
    private ArrayList<nuevaSubParte> listSubparteSpinner = new ArrayList<>();
    private Spinner spinnersubparte;

    //valores para agregar operacion
    String operacion, maquina;
    int cantidad;

    int idOperacionAgregado;//busca el ultimo registro ingresado en operacion

    EditText etOperacion,etCantidad,etMaquina;
    Button agregarOperacion;







    public Fragment_agregar_operacion_desde_op() {
        // Required empty public constructor
    }
    public Fragment_agregar_operacion_desde_op(int id_producto) {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_agregar_operacion_desde_op.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_agregar_operacion_desde_op newInstance(String param1, String param2) {
        Fragment_agregar_operacion_desde_op fragment = new Fragment_agregar_operacion_desde_op();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment_agregar_operacion_desde_op newInstance(int id_Producto, String operacion) {
        Fragment_agregar_operacion_desde_op fragment = new Fragment_agregar_operacion_desde_op();
        Bundle args = new Bundle();
        args.putString(ARG_id_producto, String.valueOf(id_Producto));
        args.putString(ARG_producto,operacion);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            listsubparte= new ArrayList<nuevaSubParte>();
            listSubparteSpinner= new ArrayList<nuevaSubParte>();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_operacion_desde_op, container, false);

        // Initialize the Spinner
        spinnersubparte = view.findViewById(R.id.spinnerSubparte);
        queue = Volley.newRequestQueue(requireContext());
        Spinnersubparte("http://khushiconfecciones.com/app_khushi/spinner_subparte.php");

        etOperacion=view.findViewById(R.id.operacion);
        etCantidad=view.findViewById(R.id.cantidad);
        etMaquina=view.findViewById(R.id.Maquina);

        operacion=etOperacion.getText().toString();
       // cantidad= Integer.parseInt(etCantidad.getText().toString());
        maquina=etMaquina.getText().toString();

        agregarOperacion=view.findViewById(R.id.buttonagregaroperacion_OC);

        agregarOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarOperacion( operacion,cantidad,maquina);

            }
        });

        return view;

    }

    private void ingresarOperacion(String operacion, int cantidad, String maquina) {
        agregarOperacion("http://khushiconfecciones.com//app_khushi/agregar_operaciones.php");

        //espera 3 segundos y luego busca el id del ultimo registro
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                obtenerUltimaOperacion();

            }
        }, 3000); // 6000 milisegundos = 6 segundos

    }

    //trae los valores desde el Spinner
    private void Spinnersubparte (String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
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
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                //AdapterDatos adapter123 = new AdapterDatos(listSubparteSpinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_filtrar_en_lotes_operaciones, nombresSubpartes);

                spinnersubparte.setAdapter(adapter); // Establecer el adaptador en el Spinner


                spinnersubparte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Obtener el ID seleccionado usando la posición en el ArrayList de IDs
                        idSubparteSeleccionada = idSubpartes.get(position);
                        subparteSeleccionada=nombresSubpartes.get(position);
                        //registrarSubproducto.setVisibility(View.VISIBLE);


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
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(), "Se ha ingresado el producto", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //mensaje si no se registra el producto satisfactoriamente
                Toast.makeText(getContext(), "No se ha podido ingresar el producto " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"


                Map<String, String> parametros = new HashMap<String, String>();
                // parametros.put("id_producto", String.valueOf(idproducto));
                //parametros.put("id_subparte",String.valueOf(idsubparte));
                parametros.put("operaciones", etOperacion.getText().toString());
                parametros.put("cantidad", etCantidad.getText().toString());
                parametros.put("maquina", etMaquina.getText().toString());

                /*if (visibilidadModificar==true){
                    parametros.put("id_producto",String.valueOf(idProducto));
                }*/


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    private void obtenerUltimaOperacion() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://khushiconfecciones.com//app_khushi/buscar_ultima_operacion.php"; // Reemplaza con tu URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Busca el último registro y asigna el id
                            idOperacionAgregado = Integer.parseInt(response.getString("id_operaciones"));
                            //rastrear el producto vinculados a ordenes de compra
                            //buscarProductoenOC (URLBuscarProductoEnOC+idproducto);
                            agregarPrecio("http://khushiconfecciones.com//app_khushi/insertar_precio_operacion.php");
                            agregarOperacion_Producto("http://khushiconfecciones.com//app_khushi/insert_operacion_a_producto.php");

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
    private void agregarPrecio(String URL) {
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(getContext(), "Precio asignado", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error
                //ff

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
                parametros.put("id_operacion", String.valueOf(idOperacionAgregado));
                parametros.put("id_subparte", String.valueOf(idSubparteSeleccionada));
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
}