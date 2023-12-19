package com.example.khushi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.khushi.AdaptadoresRecycler.Adapter_menu;
import com.example.khushi.R;

public class Home extends AppCompatActivity {

    private Toolbar toolbar1;
    ImageButton btnagregarproducto,btnagregaroperacionempleado,agregaroc;
    private RecyclerView recyclerView;
    private Adapter_menu adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        btnagregarproducto= (ImageButton) findViewById(R.id.imgbtningresarproducto);
        btnagregaroperacionempleado=(ImageButton)findViewById(R.id.imbtnoperacionrealizada);
        agregaroc=(ImageButton)findViewById(R.id.imagenoc);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] menuItems = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};
        adapter = new Adapter_menu(menuItems);
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
}