package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.khushi.AdaptadoresRecycler.AdapterOperaciones;
import com.example.khushi.clasesinfo.nuevaOperacion;
import com.example.khushi.login.RegistroActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    //variable para hacer interfaz de acuerdo al Rol
    public static Usuario usuarioValidado;


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



                if (!user.getText().toString().isEmpty()|| !contrasenia.getText().toString().isEmpty()) {
                    String contraseniaEncriptada;
                    contraseniaEncriptada=encryptPassword(contrasenia.getText().toString());
                    //user.setText(contraseniaEncriptada);
                    //Toast.makeText(MainActivity.this, contraseniaEncriptada, Toast.LENGTH_SHORT).show();
                    validarUsuario("http://khushiconfecciones.com//app_khushi/validar_inicio_sesion.php" + "?usuario=" + user.getText().toString() + "&contrasenia=" + contraseniaEncriptada);
                }else{
                    Toast.makeText(MainActivity.this, "Algun campo vacío", Toast.LENGTH_LONG).show();
                }

                //validarUsuario("http://khushiconfecciones.com//app_khushi/validar_inicio_sesion.php");
               // Intent intent= new Intent(MainActivity.this, Home.class);
                //startActivity(intent);


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

    private void validarUsuario (String URL){
        // Crear una solicitud JSON (JsonArrayRequest) con un método GET
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.length()  > 0) {

                    Toast.makeText(MainActivity.this, "Usuario validado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonObject=response.getJSONObject(i);
                        // Extraer datos del objeto JSON y mostrarlos en los campos de texto

                        int idusuario= Integer.parseInt(jsonObject.getString("id"));
                        String ROL= jsonObject.getString("Rol");
                        String usuario=jsonObject.getString("usuario");
                        contrasenia.setText(jsonObject.getString("contrasenia"));

                        usuarioValidado=new Usuario(idusuario,ROL,usuario);


                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }


                }
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();


            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    private String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}