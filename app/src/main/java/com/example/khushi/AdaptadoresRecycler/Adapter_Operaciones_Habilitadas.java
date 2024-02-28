package com.example.khushi.AdaptadoresRecycler;

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


public class Adapter_Operaciones_Habilitadas extends RecyclerView.Adapter<Adapter_Operaciones_Habilitadas.ViewHolderHabilitadas> implements View.OnClickListener {

    ArrayList<operaciones_lotes_clase> listDatos;

    private View.OnClickListener listener;
    private OnItemLongClickListener itemLongClickListener;



    //metodo constructor
    public Adapter_Operaciones_Habilitadas(ArrayList<operaciones_lotes_clase> listDatos) {
        this.listDatos = listDatos;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(operaciones_lotes_clase asignacion);
    }




    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }


    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



    @NonNull
    @Override
    public ViewHolderHabilitadas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_habilitadas,null,false);

        view.setOnClickListener(this);
        return new ViewHolderHabilitadas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Operaciones_Habilitadas.ViewHolderHabilitadas holder, int position) {

            holder.etiProducto.setText(listDatos.get(position).getProducto());
            holder.etseccion.setText(listDatos.get(position).getSubparte());
            holder.etoperacion.setText(listDatos.get(position).getOperaciones());
            holder.etcantidad.setText(String.valueOf(listDatos.get(position).getCantidad()));
            holder.etnombre.setText(listDatos.get(position).getNombre());
            holder.etapellido.setText(listDatos.get(position).getApellido());
            holder.etFecha.setText(listDatos.get(position).getFecha());
            //String habilitado= listDatos.get(position).getHabilitado();

        holder.itemView.setOnLongClickListener(v -> {
            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClick(listDatos.get(position));
                return true;
            }
            return false;
        });




    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderHabilitadas extends RecyclerView.ViewHolder {
        TextView etiProducto, etseccion, etoperacion, etcantidad, etnombre, etapellido, etFecha;

        public ViewHolderHabilitadas(@NonNull View itemView) {
            super(itemView);
            etiProducto=(TextView)itemView.findViewById(R.id.tvProduct);
            etseccion=(TextView)itemView.findViewById(R.id.tvSection);
            etoperacion=(TextView)itemView.findViewById(R.id.tvOperation);
            etcantidad=(TextView)itemView.findViewById(R.id.tvQuantity);
            etnombre=(TextView)itemView.findViewById(R.id.tvName);
            etapellido=(TextView)itemView.findViewById(R.id.tvLastName);
            etFecha=(TextView)itemView.findViewById(R.id.fecha);



        }
    }


}
