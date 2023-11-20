package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.nuevaSubParte;

import java.util.ArrayList;

public class AdapterSubParte extends RecyclerView.Adapter<AdapterSubParte.ViewHoldersubparte>implements View.OnClickListener {
    ArrayList<nuevaSubParte> listSubParte;
    private View.OnClickListener listener;

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
        holder.id_producto.setText(String.valueOf(listSubParte.get(position).getId_producto()));
        holder.id_subparte.setText(String.valueOf(listSubParte.get(position).getId_subparte()));
        holder.subparte.setText(listSubParte.get(position).getSubparte());

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
