package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private CheckBox  cbsupervisor;
    private EditText edtnombre,edtApellidos, edtcontrasenasupervisor
            , edtCorreo,edttelefono, edtcelular, edtEPS,edtcedula,fondoPension,
            edDireccion,edtContactoEmergencia,edtNumeroEmergencia,edtFecha_Nacimiento,
            edtFechaVinculacion,edtcelularWhatsapp;
    private Button btnsiguiene;
    private Calendar calendar;
    private Switch switchActivar;
    private EditText codigoAntiguo;


    boolean validacion=false;
    private String Rol="OPERARIO";
    private int idPersona;


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
        edtcedula=(EditText)findViewById(R.id.edtcedula);
        fondoPension=(EditText)findViewById(R.id.edtPeniones);
        edDireccion=(EditText)findViewById(R.id.edDireccion1);
        edtContactoEmergencia=(EditText)findViewById(R.id.edtContacto_emergencia);
        edtNumeroEmergencia=(EditText)findViewById(R.id.edtNumero_emergencia);
        edtFecha_Nacimiento=(EditText)findViewById(R.id.edtFechaNacimiento);
        edtFechaVinculacion=(EditText)findViewById(R.id.edtFechaVinculacion);
        edtcelularWhatsapp=(EditText)findViewById(R.id.edtcelularWhatsapp);

        configFechaNacimiento();
        configFechaVinculacion();


        edtcontrasenasupervisor= (EditText) findViewById(R.id.edtcontrasenasupervisor);
        cbsupervisor= (CheckBox) findViewById(R.id.cbsupervisor);

        btnsiguiene=(Button) findViewById(R.id.btnsiguiente) ;








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

    private void configFechaVinculacion() {

        edtFechaVinculacion.setFocusable(false);
        // Crear un objeto Calendar
        calendar = Calendar.getInstance();

        // Configurar el OnClickListener para el EditText
        edtFechaVinculacion.setOnClickListener(v -> showDatePickerDialog(edtFechaVinculacion));
    }

    private void configFechaNacimiento() {
        edtFecha_Nacimiento.setFocusable(false);
        // Crear un objeto Calendar
        calendar = Calendar.getInstance();

        // Configurar el OnClickListener para el EditText
        edtFecha_Nacimiento.setOnClickListener(v -> showDatePickerDialog(edtFecha_Nacimiento));
    }

    private void showDatePickerDialog(EditText edt) {
        // Obtener el año, mes y día actuales
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear un DatePickerDialog con la fecha actual como predeterminada
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Formatear la fecha seleccionada al formato AAAA-MM-DD
               if (edt==edtFecha_Nacimiento){
                   String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                   RegistroActivity.this.edtFecha_Nacimiento.setText(fechaSeleccionada);
               }else if (edt==edtFechaVinculacion){
                   String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                   RegistroActivity.this.edtFechaVinculacion.setText(fechaSeleccionada);
               }

            }
        }, year, month, dayOfMonth);

        // Mostrar el diálogo de selección de fecha
        datePickerDialog.show();
    }


    public void guardarDatos(){

        Intent intent=new Intent(RegistroActivity.this, validacionContrasenaAvtivity.class);

        idPersona=generarid();
        intent.putExtra("idpersona",String.valueOf(idPersona).trim());
        intent.putExtra("nombre", edtnombre.getText().toString().trim());
        intent.putExtra("apellidos", edtApellidos.getText().toString().trim());
        intent.putExtra("correo", edtCorreo.getText().toString().trim());
        intent.putExtra("telefono", edttelefono.getText().toString().trim());
        intent.putExtra("celular", edtcelular.getText().toString().trim());
        intent.putExtra("EPS", edtEPS.getText().toString().trim());
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
                parametros.put("idpersona",String.valueOf(idPersona));
                parametros.put("edtnombre",edtnombre.getText().toString());
                parametros.put("edtapellidos",edtApellidos.getText().toString());
                parametros.put("correo_electronico",edtCorreo.getText().toString());
                parametros.put("edtfijo",edttelefono.getText().toString());
                parametros.put("edtcelular",edtcelular.getText().toString());
                parametros.put("edtEPS",edtEPS.getText().toString());
                parametros.put("anio_vinculacion",edtFechaVinculacion.getText().toString());
                parametros.put("cedula",edtcedula.getText().toString());
                parametros.put("fondo_de_pensiones",fondoPension.getText().toString());
                parametros.put("direccion_residencia",edDireccion.getText().toString());
                parametros.put("contacto_emergencia",edtContactoEmergencia.getText().toString());
                parametros.put("telefono_emergencia",edtNumeroEmergencia.getText().toString());
                parametros.put("fecha_de_nacimiento",edtFecha_Nacimiento.getText().toString());
                parametros.put("whatsapp",edtcelularWhatsapp.getText().toString());






                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley para que se envíe al servidor
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private int generarid(){
        int idpersona = (int)(Math.random()*500+1);
        buscaridvalidacion("http://khushiconfecciones.com//app_khushi/buscar_producto.php?id="+String.valueOf(idpersona));
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

        String id, nombre, apellidos,correo,telefono,celular, EPS,
                aniovinculacion_,cedula,pensiones,direccion,fechaNacimiento,fechaVinculacion,
                contactoEmergencia,numeroEmergencia,celuWhatsapp;
        id=String.valueOf(generarid());
        nombre=edtnombre.getText().toString();
        apellidos=edtApellidos.getText().toString();
        correo=edtCorreo.getText().toString();
        //telefono=edttelefono.getText().toString();
        celular=edtcelular.getText().toString();
        EPS=edtEPS.getText().toString();
        cedula=edtcedula.getText().toString();
        pensiones=fondoPension.getText().toString();
        direccion=edDireccion.getText().toString();
        fechaNacimiento=edtFecha_Nacimiento.getText().toString();
        fechaVinculacion=edtFechaVinculacion.getText().toString();
        contactoEmergencia=edtContactoEmergencia.getText().toString();
        numeroEmergencia=edtNumeroEmergencia.getText().toString();
        celuWhatsapp=edtcelularWhatsapp.getText().toString();





        return !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty()
                 && !celular.isEmpty() && !EPS.isEmpty()&& !cedula.isEmpty() && !pensiones.isEmpty()&&
                !direccion.isEmpty()&&!fechaNacimiento.isEmpty()&& !fechaVinculacion.isEmpty()&&
                !contactoEmergencia.isEmpty()&&!numeroEmergencia.isEmpty()&&!celuWhatsapp.isEmpty();

    }


}