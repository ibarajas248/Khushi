package com.example.khushi.AdaptadoresRecycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoproducto_en_oc;
import com.example.khushi.clasesinfo.operacionesFiltradas;

import java.util.ArrayList;


public class Adapter_operaciones_filtrado extends RecyclerView.Adapter<Adapter_operaciones_filtrado.ViewHolderoperaciones> implements View.OnClickListener {
    ArrayList<operacionesFiltradas> listOperacionesFiltradas;

    private View.OnClickListener listener;

    public Adapter_operaciones_filtrado(ArrayList<operacionesFiltradas>listOperacionesFiltradas){
        this.listOperacionesFiltradas= listOperacionesFiltradas;
    }
    @NonNull
    @Override
    public Adapter_operaciones_filtrado.ViewHolderoperaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_filtradas,null,false);
        view.setOnClickListener(this);
        return new Adapter_operaciones_filtrado.ViewHolderoperaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_operaciones_filtrado.ViewHolderoperaciones holder, @SuppressLint("RecyclerView") int position) {
        holder.idProducto.setText(String.valueOf(listOperacionesFiltradas.get(position).getIdProducto()));
        holder.producto.setText(listOperacionesFiltradas.get(position).getProducto());
        holder.idSubparte.setText(String.valueOf(listOperacionesFiltradas.get(position).getIdSubparte()));
        holder.subparte.setText(listOperacionesFiltradas.get(position).getSubparte());
        holder.idOperaciones.setText(String.valueOf(listOperacionesFiltradas.get(position).getOperaciones()));
        holder.Operaciones.setText(listOperacionesFiltradas.get(position).getOperaciones());
        holder.cantidadOperacion.setText(String.valueOf(listOperacionesFiltradas.get(position).getCantidad()));
        holder.maquina.setText(listOperacionesFiltradas.get(position).getMaquina());
        holder.precioFiltrado.setText(String.valueOf(listOperacionesFiltradas.get(position).getPrecio()));


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Guarda el estado del CheckBox en tu modelo de datos o realiza acciones basadas en su estado
                listOperacionesFiltradas.get(position).setChecked(isChecked);
            }
        });

        // Establece el estado del CheckBox en función del modelo de datos

        holder.checkBox.setChecked(listOperacionesFiltradas.get(position).isChecked());

    }

    @Override
    public int getItemCount() {
        return listOperacionesFiltradas.size();
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

    public class ViewHolderoperaciones extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView idProducto, producto, idSubparte, subparte, idOperaciones, Operaciones,
        cantidadOperacion,maquina, precioFiltrado;
        public ViewHolderoperaciones(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            idProducto=itemView.findViewById(R.id.id_producto_filtro);
            producto=itemView.findViewById(R.id.producto_filtrado);
            idSubparte=itemView.findViewById(R.id.id_subparte_filtrado);
            subparte=itemView.findViewById(R.id.subparte_filtrado);
            idOperaciones= itemView.findViewById(R.id.id_operaciones_filtrado);
            Operaciones=itemView.findViewById(R.id.operaciones_filtrado);
            cantidadOperacion=itemView.findViewById(R.id.cantidadoperacion_filtrado);
            maquina=itemView.findViewById(R.id.maquina_filtrado);
            precioFiltrado=itemView.findViewById(R.id.precio_filtrado);
        }
    }
}
