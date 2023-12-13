package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.ordenDeCompraclase;

import java.util.ArrayList;

public class AdapterOC extends RecyclerView.Adapter<AdapterOC.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<ordenDeCompraclase> listOC;
    private View.OnClickListener listener;

    private OnItemLongClickListener itemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(ordenDeCompraclase OC);
    }


    @NonNull
    @Override
    public AdapterOC.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_orden_de_compra,null,false);
        // escucha el evento de seleccion
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);

    }

    public AdapterOC(ArrayList<ordenDeCompraclase>listOC){
        this.listOC=listOC;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOC.ViewHolderDatos holder, int position) {
        holder.idOC.setText(String.valueOf(listOC.get(position).getIdOrdenCompra()));
        holder.ordenCompra.setText(String.valueOf(listOC.get(position).getOrdendeCompra()));
        if (position % 2 == 0) {
            // Para posiciones pares, cambia el color de fondo o el color del texto
            holder.itemView.setBackgroundColor(0xFFCCCCCC);

                    holder.ordenCompra.setTextColor(0xFF000000);
        } else {
            // Para posiciones impares, otro color diferente
            holder.itemView.setBackgroundColor(0xFFEFE1AD);
                    // o
                    holder.ordenCompra.setTextColor(0xFF000000);
        };

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(listOC.get(position));
                    return true;
                }
                return false;
            }
        });

    }



    @Override
    public int getItemCount() {
        return listOC.size();
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

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView idOC, ordenCompra;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            idOC=itemView.findViewById(R.id.idOC);
            ordenCompra=itemView.findViewById(R.id.orden_de_compra);
        }
    }
}
