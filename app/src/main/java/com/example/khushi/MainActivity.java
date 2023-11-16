package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    boolean validacion=false;
    Button btnregistro;
    Button btniniciosesion;

    private EditText contrasenia;
    private EditText user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregistro=(Button) findViewById(R.id.btnregistro);
        btniniciosesion=(Button)findViewById(R.id.btniniciarsesion) ;

        user=(EditText)findViewById(R.id.edtusuario);
        contrasenia=(EditText)findViewById(R.id.edtContraseniapp1);


        btniniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accion al presionar el boton


                //buscaruser("http://khushiconfecciones.com//app_khushi/validar_inicio_sesion.php"+ "?usuario=" + user.getText().toString() + "&contrasenia=" + contrasenia.getText().toString());
                //validarUsuario("http://khushiconfecciones.com//app_khushi/validar_inicio_sesion.php");
                Intent intent= new Intent(MainActivity.this, Home.class);
                startActivity(intent);


            }
        });
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validarUsuario(String URL){

        /*Request.Method.POST: Este es el primer parámetro y especifica el método HTTP que se utilizará
        para la solicitud. En este caso, se está utilizando POST, lo que significa que los datos se
        enviarán al servidor en el cuerpo de la solicitud HTTP.


        esponse.Listener. Define lo que debe hacer la aplicación cuando reciba una respuesta exitosa
         (es decir, cuando el servidor responda correctamente).
         */


        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override

            //on response quiere decir que si se valida...

            public void onResponse(String response) {
                // Si la respuesta no está vacía, significa que el usuario es válido
                if(!response.isEmpty()){
                   // guardarPreferencias();
                    //lollevaalaactividadprincipal
                    Toast.makeText(MainActivity.this,"validado correctamente",Toast.LENGTH_SHORT).show();

                    //Intentintent=newIntent(getApplicationContext(),PrincipalActivity.class);
                    //startActivity(intent);
                    //finish();
                }else{

                    //Mostrarunmensajedeerrorsilascredencialessonincorrectas

                    Toast.makeText(MainActivity.this,"usuarioocontraseñainconrrecta",Toast.LENGTH_SHORT).show();
                }

            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

                //cuando hay error en conectarse a la base de Datos

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }

        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // envian los parametros  a php
                Map<String, String>parametros=new HashMap<String, String>();
                parametros.put ("usuario", user.getText().toString());
                parametros.put ("contrasenia", contrasenia.getText().toString());

                return parametros;
            }
        };

       /* Aquí se crea una instancia de la clase RequestQueue utilizando el método Volley.
         newRequestQueue(this). La variable requestQueue se utiliza para almacenar y
         administrar todas las solicitudes de red que la aplicación necesita realizar.*/


        RequestQueue requestQueue=Volley.newRequestQueue(this);

        /*
        que representa la solicitud HTTP POST que se debe realizar al servidor.
        Al agregar la solicitud a la cola, Volley se encarga de administrar la ejecución
         de la solicitud en segundo plano, lo que incluye establecer la conexión con el
         */

        requestQueue.add(stringRequest);
    }// fin del metodo validar usuario










    private String encryptPassword(String password) {
        try {
            byte[] salt = getSalt();
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            SecretKeySpec secretKey = new SecretKeySpec(hash, "AES");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(secretKey.getEncoded());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return password;
    }

    private byte[] getSalt() {
        return new byte[16];
    }

}