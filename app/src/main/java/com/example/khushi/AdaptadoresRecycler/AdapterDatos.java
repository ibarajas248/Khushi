package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoProducto;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos>implements View.OnClickListener {

    ArrayList<nuevoProducto> listDatos;
    private View.OnClickListener listener;

    public AdapterDatos(ArrayList<nuevoProducto> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        // escucha el evento de seleccion
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {


        holder.id_producto.setText(String.valueOf(listDatos.get(position).getId_producto()));
        holder.producto.setText(listDatos.get(position).getProducto());
        holder.precio.setText(String.valueOf(listDatos.get(position).getPrecio()));


    }

    @Override
    public int getItemCount() {
        return listDatos.size();
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

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView id_producto,producto,precio;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //hago referencia a los datos que le llegan
            id_producto= itemView.findViewById(R.id.idproducto);
            producto=itemView.findViewById(R.id.producto);
            precio=itemView.findViewById(R.id.precioProducto);

        }


    }
}
