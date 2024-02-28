package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.khushi.AdaptadoresRecycler.AdapterMenu;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.menuClase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private String ROL ;
    private Toolbar toolbar1;
    ImageButton btnagregarproducto,btnagregaroperacionempleado,agregaroc;
    private RecyclerView recyclerView;

    ArrayList<menuClase> listaMenu;



    String Rol, idEmpleado; //variable para perfil de usuario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        ROL = intent.getStringExtra("Rol");
        idEmpleado= intent.getStringExtra("idEmpleado");

        Toast.makeText(this, idEmpleado, Toast.LENGTH_SHORT).show();


        abrirEnlaceExterno("https://www.google.com");

        listaMenu = new ArrayList<>();

        toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        getSupportActionBar().setTitle("Khushi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Muestra el botón de retroceso


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        llenarMenu();

        AdapterMenu adapter=new AdapterMenu(listaMenu);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listaMenu.get(recyclerView.getChildAdapterPosition(v)).getTitulo()=="Productos"){
                    Intent intent= new Intent(Home.this, agregarProducto.class);
                    startActivity(intent);
                }else if(listaMenu.get(recyclerView.getChildAdapterPosition(v)).getTitulo()=="Orden de compra"){
                    Intent intent= new Intent(Home.this, ordenDeCompra.class);
                    startActivity(intent);
                }else if(listaMenu.get(recyclerView.getChildAdapterPosition(v)).getTitulo()=="Operaciones asignadas"){
                    Intent intent= new Intent(Home.this, consultar_tareas_asignadas.class);
                    intent.putExtra("Rol",String.valueOf(ROL));
                    intent.putExtra("operaciones_completadas","no");
                    intent.putExtra("idEmpleado",String.valueOf(idEmpleado));
                    startActivity(intent);

                }else if (listaMenu.get(recyclerView.getChildAdapterPosition(v)).getTitulo()=="Operaciones completadas"){
                    /*Intent intent= new Intent(Home.this, consultar_tareas_asignadas.class);
                    intent.putExtra("Rol",String.valueOf(ROL));
                    intent.putExtra("operaciones_completadas","si");
                    intent.putExtra("idEmpleado",String.valueOf(idEmpleado));
                    startActivity(intent);

                    */
                    Intent i =new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com"));
                    startActivity(i);
                }





            }
        });
        recyclerView.setAdapter(adapter);












    }



    private void llenarMenu() {

        //

        if (ROL.equalsIgnoreCase("OPERARIO")){

            listaMenu.add(new menuClase("Operaciones asignadas",R.drawable.ic_menu_camera));
            listaMenu.add(new menuClase("Operaciones completadas"));
        }else {
            listaMenu.add(new menuClase("Productos",R.drawable.producto));
            listaMenu.add(new menuClase("Orden de compra",R.drawable.oc));
            listaMenu.add(new menuClase("Operaciones asignadas",R.drawable.asignar));
            listaMenu.add(new menuClase("Operaciones completadas",R.drawable.tarea));
            listaMenu.add(new menuClase("cinco"));
        }


    }


    //metodo que implementa el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuPrincipal) {
            Intent intent = new Intent(Home.this, Menu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Cierra la actividad actual
            return true;  // Importante agregar esta línea para indicar que el evento ha sido manejado

        } else if (id == R.id.fragmento2) {
            // Lanzar la Activity correspondiente al fragmento2
            //Intent intentFragmento2 = new Intent(this, Home.class);
            //startActivity(intentFragmento2);
            Toast.makeText(this, "hola2", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void abrirEnlaceExterno(String url) {
        // Crea un intent con la acción ACTION_VIEW y la URI del enlace
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Verifica si hay una aplicación que pueda manejar la acción ACTION_VIEW
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Inicia la actividad utilizando el intent
            startActivity(intent);
        } else {
            // Maneja el caso en el que no hay una aplicación para manejar la acción ACTION_VIEW
            // Puedes mostrar un mensaje al usuario o realizar alguna otra acción
        }
    }







}