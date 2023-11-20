package com.example.khushi.AdaptadoresRecycler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaOperacion;
import com.example.khushi.clasesinfo.nuevoProducto;

import java.util.ArrayList;

public class AdapterOperaciones extends RecyclerView.Adapter<AdapterOperaciones.ViewHolderOperaciones> implements View.OnClickListener{
    ArrayList<nuevaOperacion> listOperaciones;
    private View.OnClickListener listener;

    public AdapterOperaciones(ArrayList<nuevaOperacion> listOperaciones) {
        this.listOperaciones = listOperaciones;
    }



    @NonNull
    @Override
    public AdapterOperaciones.ViewHolderOperaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operaciones,null,false);
        view.setOnClickListener(this);
        return new ViewHolderOperaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOperaciones.ViewHolderOperaciones holder, int position) {
        holder.id_producto.setText(String.valueOf(listOperaciones.get(position).getId_producto()));
        holder.id_subparte.setText(String.valueOf(listOperaciones.get(position).getId_subparte()));
        holder.id_operacion.setText(String.valueOf(listOperaciones.get(position).getId_operacion()));
        holder.nombre_operacion.setText(listOperaciones.get(position).getNombreOperacion());
        holder.cantidad.setText(String.valueOf(listOperaciones.get(position).getCantidad()));
        holder.maquina.setText(listOperaciones.get(position).getMaquina());
    }

    @Override
    public int getItemCount() {
        return listOperaciones.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }

    }

    public class ViewHolderOperaciones extends RecyclerView.ViewHolder {
        TextView id_producto,id_operacion,id_subparte,nombre_operacion,cantidad,maquina;
        public ViewHolderOperaciones(@NonNull View itemView) {
            super(itemView);


            //Hago referencia a los datos que le llegan
            id_producto= itemView.findViewById(R.id.idproducto_operacion);
            id_operacion=itemView.findViewById(R.id.id_operacion_operacion);
            id_subparte=itemView.findViewById(R.id.id_subparte_operacion);
            nombre_operacion=itemView.findViewById(R.id.nombre_operacion_operacion);
            cantidad=itemView.findViewById(R.id.cantidad_operacion);
            maquina=itemView.findViewById(R.id.maquina_operacion);



        }
    }
}
