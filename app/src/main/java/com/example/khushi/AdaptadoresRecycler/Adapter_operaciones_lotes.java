package com.example.khushi.AdaptadoresRecycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import java.util.ArrayList;
import java.util.List;

public class Adapter_operaciones_lotes extends RecyclerView.Adapter<Adapter_operaciones_lotes.ViewHolderOperacionesLotes> implements View.OnClickListener{


    ArrayList<operaciones_lotes_clase> listOperaciones;


    private View.OnClickListener listener;

    // Agregar una lista para los datos del Spinner
    List<String> spinnerDataList;

    List<Integer> spinnerIdsList; // Lista para almacenar IDs

    List<Empleado_clase>listaEmpleados;





    public Adapter_operaciones_lotes(ArrayList<operaciones_lotes_clase> listOperaciones, List<String> spinnerDataList, List<Integer> spinnerIdsList, ArrayList<Empleado_clase> listaEmpleados){
        this.listOperaciones = listOperaciones;
        this.spinnerDataList = spinnerDataList; // Inicializar la lista para el Spinner
        this.spinnerIdsList = spinnerIdsList; // Inicializa la lista de IDs
        this.listaEmpleados = listaEmpleados; //inicializa el objeto empleados
    }




    @NonNull
    @Override
    public Adapter_operaciones_lotes.ViewHolderOperacionesLotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_lote,null,false);
        // escucha el evento de seleccion
        view.setOnClickListener(this);
        return new Adapter_operaciones_lotes.ViewHolderOperacionesLotes(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_operaciones_lotes.ViewHolderOperacionesLotes holder, int position) {

        holder.producto.setText(listOperaciones.get(position).getProducto());
        holder.subparte.setText(listOperaciones.get(position).getSubparte());
        holder.operaciones.setText(listOperaciones.get(position).getOperaciones());
        holder.idLotesOperaciones.setText(String.valueOf(listOperaciones.get(position).getId_lotes_operaciones()));
        holder.cantidad.setText(String.valueOf(listOperaciones.get(position).getCantidad()));
        holder.empleado.setText(String.valueOf(listOperaciones.get(position).getEmpleado()));
        holder.id_operaciones_subparte_producto.setText(String.valueOf(listOperaciones.get(position).getId_operacione_subparte_producto()));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, spinnerDataList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(spinnerAdapter);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos >= 0 && pos < spinnerIdsList.size() && pos < listaEmpleados.size()) {
                    //int selectedId = spinnerIdsList.get(pos-1);
                    String selectid = (listaEmpleados.get(pos).getId());
                    String selectnombre= listaEmpleados.get(pos).getNombre();

                    Log.d("seleccion",selectid+" "+selectnombre);

                   // Log.d("Selected ID", String.valueOf(selectedId));
                    //Log.d("Longitud ID", String.valueOf(spinnerIdsList.size()));
                    //Log.d("Longitud Nombres", String.valueOf(spinnerDataList.size()));
                    //Log.d("Lista del Spinner", String.valueOf(spinnerDataList));
                } else {
                    Log.d("Error", "Índice de Spinner fuera de rango");
                }



                // Realizar acciones con el elemento seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Acciones cuando no se selecciona nada
            }
        });


    }

    @Override
    public int getItemCount() {
        return listOperaciones.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public class ViewHolderOperacionesLotes extends RecyclerView.ViewHolder {

        TextView producto,subparte, operaciones,idLotesOperaciones,cantidad, empleado, id_operaciones_subparte_producto;
        Spinner spinner;
        public ViewHolderOperacionesLotes(@NonNull View itemView) {
            super(itemView);
            producto=itemView.findViewById(R.id.producto);
            subparte=itemView.findViewById(R.id.subparte);
            operaciones=itemView.findViewById(R.id.operaciones);
            idLotesOperaciones=itemView.findViewById(R.id.id_lotes_operaciones);
            cantidad=itemView.findViewById(R.id.cantidad);
            empleado= itemView.findViewById(R.id.empleado);
            id_operaciones_subparte_producto=itemView.findViewById(R.id.id_operaciones_subparte_producto);
            spinner = itemView.findViewById(R.id.spinner2);
        }
    }
}
