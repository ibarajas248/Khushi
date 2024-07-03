package com.example.khushi.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.R;

import java.util.HashMap;
import java.util.Map;import android.content.Context;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentModificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentModificar extends Fragment {

    RequestQueue requestQueue; // Declaración de la cola de solicitudes de Volley

    // Declaración de una interfaz para manejar eventos de clic en botones
    private OnButtonClickListener mListener;
    // Declara una variable para almacenar el contexto de la actividad

    // Declaración de una variable para almacenar el contexto de la actividad
    private Context mContext;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // Constantes para los nombres de los parámetros de inicialización del fragmento
    private static final String ARG_PARAM1 = "producto";
    private static final String ARG_PARAM2 = "subparte";
    private static final String ARG_PARAM3 = "operaciones";
    private static final String ARG_PARAM4 = "maquina";
    private static final String ARG_PARAM5 = "cantidad";
    private static final String ARG_PARAM6 = "precio";
    private static final String ARG_PARAM7 = "id_precio";
    private static final String ARG_PARAM8 = "id_asignacion_operacion";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    String producto;
    String subparte;
    String operaciones;
    String maquina;
    float cantidad;
    float precio;
    int id_precio;
    int id_asignacion_operacion;





    EditText etProducto, etSubparte, etOperaciones, etMaquina, etCantidad, etPrecio, etId_precio;


    public FragmentModificar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentModificar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentModificar newInstance(String param1, String param2) {
        FragmentModificar fragment = new FragmentModificar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    /**
     * Método factory para crear una nueva instancia del fragmento con todos los parámetros.
     */

    public static FragmentModificar newInstance(String producto, String subparte,String operaciones, String maquina, float cantidad,float precio, int id_precio,int id_asignacion_operacion) {
        FragmentModificar fragment = new FragmentModificar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, producto);
        args.putString(ARG_PARAM2, subparte);
        args.putString(ARG_PARAM3, operaciones);
        args.putString(ARG_PARAM4, maquina);
        args.putString(ARG_PARAM5, String.valueOf(cantidad));
        args.putString(ARG_PARAM6, String.valueOf(precio));
        args.putString(ARG_PARAM7, String.valueOf(id_precio));
        args.putString(ARG_PARAM8, String.valueOf(id_asignacion_operacion));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            // Obtención de los valores de los argumentos y asignación a las variables correspondientes
            producto = getArguments().getString(ARG_PARAM1);
            subparte = getArguments().getString(ARG_PARAM2);
           operaciones = getArguments().getString(ARG_PARAM3);
            maquina = getArguments().getString(ARG_PARAM4);
            cantidad = Float.parseFloat(getArguments().getString(ARG_PARAM5));
            precio = Float.parseFloat(getArguments().getString(ARG_PARAM6));
           id_precio = Integer.parseInt(getArguments().getString(ARG_PARAM7));
            id_asignacion_operacion = Integer.parseInt(getArguments().getString(ARG_PARAM8));

           // Toast.makeText(getActivity(), String.valueOf(id_precio), Toast.LENGTH_SHORT).show();






            //EditText etProducto, etSubparte, etOperaciones, etMaquina, etCantidadm, etPrecio, etId_precio;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_modificar, container, false);
        /*if (getActivity() != null) {
            // Aquí puedes utilizar getActivity() de forma segura
            Toast.makeText(getActivity(), "Mensaje de ejemplo "+String.valueOf(id_asignacion_operacion), Toast.LENGTH_SHORT).show();
        }*/
        // Encontrar referencias de los EditText en el layout inflado
        etProducto = rootView.findViewById(R.id.producto);
        etSubparte = rootView.findViewById(R.id.subparte);
        etOperaciones = rootView.findViewById(R.id.operaciones);
        etMaquina = rootView.findViewById(R.id.maquina);
        etCantidad = rootView.findViewById(R.id.cantidad);
        etPrecio = rootView.findViewById(R.id.precio);
        Button botoModifi = rootView.findViewById(R.id.buttonModificarPrecio);
        //etId_precio = rootView.findViewById(R.id.id_precio);

        // Configurar los valores recibidos en los EditText
        etProducto.setText(producto);
        etSubparte.setText(subparte);
        etOperaciones.setText(operaciones);
        etMaquina.setText(maquina);
        etCantidad.setText(String.valueOf(cantidad));
        etPrecio.setText(String.valueOf(precio));
       // etIdPrecio.setText(String.valueOf(id_precio));



        // Devolver la vista del layout inflado



        Button botonModificar= rootView.findViewById(R.id.buttonModificarPrecio);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String URL="http://khushiconfecciones.com//app_khushi/producto/update_precio.php";
                modificarPrecio(URL, id_precio,precio);


                // Llama al método de la interfaz
                if (mListener != null) {
                    mListener.onButtonClicked();
                }


                //ce cierra el fragment
                getFragmentManager().beginTransaction().remove(FragmentModificar.this).commit();


            }
        });

        Button botonEliminar= rootView.findViewById(R.id.buttonEliminarOperacion);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL="http://khushiconfecciones.com//app_khushi/producto/eliminar_operacion_producto.php";
                eliminaroperacion(URL);

                if (mListener != null) {
                    mListener.onButtonClicked();
                }


                //ce cierra el fragment
                getFragmentManager().beginTransaction().remove(FragmentModificar.this).commit();


            }
        });

        return rootView;
    }

    private void modificarPrecio(String URL, int idPrecio, float precio) {
        // Crear una solicitud de cadena (StringRequest) con un método POST
        //holidfd
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Verifica si mContext es nulo para evitar NullPointerException
                if (mContext != null) {
                    Toast.makeText(mContext, "Operación existosa", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("precio",etPrecio.getText().toString());
                parametros.put("id_precio", String.valueOf(id_precio));
                //parametros.put("producto",edtProducto.getText().toString());
                //parametros.put("precio",edtprecio.getText().toString());
                //parametros.put("fabricante",edtFabricante.getText().toString());

                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        Context context = getActivity();
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void eliminaroperacion (String URL){
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


                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id", String.valueOf(id_asignacion_operacion));


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor

        Context context = getActivity();
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    /*El método onAttach() es un método del ciclo de vida de un fragmento en Android que se llama
    cuando el fragmento se adjunta a su actividad contenedora. Se utiliza para realizar tareas de
    inicialización que requieran acceso al contexto de la actividad.*/
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnButtonClickListener) {
            mListener = (OnButtonClickListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//interfaz en el fragmento que contenga el método que deseo llamar en la actividad
    public interface OnButtonClickListener {
        void onButtonClicked();
    }

}