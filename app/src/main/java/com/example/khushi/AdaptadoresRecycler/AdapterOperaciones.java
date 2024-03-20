package com.example.khushi.AdaptadoresRecycler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaOperacion;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import java.util.ArrayList;

public class AdapterOperaciones extends RecyclerView.Adapter<AdapterOperaciones.ViewHolderOperaciones> implements View.OnClickListener{
    ArrayList<nuevaOperacion> listOperaciones;
    private View.OnClickListener listener;
    private OnItemLongClickListener longClickListener;




    private int selectedItem = RecyclerView.NO_POSITION;//almacena la posicion del elemento seleccionado
    private int selectedPosition = RecyclerView.NO_POSITION; //variable para cambiar de color de la fila

    public AdapterOperaciones(ArrayList<nuevaOperacion> listOperaciones) {
        this.listOperaciones = listOperaciones;
    }

    public AdapterOperaciones(ArrayList<nuevaOperacion> listOperaciones,OnItemLongClickListener longClickListener) {
        this.listOperaciones = listOperaciones;
        this.longClickListener = longClickListener;
    }



    @NonNull
    @Override
    public AdapterOperaciones.ViewHolderOperaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones,null,false);
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

        if (position == selectedPosition) {
            holder.id_producto.setBackgroundColor(0xFFE82900);
            holder.id_subparte.setBackgroundColor(0xFFE82900);
            holder.id_operacion.setBackgroundColor(0xFFE82900);
            holder.nombre_operacion.setBackgroundColor(0xFFE82900);
            holder.cantidad.setBackgroundColor(0xFFE82900);
            holder.maquina.setBackgroundColor(0xFFE82900);

        }else{



        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restaurar el color de fondo del elemento previamente seleccionado
                notifyItemChanged(selectedPosition);

                // Actualizar la posición seleccionada
                selectedPosition = position;

                // Cambiar el color de fondo del elemento seleccionado
                notifyItemChanged(selectedPosition);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Obtén la posición del elemento clickeado
                int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);

                // Obtén el objeto asociado a la posición
                nuevaOperacion operacion = listOperaciones.get(position);

                // Notificar al listener que se ha realizado un long click
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(operacion);
                }

                return true; // Indica que el evento ha sido manejado
            }
        });



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


            // Obtén la posición del elemento clickeado
            int position = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);

            // Actualiza la posición del elemento seleccionado
            selectedItem = position;

            // Notifica que los datos han cambiado para refrescar el RecyclerView
            notifyDataSetChanged();


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

    public interface OnItemLongClickListener {
        void onItemLongClick(nuevaOperacion asignacion);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        longClickListener = listener;
    }


}
