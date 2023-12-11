package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class operacion_realizada extends AppCompatActivity {
    private Spinner spinnerproducto, spinnersubparte, spinneroperaciones;
    ArrayList<String> listDatos;
    RequestQueue requestQueue;
    String id_subparte, subparte, idproducto, data3;
    ArrayList<String[]> dataArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacion_realizada);

        spinnerproducto = (Spinner) findViewById(R.id.spinnerselectproducto);
        spinnersubparte = (Spinner) findViewById(R.id.spinnerselect_subparte);
        spinneroperaciones = (Spinner) findViewById(R.id.spinner_operaciones);
        listDatos = new ArrayList<String>();
        requestQueue = Volley.newRequestQueue(this);
        dataArrayList = new ArrayList<>();

        agregarlista("http://khushiconfecciones.com/app_khushi/recycler.php"); // Volver a cargar la lista desde el servidor

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
                        String data1 = jsonObject.getString("producto");
                        String data2 = jsonObject.getString("precio");
                        data3 = jsonObject.getString("id_producto");

                        String[] data = {String.valueOf(i), data3};
                        dataArrayList.add(data);

                        listDatos.add(data1);



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ArrayAdapter <String> adapter = new ArrayAdapter<String>(operacion_realizada.this, android.R.layout.simple_list_item_1,listDatos);
                spinnerproducto.setAdapter(adapter);
                Log.d("DataArrayList", "Imprimiendo los valores de dataArrayList:");
                for (String[] data : dataArrayList) {
                    Log.d("DataArrayList", "Valor en posición 0: " + data[0] + ", Valor en posición 1: " + data[1]);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonArrayRequest);



    }

    private void buscarsubparte (String URL){
        // Crear una solicitud JSON (JsonArrayRequest) con un método GET
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonObject=response.getJSONObject(i);
                        // Extraer datos del objeto JSON y mostrarlos en los campos de texto

                        subparte=jsonObject.getString("subparte");
                        subparte=jsonObject.getString("precio");



                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                }
                ArrayAdapter <String> adaptersubparte = new ArrayAdapter<String>(operacion_realizada.this, android.R.layout.simple_list_item_1,listDatos);
                spinnerproducto.setAdapter(adaptersubparte);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();


            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}