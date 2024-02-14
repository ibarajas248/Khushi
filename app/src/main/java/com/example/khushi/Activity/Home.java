package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.khushi.AdaptadoresRecycler.AdapterMenu;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.menuClase;

import java.util.ArrayList;

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
                    Intent intent= new Intent(Home.this, consultar_tareas_asignadas.class);
                    intent.putExtra("Rol",String.valueOf(ROL));
                    intent.putExtra("operaciones_completadas","si");
                    intent.putExtra("idEmpleado",String.valueOf(idEmpleado));
                    startActivity(intent);
                }





            }
        });
        recyclerView.setAdapter(adapter);












    }



    private void llenarMenu() {

        //

        if (ROL.equalsIgnoreCase("OPERARIO")){

            listaMenu.add(new menuClase("Operaciones asignadas"));
            listaMenu.add(new menuClase("Operaciones completadas"));
        }else {
            listaMenu.add(new menuClase("Productos"));
            listaMenu.add(new menuClase("Orden de compra"));
            listaMenu.add(new menuClase("Operaciones asignadas"));
            listaMenu.add(new menuClase("Operaciones completadas"));
            listaMenu.add(new menuClase("cinco"));
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }


}