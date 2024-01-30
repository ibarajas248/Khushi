package com.example.khushi.AdaptadoresRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import java.util.ArrayList;

public class Adapter_consultar_tareas extends RecyclerView.Adapter<Adapter_consultar_tareas.ViewHolderOperacionesLotes> {

    private ArrayList<operaciones_lotes_clase> listOperaciones;
    private OnItemLongClickListener longClickListener;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public Adapter_consultar_tareas(ArrayList<operaciones_lotes_clase> listOperaciones) {
        this.listOperaciones = listOperaciones;
    }

    @NonNull
    @Override
    public ViewHolderOperacionesLotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_lote, parent, false);
        return new ViewHolderOperacionesLotes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOperacionesLotes holder, int position) {
        holder.bindData(listOperaciones.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listOperaciones.size();
    }

    public class ViewHolderOperacionesLotes extends RecyclerView.ViewHolder {

        TextView producto, subparte, operaciones, idLotesOperaciones, cantidad, empleado, id_operaciones_subparte_producto;

        public ViewHolderOperacionesLotes(@NonNull View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.producto);
            subparte = itemView.findViewById(R.id.subparte);
            operaciones = itemView.findViewById(R.id.operaciones);
            idLotesOperaciones = itemView.findViewById(R.id.id_lotes_operaciones);
            cantidad = itemView.findViewById(R.id.cantidad);
            empleado = itemView.findViewById(R.id.empleado);
            id_operaciones_subparte_producto = itemView.findViewById(R.id.id_operaciones_subparte_producto);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (longClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(v, adapterPosition);

                        // Muestra un Toast con el nombre del producto
                        Context context = v.getContext();
                        Toast.makeText(context, "Nombre del producto: " + listOperaciones.get(adapterPosition).getProducto(), Toast.LENGTH_SHORT).show();

                        return true; // consume el evento
                    }
                    return false;
                }
            });
        }

        public void bindData(operaciones_lotes_clase operacion, int position) {
            producto.setText(operacion.getProducto());
            subparte.setText(operacion.getSubparte());
            operaciones.setText(operacion.getOperaciones());
            idLotesOperaciones.setText(String.valueOf(operacion.getId_lotes_operaciones()));
            cantidad.setText(String.valueOf(operacion.getCantidad()));
            empleado.setText(String.valueOf(operacion.getEmpleado()));
            id_operaciones_subparte_producto.setText(String.valueOf(operacion.getId_operacione_subparte_producto()));

            // Cambia el color de fondo según la posición seleccionada
            if (position == selectedPosition) {
                itemView.setBackgroundColor(0xFFE82900);
            } else {
                itemView.setBackgroundColor(0xFF1976D2);
            }
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    // Método para cambiar la posición seleccionada
    public void setSelectedPosition(int position) {
        // Restaura el color de fondo del elemento previamente seleccionado
        notifyItemChanged(selectedPosition);

        // Actualiza la posición seleccionada
        selectedPosition = position;

        // Cambia el color de fondo del elemento seleccionado
        notifyItemChanged(selectedPosition);
    }
}
