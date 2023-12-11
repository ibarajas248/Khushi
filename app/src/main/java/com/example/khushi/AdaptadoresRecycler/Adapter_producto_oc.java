package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoproducto_en_oc;

import java.util.ArrayList;

public class Adapter_producto_oc extends RecyclerView.Adapter<Adapter_producto_oc.ViewHolderproducto_oc> implements View.OnClickListener {

    ArrayList<nuevoproducto_en_oc> listoperaciones_oc;
    private View.OnClickListener listener;

    public Adapter_producto_oc(ArrayList<nuevoproducto_en_oc>listoperaciones_oc){
        this.listoperaciones_oc= listoperaciones_oc;
    }

    @NonNull
    @Override
    public Adapter_producto_oc.ViewHolderproducto_oc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_producto_oc,null,false);
        view.setOnClickListener(this);
        return new Adapter_producto_oc.ViewHolderproducto_oc(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_producto_oc.ViewHolderproducto_oc holder, int position) {
        holder.id.setText(String.valueOf(listoperaciones_oc.get(position).getId()));
        holder.id_oc.setText(String.valueOf(listoperaciones_oc.get(position).getIdOrdenCompra()));
        holder.id_producto.setText(String.valueOf(listoperaciones_oc.get(position).getIdProducto()));
        holder.ordenCompra.setText(listoperaciones_oc.get(position).getOrdendeCompra());
        holder.producto.setText(listoperaciones_oc.get(position).getProducto());
        holder.lotes.setText(String.valueOf(listoperaciones_oc.get(position).getLotes()));
        holder.cantidadDeProductos.setText(String.valueOf(listoperaciones_oc.get(position).getCantidad_de_productos()));

    }
    @Override
    public int getItemCount() {
        return listoperaciones_oc.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolderproducto_oc extends RecyclerView.ViewHolder {
        TextView id,id_oc,id_producto,ordenCompra,producto,lotes, cantidadDeProductos;
        public ViewHolderproducto_oc(@NonNull View itemView) {

            super(itemView);
            id=itemView.findViewById(R.id.id_producto_oc);
            id_oc=itemView.findViewById(R.id.id_oc_producto_oc);
            id_producto=itemView.findViewById(R.id.id_producto_producto_oc);
            ordenCompra=itemView.findViewById(R.id.ordenCompra_p_oc);
            producto=itemView.findViewById(R.id.producto_p_oc);
            lotes=itemView.findViewById(R.id.lotes_producto_oc);
            cantidadDeProductos=itemView.findViewById(R.id.cantidadProductos);

        }
    }
}
