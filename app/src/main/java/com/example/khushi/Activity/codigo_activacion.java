package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class codigo_activacion extends AppCompatActivity {

    EditText edtCodigo;
    Button btnsiguiente;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_activacion);


        edtCodigo= findViewById(R.id.edtCodigo);
        btnsiguiente= findViewById(R.id.btnsiguiente);

        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            buscar_codigo_verificacion("http://khushiconfecciones.com/app_khushi/buscar_codigo_verificacion.php?codigo_activacion=" +edtCodigo.getText().toString());
            }
        });

    }


    private void buscar_codigo_verificacion (String URL){
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
                        //edtProducto.setText(jsonObject.getString("producto"));
                        //edtprecio.setText(jsonObject.getString("precio"));
                        //edtFabricante.setText(jsonObject.getString("fabricante"));
                        Toast.makeText(codigo_activacion.this, "codigo validado", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(codigo_activacion.this, RegistroActivity.class);
                        startActivity(intent);

                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se encontró el código de activación ", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}