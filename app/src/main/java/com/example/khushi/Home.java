package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    ImageButton btnagregarproducto,btnagregaroperacionempleado,agregaroc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnagregarproducto= (ImageButton) findViewById(R.id.imgbtningresarproducto);
        btnagregaroperacionempleado=(ImageButton)findViewById(R.id.imbtnoperacionrealizada);
        agregaroc=(ImageButton)findViewById(R.id.imagenoc);


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