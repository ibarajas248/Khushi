package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class validacionContrasenaAvtivity extends AppCompatActivity {





    RequestQueue requestQueue;
    private EditText edtcontrasena1, edtcontrasena2, usuario;
    private Button btnregistrarme;
    String idpersona, nombre, apellidos, correo, telefono, celular, EPS, anio_vinculacion, Rol,user, contrasenia;
    boolean validacion=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_contrasena_avtivity);


        // Recupera los extras enviados desde la actividad anterior





        Intent intent = getIntent();
        idpersona = intent.getStringExtra("idpersona");
        nombre = intent.getStringExtra("nombre");
        apellidos = intent.getStringExtra("apellidos");
        correo = intent.getStringExtra("correo");
        telefono = intent.getStringExtra("telefono");
        celular = intent.getStringExtra("celular");
        EPS = intent.getStringExtra("EPS");
        anio_vinculacion = intent.getStringExtra("anio_vinculacion");
        Rol=intent.getStringExtra("Rol");




        //fin de datos que son traidos desde el otro activity


        usuario=(EditText)findViewById(R.id.edscribausuario);

        btnregistrarme=(Button) findViewById(R.id.btnregistrarme1);




        user= usuario.getText().toString();

        btnregistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user= usuario.getText().toString();
                edtcontrasena1= (EditText) findViewById(R.id.contrasena1validacion);
                edtcontrasena2= (EditText) findViewById(R.id.contrasena2validacion);
                if (edtcontrasena1.getText().toString().equals(edtcontrasena2.getText().toString())){
                    contrasenia=encryptPassword(edtcontrasena1.getText().toString());
                }

                buscaruservalidacion("http://khushiconfecciones.com//app_khushi/buscar_usuario.php"+(user));
                if (validacion==true){
                    return;
                }
                agregarusuario("http://khushiconfecciones.com//app_khushi/insertar_empleado.php");

            }
        });




    }//fin del oncreate

    private void  agregarusuario (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena

                Log.d("Response", response);
                Toast.makeText(validacionContrasenaAvtivity.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                //Toast.makeText(validacionContrasenaAvtivity.this, error.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(validacionContrasenaAvtivity.this, "Error en la solicitud: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la solicitud: " + error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"




/*
                Intent intent = getIntent();
                idpersona = intent.getStringExtra("idpersona");
                nombre = intent.getStringExtra("nombre");
                apellidos = intent.getStringExtra("apellidos");
                correo = intent.getStringExtra("correo");
                telefono = intent.getStringExtra("telefono");
                celular = intent.getStringExtra("celular");
                EPS = intent.getStringExtra("EPS");
                anio_vinculacion = intent.getStringExtra("anio_vinculacion");
                Rol=intent.getStringExtra("Rol");*/


                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("idpersona",String.valueOf(idpersona));
                parametros.put("edtnombre",nombre);
                parametros.put("edtapellidos",apellidos);
                parametros.put("correo_electronico",correo);
                parametros.put("edtfijo",telefono);
                parametros.put("edtcelular",celular);
                parametros.put("edtEPS",EPS);
                parametros.put("anio_vinculacion",anio_vinculacion);
                parametros.put("Rol",Rol);
                parametros.put("usuario",user);
                parametros.put("contrasenia",contrasenia);


                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void buscaruservalidacion (String URL){

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonObject=response.getJSONObject(i);
                        validacion=true;





                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        //Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();


            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private String encryptPassword(String password) {
        try {
            byte[] salt = getSalt();
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            SecretKeySpec secretKey = new SecretKeySpec(hash, "AES");
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private byte[] getSalt() {
        return new byte[16];
    }



}