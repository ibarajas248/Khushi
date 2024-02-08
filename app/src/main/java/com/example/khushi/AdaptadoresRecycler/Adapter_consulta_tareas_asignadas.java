package com.example.khushi.AdaptadoresRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevoProducto;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter_consulta_tareas_asignadas extends RecyclerView.Adapter<Adapter_consulta_tareas_asignadas.ViewHolderTareasAsignadas> implements View.OnClickListener {

    ArrayList<operaciones_lotes_clase> buscador; //Array para el searchView

    private int selectedItem = RecyclerView.NO_POSITION;//almacena la posicion del elemento seleccionado
    ArrayList<operaciones_lotes_clase> listOperaciones;

    private View.OnClickListener listener;


    private OnItemLongClickListener itemLongClickListener;


    public interface OnItemLongClickListener {
        void onItemLongClick(operaciones_lotes_clase asignacion);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    //constru
    public Adapter_consulta_tareas_asignadas(ArrayList<operaciones_lotes_clase> listOperaciones) {
        this.listOperaciones = listOperaciones;

        //inicializa el buscador
        buscador = new ArrayList<>();
        buscador.addAll(listOperaciones);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);

            // Obtén la posición del elemento clickeado
            int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);

            // Actualiza la posición del elemento seleccionado
            selectedItem = position;

            // Notifica que los datos han cambiado para refrescar el RecyclerView
            notifyDataSetChanged();
        }

    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;




    }

    @NonNull
    @Override
    public Adapter_consulta_tareas_asignadas.ViewHolderTareasAsignadas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_lote, parent, false);
        // escucha el evento de selección
        view.setOnClickListener(this);
        return new Adapter_consulta_tareas_asignadas.ViewHolderTareasAsignadas(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_consulta_tareas_asignadas.ViewHolderTareasAsignadas holder, int position) {

        holder.producto.setText(listOperaciones.get(position).getProducto());
        holder.producto.setVisibility(View.VISIBLE);//visibilizar producto
        holder.subparte.setText(listOperaciones.get(position).getSubparte());
        holder.operaciones.setText(listOperaciones.get(position).getOperaciones());
        holder.idLotesOperaciones.setText(String.valueOf(listOperaciones.get(position).getId_lotes_operaciones()));
        holder.cantidad.setText(String.valueOf(listOperaciones.get(position).getCantidad()));
        holder.empleado.setText(String.valueOf(listOperaciones.get(position).getEmpleado()));
        holder.id_operaciones_subparte_producto.setText(String.valueOf(listOperaciones.get(position).getId_operacione_subparte_producto()));
        holder.nombre.setText(listOperaciones.get(position).getNombre());
        holder.nombre.setVisibility(View.VISIBLE); //textview nombre visible
        holder.apellidos.setText(listOperaciones.get(position).getApellido());
        holder.apellidos.setVisibility(View.VISIBLE); //apellidos visible
        holder.lote.setText(String.valueOf(listOperaciones.get(position).getLotes()));
        holder.completado.setText(listOperaciones.get(position).getCompletado());


        // Cambia el color de fondo si el elemento está seleccionado
        if (position == selectedItem) {
            holder.itemView.setBackgroundColor(0xFFE82900); // Color cuando está seleccionado
            holder.producto.setBackgroundColor(0xFFE82900);
            holder.subparte.setBackgroundColor(0xFFE82900);
            holder.operaciones.setBackgroundColor(0xFFE82900);
            holder.idLotesOperaciones.setBackgroundColor(0xFFE82900);
            holder.cantidad.setBackgroundColor(0xFFE82900);
            holder.empleado.setBackgroundColor(0xFFE82900);
            holder.id_operaciones_subparte_producto.setBackgroundColor(0xFFE82900);
            holder.nombre.setBackgroundColor(0xFFE82900);
            holder.apellidos.setBackgroundColor(0xFFE82900);
            holder.lote.setBackgroundColor(0xFFE82900);
            holder.completado.setBackgroundColor(0xFFE82900);
        } else {
            holder.producto.setBackgroundColor(0xFF1976D2);
            holder.subparte.setBackgroundColor(0xFF1E88E5);
            holder.operaciones.setBackgroundColor(0xFF1976D2);
            holder.idLotesOperaciones.setBackgroundColor(0xFF1976D2);
            holder.cantidad.setBackgroundColor(0xFF1E88E5);
            holder.empleado.setBackgroundColor(0xFF1976D2);
            holder.id_operaciones_subparte_producto.setBackgroundColor(0xFF1976D2);
            holder.nombre.setBackgroundColor(0xFF1976D2);
            holder.apellidos.setBackgroundColor(0xFF1E88E5);
            holder.lote.setBackgroundColor(0xFF1976D2);
            holder.completado.setBackgroundColor(0xFF1976D2);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(listOperaciones.get(position));
                    return true;
                }
                return false;
            }
        });




    }

    @Override
    public int getItemCount() {
        return listOperaciones.size();
    }

    public class ViewHolderTareasAsignadas extends RecyclerView.ViewHolder {
        TextView producto, subparte, operaciones, idLotesOperaciones,
                cantidad, empleado, id_operaciones_subparte_producto,
                nombre, apellidos, lote, completado ;


        public ViewHolderTareasAsignadas(@NonNull View itemView) {
            super(itemView);

            producto = itemView.findViewById(R.id.producto);
            subparte = itemView.findViewById(R.id.subparte);
            operaciones = itemView.findViewById(R.id.operaciones);
            idLotesOperaciones = itemView.findViewById(R.id.id_lotes_operaciones);
            cantidad = itemView.findViewById(R.id.cantidad);
            empleado = itemView.findViewById(R.id.empleado);
            id_operaciones_subparte_producto = itemView.findViewById(R.id.id_operaciones_subparte_producto);
            nombre= itemView.findViewById(R.id.nombre);
            apellidos=itemView.findViewById(R.id.apellido);
            lote= itemView.findViewById(R.id.edtlote);
            completado= itemView.findViewById(R.id.edtcompletado);

        }

        public void filtrado(String txtBuscar) {
            int longitud = txtBuscar.length();
            if (longitud == 0) {
                listOperaciones.clear();
                listOperaciones.addAll(buscador);
            } else {
                List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                        (i -> i.getOperaciones().toLowerCase().contains(txtBuscar.toLowerCase()) ||
                                i.getProducto().toLowerCase().contains(txtBuscar.toLowerCase()) ||
                                i.getSubparte().toLowerCase().contains(txtBuscar.toLowerCase())||
                                String.valueOf(i.getCantidad()).toLowerCase().contains(txtBuscar.toLowerCase())||
                                String.valueOf(i.getLotes()).toLowerCase().contains(txtBuscar.toLowerCase())||
                                i.getCompletado().toLowerCase().contains(txtBuscar.toLowerCase())||
                                i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())

                        ).collect(Collectors.toList());
                listOperaciones.clear();
                listOperaciones.addAll(coleccion);
            }
            notifyDataSetChanged();
        }
    }
}
