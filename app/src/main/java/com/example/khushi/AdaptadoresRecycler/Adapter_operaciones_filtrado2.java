package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.operacionesFiltradas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter_operaciones_filtrado2 extends RecyclerView.Adapter<Adapter_operaciones_filtrado2.ViewHolderoperaciones>implements View.OnClickListener {

    ArrayList<operacionesFiltradas> listOperacionesFiltradas;
    private View.OnClickListener listener;
    private View.OnLongClickListener longClickListener;
    ArrayList<operacionesFiltradas> buscador; //Array para el searchView

    public Adapter_operaciones_filtrado2(ArrayList<operacionesFiltradas> listOperacionesFiltradas) {
        this.listOperacionesFiltradas = listOperacionesFiltradas;
        this.buscador = new ArrayList<>(listOperacionesFiltradas);
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public Adapter_operaciones_filtrado2.ViewHolderoperaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_filtradas,null,false);

        view.setOnClickListener(this);
        // Configurar el LongClickListener para el itemView
        view.setOnLongClickListener(longClickListener);
        return new ViewHolderoperaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_operaciones_filtrado2.ViewHolderoperaciones holder, int position) {
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

    public void filtrado(String txtBuscar){
        int longitud =txtBuscar.length();
        if(longitud==0){
            listOperacionesFiltradas.clear();
            listOperacionesFiltradas.addAll(buscador);
        }else{
            List<operacionesFiltradas> colleccion =listOperacionesFiltradas.stream().filter
                            (i ->i.getOperaciones().toLowerCase().contains(txtBuscar.toLowerCase())||
                                    i.getProducto().toLowerCase().contains(txtBuscar.toLowerCase()) ||
                                    i.getSubparte().toLowerCase().contains(txtBuscar.toLowerCase())||
                                    String.valueOf(i.getPrecio()).toLowerCase().contains(txtBuscar.toLowerCase())||
                                    String.valueOf(i.getCantidad()).toLowerCase().contains(txtBuscar.toLowerCase())
                            )
                    .collect(Collectors.toList());
            listOperacionesFiltradas.clear();
            listOperacionesFiltradas.addAll(colleccion);
        }
        notifyDataSetChanged();

    }

    public void setOnItemLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


}
