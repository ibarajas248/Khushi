package com.example.khushi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.Activity.mostrar_agregar_subparte;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaSubParte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RequestQueue queue;
    private int idSubparteSeleccionada; //id cuando seleccion subpñarte del Spinner

    private String subparteSeleccionada;
    private ArrayList<nuevaSubParte> listsubparte = new ArrayList<>();
    private ArrayList<nuevaSubParte> listSubparteSpinner = new ArrayList<>();
    private Spinner spinnersubparte;


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

    public static Fragment_agregar_operacion_desde_op newInstance(int id_Producto) {
        Fragment_agregar_operacion_desde_op fragment = new Fragment_agregar_operacion_desde_op();
        Bundle args = new Bundle();
        args.putString(ARG_id_producto, String.valueOf(id_Producto));

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

        return view;

    }


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
}