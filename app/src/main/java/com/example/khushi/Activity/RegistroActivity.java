package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private CheckBox  cbsupervisor;
    private EditText edtnombre,edtApellidos, edtcontrasenasupervisor
            , edtCorreo,edttelefono, edtcelular, edtEPS,edtcedula,fondoPension;
    private Button btnsiguiene;
    private Switch switchActivar;
    private EditText codigoAntiguo;
    private Spinner aniovinculacion;

    boolean validacion=false;
    private String Rol="OPERARIO";


    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtnombre= (EditText) findViewById(R.id.editnombre);
        edtApellidos= (EditText) findViewById(R.id.editApellidos);
        edtCorreo= (EditText) findViewById(R.id.edtcorreo);
        edttelefono= (EditText) findViewById(R.id.editTextfijo);
        edtcelular= (EditText) findViewById(R.id.edtcelular);
        edtEPS= (EditText) findViewById(R.id.edEPS);
        aniovinculacion=(Spinner)findViewById(R.id.spaniovinculado);
        edtcedula=(EditText)findViewById(R.id.edtcedula);
        fondoPension=(EditText)findViewById(R.id.edtPeniones);

        edtcontrasenasupervisor= (EditText) findViewById(R.id.edtcontrasenasupervisor);
        cbsupervisor= (CheckBox) findViewById(R.id.cbsupervisor);

        btnsiguiene=(Button) findViewById(R.id.btnsiguiente) ;

        //-----------Spinner--------------------//

        Spinner aniovinculacion = findViewById(R.id.spaniovinculado);
        String[] anios = new String[50];
        for (int i = 0; i < 50; i++) {
            anios[i] = String.valueOf(1989 + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, anios);
        // Especifica el diseño del elemento desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        aniovinculacion.setAdapter(adapter);

        //------------fin del spiner-----------------//






       btnsiguiene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarcamposllenos()){
                    guardarDatos();
                }else{
                    Toast.makeText(RegistroActivity.this, "Hay campos incompletos", Toast.LENGTH_SHORT).show();
                }


                agregarusuario("http://khushiconfecciones.com//app_khushi/insertar_empleado.php");


            }
        });


        //activa el EditText para codigo de supervisor
        cbsupervisor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtcontrasenasupervisor.setEnabled(isChecked);
                if(isChecked){
                    edtcontrasenasupervisor.setVisibility(View.VISIBLE);



                }else{
                    edtcontrasenasupervisor.setVisibility(View.GONE);

                }
            }
        });




    }//fin del ONcreate

    public void guardarDatos(){

        Intent intent=new Intent(RegistroActivity.this, validacionContrasenaAvtivity.class);

        intent.putExtra("idpersona",String.valueOf(generarid()).trim());
        intent.putExtra("nombre", edtnombre.getText().toString().trim());
        intent.putExtra("apellidos", edtApellidos.getText().toString().trim());
        intent.putExtra("correo", edtCorreo.getText().toString().trim());
        intent.putExtra("telefono", edttelefono.getText().toString().trim());
        intent.putExtra("celular", edtcelular.getText().toString().trim());
        intent.putExtra("EPS", edtEPS.getText().toString().trim());
        intent.putExtra("anio_vinculacion", aniovinculacion.getSelectedItem().toString().trim());
        intent.putExtra("cedula",edtcedula.getText().toString().trim());
        intent.putExtra("fondo_de_pensiones",fondoPension.getText().toString().trim());



        // para asignar roles
        if (edtcontrasenasupervisor.getText().toString().equals("j488wn3")&& cbsupervisor.isChecked()){

            intent.putExtra("Rol", "SUPERVISOR");
        }else if(!edtcontrasenasupervisor.getText().toString().equals("j488wn3")&& cbsupervisor.isChecked()){
            //intent.putExtra("Rol","OPERARIO");
            Toast.makeText(RegistroActivity.this, "Contraseña  de supervisor incorrecta", Toast.LENGTH_SHORT).show();
            return;
        }else{
            intent.putExtra("Rol", "OPERARIO");
        }
        startActivity(intent);

    }// cierre de guardar Datos

    private void  agregarusuario (String URL){
        // Crear una solicitud de cadena (StringRequest) con un método POST
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Este método se llama cuando la solicitud es exitosa
                // response contiene la respuesta del servidor en formato de cadena


                Toast.makeText(RegistroActivity.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Este método se llama si hay un error en la solicitud
                // error contiene detalles del error, como un mensaje de error

                Toast.makeText(RegistroActivity.this, error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Este método se utiliza para definir los parámetros que se enviarán en la solicitud POST
                // Debes especificar los parámetros que el servidor espera, como "codigo", "producto", "precio", "fabricante"



                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("idpersona",String.valueOf(generarid()));
                parametros.put("edtnombre",edtnombre.getText().toString());
                parametros.put("edtapellidos",edtApellidos.getText().toString());
                parametros.put("correo_electronico",edtCorreo.getText().toString());
                parametros.put("edtfijo",edttelefono.getText().toString());
                parametros.put("edtcelular",edtcelular.getText().toString());
                parametros.put("edtEPS",edtEPS.getText().toString());
                parametros.put("anio_vinculacion",aniovinculacion.getSelectedItem().toString());
                parametros.put("cedula",edtcedula.getText().toString());
                parametros.put("fondo_de_pensiones",fondoPension.getText().toString());

                if (edtcontrasenasupervisor.getText().toString().equals("123123123")){
                    parametros.put("Rol","ADMIN");
                }



                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private int generarid(){
        int idpersona = (int)(Math.random()*500+1);
        buscaridvalidacion("http://khushiconfecciones.com//app_khushi/buscar_producto.php"+String.valueOf(idpersona));
        //si el codigo ya se encuentra en la base de datos
        // entonces vuelve a llamar el metodo

        if (validacion==true){
            return generarid();
        }
        return idpersona;
    }

    private void buscaridvalidacion (String URL){

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

    private Boolean verificarcamposllenos (){

        String id, nombre, apellidos,correo,telefono,celular, EPS, aniovinculacion_,cedula,pensiones;
        id=String.valueOf(generarid());
        nombre=edtnombre.getText().toString();
        apellidos=edtApellidos.getText().toString();
        correo=edtCorreo.getText().toString();
        telefono=edttelefono.getText().toString();
        celular=edtcelular.getText().toString();
        EPS=edtEPS.getText().toString();
        aniovinculacion_=aniovinculacion.getSelectedItem().toString();
        cedula=edtcedula.getText().toString();
        pensiones=fondoPension.getText().toString();



        return !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty()
                && !telefono.isEmpty() && !celular.isEmpty() && !EPS.isEmpty()&&!aniovinculacion_.isEmpty() && !cedula.isEmpty() && !pensiones.isEmpty();

    }


}