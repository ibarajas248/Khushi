package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import java.util.ArrayList;

public class Adapter_operaciones_lotes_con_card extends RecyclerView.Adapter<Adapter_operaciones_lotes_con_card.ViewHolderOperacionesLotes_card> implements View.OnClickListener  {

    ArrayList<operaciones_lotes_clase> listOperaciones;
    private View.OnClickListener listener;

    public Adapter_operaciones_lotes_con_card(ArrayList<operaciones_lotes_clase>listOperaciones){
        this.listOperaciones = listOperaciones;
    }
    @Override
    public void onClick(View v) {

    }


    @NonNull
    @Override
    public Adapter_operaciones_lotes_con_card.ViewHolderOperacionesLotes_card onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_lotes_cardview, parent, false);
        // escucha el evento de seleccion
        view.setOnClickListener(this);
        return new Adapter_operaciones_lotes_con_card.ViewHolderOperacionesLotes_card(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_operaciones_lotes_con_card.ViewHolderOperacionesLotes_card holder, int position) {
        holder.Operacion.setText(String.valueOf(listOperaciones.get(position).getOperaciones()));
    }

    @Override
    public int getItemCount() {
        return listOperaciones.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public class ViewHolderOperacionesLotes_card extends RecyclerView.ViewHolder {
        TextView Operacion, subparte, cantidad, lote, estado, Empleado;
        public ViewHolderOperacionesLotes_card(@NonNull View itemView) {
            super(itemView);
            Operacion=itemView.findViewById(R.id.Operacion);
        }
    }
}
