package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.khushi.AdaptadoresRecycler.Adapter_operaciones_lotes;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Usuario;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    //id de software_para verificar en una solicitud http si esta version todavia es compatible...
    int version_Software_id=9;// Si el id de la base de datos de coincide, se exige
    String linkDescargaSoftware;

    Boolean permisoInisioSesion= true;
    RequestQueue requestQueue;
    boolean validacion=false;
    Button btnregistro;
    Button btniniciosesion;

    private EditText contrasenia;
    public EditText user;
    //variable para hacer interfaz de acuerdo al Rol
    public static Usuario usuarioValidado;

    String nombreUsuario;


    //variables que paso al intent

    private String ROL, idEmpleado;


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
                validacionSoftware("http://khushiconfecciones.com//app_khushi/informacion_software/buscar_version_software.php");

                Handler handler = new Handler();
                //finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ((!user.getText().toString().isEmpty() || !contrasenia.getText().toString().isEmpty())&&permisoInisioSesion==true) {
                            String contraseniaEncriptada;
                            contraseniaEncriptada = encryptPassword(contrasenia.getText().toString());
                            //user.setText(contraseniaEncriptada);
                            //Toast.makeText(MainActivity.this, contraseniaEncriptada, Toast.LENGTH_SHORT).show();
                            validarUsuario("http://khushiconfecciones.com//app_khushi/validar_inicio_sesion.php" + "?usuario=" + user.getText().toString() + "&contrasenia=" + contraseniaEncriptada);
                        } else if(permisoInisioSesion==false){

                            Toast.makeText(MainActivity.this, "Debe actualizar el software", Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            //finalVariableRecibida_idproducto_oc = variableRecibida_idproducto_oc;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Llama al primer método aquí

                                    if (linkDescargaSoftware.equalsIgnoreCase("mantenimiento")){
                                        Intent intent= new Intent(MainActivity.this, Mantenimiento.class);
                                        startActivity(intent);
                                    }else{
                                        Intent descarga =new Intent(Intent.ACTION_VIEW,Uri.parse(linkDescargaSoftware));
                                        startActivity(descarga);
                                    }

                                }
                            }, 5000); // Retraso de 5000 milisegundos (5 segundos)
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Algun campo vacío", Toast.LENGTH_LONG).show();
                        }

                    }
                }, 3000); // Retraso de 5000 milisegundos (5 segundos)




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


                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonObject=response.getJSONObject(i);
                        // Extraer datos del objeto JSON y mostrarlos en los campos de texto
                        int idusuario= Integer.parseInt(jsonObject.getString("id"));
                        idEmpleado= String.valueOf(idusuario);
                        ROL= jsonObject.getString("Rol");
                        String usuario=jsonObject.getString("usuario");
                        contrasenia.setText(jsonObject.getString("contrasenia"));
                        usuarioValidado=new Usuario(idusuario,ROL,usuario);

                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();
                    }

                    if (response.length()  > 0) {

                        Toast.makeText(MainActivity.this, "Usuario validado correctamente", Toast.LENGTH_SHORT).show();
                        String nombreUsuario= String.valueOf(user.getText());
                        Intent intent= new Intent(MainActivity.this, Home.class);
                        user.setText("");
                        contrasenia.setText("");

                        intent.putExtra("Rol",String.valueOf(ROL));
                        intent.putExtra("idEmpleado",String.valueOf(idEmpleado));
                        startActivity(intent);
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

    private void validacionSoftware (String URL){
        // Crear una solicitud JSON (JsonArrayRequest) con un método GET
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Este método se llama cuando la solicitud es exitosa y recibe un JSONArray como respuesta
                JSONObject jsonObject=null;
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        // Extraer datos del objeto JSON y mostrarlos en los campos de texto

                        int id = Integer.parseInt(jsonObject.getString("id"));
                        linkDescargaSoftware = jsonObject.getString("enlace_descarga");


                        if (id==version_Software_id){
                            permisoInisioSesion=true;
                        }else{
                            permisoInisioSesion=false;
                        }


                    }catch (JSONException e){
                        // Capturar y mostrar cualquier error JSON que ocurra
                        Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                }
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