package com.example.khushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class agregarProducto extends AppCompatActivity {
    ListView listView;
    RecyclerView listadeproductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
         listadeproductos = (RecyclerView) findViewById(R.id.recyclerViewProductos);
         listadeproductos.setLayoutManager(new LinearLayoutManager(this));




        // Realizar una solicitud HTTP al script PHP
        String url = "http://your_domain.com/your_script.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<MyDataModel> dataList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            // Extraer los datos y añadirlos a la lista
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");
                            dataList.add(new MyDataModel(id, name, email));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // Configurar el RecyclerView con el adaptador personalizado
                    MyAdapter adapter = new MyAdapter(dataList);
                    listadeproductos.setAdapter(adapter);
                },
                error -> {
                    // Manejar errores de la solicitud
                });
        queue.add(jsonArrayRequest);



        //finaliza la solicitud


        /*          inicia el codigo php

        <?php

// Configuración de la base de datos
                $servername = "localhost";
        $username = "username";
        $password = "password";
        $dbname = "database_name";

// Crear conexión
        $conn = new mysqli($servername, $username, $password, $dbname);

// Verificar la conexión
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

// Realizar la consulta a la base de datos
        $sql = "SELECT id, name, email FROM table_name";
        $result = $conn->query($sql);

// Verificar si se obtuvieron resultados
        if ($result->num_rows > 0) {
            // Crear un array para almacenar los datos
            $data = array();
            // Almacenar los resultados en el array
            while($row = $result->fetch_assoc()) {
                $data[] = $row;
            }
            // Devolver los datos en formato JSON
            echo json_encode($data);
        } else {
            echo "0 results";
        }
        $conn->close();
?>






*/
        //finaliza el codigo php


    }
}