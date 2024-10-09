package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;

import java.util.ArrayList;

public class Adapter_empleados extends RecyclerView.Adapter<Adapter_empleados.ViewHolderempleados> implements View.OnClickListener {

    ArrayList<Empleado_clase> listaEmpleados;
    private View.OnClickListener listener;

    public Adapter_empleados (ArrayList<Empleado_clase>listaEmpleados){
        this.listaEmpleados=listaEmpleados;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public Adapter_empleados.ViewHolderempleados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empleados,null,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empleados, parent, false);

        view.setOnClickListener(this);

        return new ViewHolderempleados(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_empleados.ViewHolderempleados holder, int position) {
        holder.nombre.setText(String.valueOf(listaEmpleados.get(position).getNombre()));
        holder.Apellidos.setText(String.valueOf(listaEmpleados.get(position).getApellidos()));
        holder.correo.setText(String.valueOf(listaEmpleados.get(position).getCorreoElectronico()));
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;

    }

    public class ViewHolderempleados extends RecyclerView.ViewHolder {
        TextView nombre, Apellidos, correo;
        public ViewHolderempleados(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre);
            Apellidos= itemView.findViewById(R.id.Apellidos);
            correo= itemView.findViewById(R.id.Correo_Electronico);
        }
    }
}
