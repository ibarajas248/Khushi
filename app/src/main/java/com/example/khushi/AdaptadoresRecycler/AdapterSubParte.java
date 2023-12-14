package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaSubParte;
import com.example.khushi.clasesinfo.nuevoProducto;

import java.util.ArrayList;

public class AdapterSubParte extends RecyclerView.Adapter<AdapterSubParte.ViewHoldersubparte>implements View.OnClickListener {
    ArrayList<nuevaSubParte> listSubParte;
    private View.OnClickListener listener;
    private AdapterSubParte.OnItemLongClickListener itemLongClickListener;
    public interface OnItemLongClickListener {
        void onItemLongClick(nuevaSubParte subparte);
    }
    public void setOnItemLongClickListener(AdapterSubParte.OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public AdapterSubParte(ArrayList<nuevaSubParte>listSubParte){
        this.listSubParte=listSubParte;
    }

    @Override

    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public AdapterSubParte.ViewHoldersubparte onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adptersubparte,null,false);



        view.setOnClickListener(this);

        return new ViewHoldersubparte(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubParte.ViewHoldersubparte holder, int position) {

        nuevaSubParte currentItem = listSubParte.get(position);
        holder.id_producto.setText(String.valueOf(listSubParte.get(position).getId_producto()));
        holder.id_subparte.setText(String.valueOf(listSubParte.get(position).getId_subparte()));
        holder.subparte.setText(listSubParte.get(position).getSubparte());

        // Verificar si la posición es par o impar
        if (position % 2 == 0) {
            // Para posiciones pares, cambia el color de fondo o el color del texto
            holder.itemView.setBackgroundColor(0xFFACA89C);

            holder.subparte.setTextColor(0xFF000000);
        } else {
            // Para posiciones impares, otro color diferente
            holder.itemView.setBackgroundColor(0xFFEFE1AD);
            // o
            holder.subparte.setTextColor(0xFF000000);
        };

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(listSubParte.get(position));
                    return true;
                }
                return false;
            }
        });

       /* if (position % 2 == 0) { // Elementos pares
            holder.itemView.setBackgroundColor(0xFFEDEDED);
        } else { // Elementos impares
            holder.itemView.setBackgroundColor(0xFFFFFFFF);
        }*/



    }

    @Override
    public int getItemCount() {
        return listSubParte.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;


    }





    public class ViewHoldersubparte extends RecyclerView.ViewHolder {

        TextView id_producto,id_subparte, subparte;
        public ViewHoldersubparte(@NonNull View itemView) {
            super(itemView);
            //hago referencia a los datos que le llegan
            id_producto=itemView.findViewById(R.id.idproducto_subparte);
            id_subparte=itemView.findViewById(R.id.idsubparte);
            subparte=itemView.findViewById(R.id.subparte);

        }
    }
}
