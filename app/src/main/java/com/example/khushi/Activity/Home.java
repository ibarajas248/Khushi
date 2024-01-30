package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.khushi.AdaptadoresRecycler.AdapterDatos;
import com.example.khushi.AdaptadoresRecycler.AdapterMenu;
import com.example.khushi.AdaptadoresRecycler.Adapter_menu;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.menuClase;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private Toolbar toolbar1;
    ImageButton btnagregarproducto,btnagregaroperacionempleado,agregaroc;
    private RecyclerView recyclerView;

    ArrayList<menuClase> listaMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listaMenu = new ArrayList<>();
        btnagregarproducto= (ImageButton) findViewById(R.id.imgbtningresarproducto);
        btnagregaroperacionempleado=(ImageButton)findViewById(R.id.imbtnoperacionrealizada);
        agregaroc=(ImageButton)findViewById(R.id.imagenoc);

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
                    startActivity(intent);
                }





            }
        });
        recyclerView.setAdapter(adapter);












        btnagregarproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this, agregarProducto.class);
                startActivity(intent);


            }
        });
        btnagregaroperacionempleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this, operacion_realizada.class);
                startActivity(intent);

            }
        });

        agregaroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this, ordenDeCompra.class);
                startActivity(intent);
            }
        });
    }

    private void llenarMenu() {
        listaMenu.add(new menuClase("Productos"));
        listaMenu.add(new menuClase("Orden de compra"));
        listaMenu.add(new menuClase("Operaciones asignadas"));
        listaMenu.add(new menuClase("cuatro"));
        listaMenu.add(new menuClase("cinco"));
    }


}