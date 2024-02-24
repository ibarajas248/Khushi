package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.Activity.Home;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.menuClase;

import java.util.ArrayList;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.viewHolderMenu> implements View.OnClickListener{

    ArrayList<menuClase> listaMenu;


    private View.OnClickListener listener;
    //constructor


    public AdapterMenu(ArrayList<menuClase> listaMenu) {
        this.listaMenu = listaMenu;
    }


    @NonNull
    @Override
    public AdapterMenu.viewHolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_menu,null,false);

        view.setOnClickListener(this);
        return new viewHolderMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMenu.viewHolderMenu holder, int position) {
        holder.etiNombre.setText(listaMenu.get(position).getTitulo());
       //holder.foto.setImageResource(listaMenu.get(position).getFoto());

        // Verificar si la posición es par o impar
        if (position % 2 == 0) {
            // Para posiciones pares, cambia el color de fondo o el color del texto
            holder.itemView.setBackgroundColor(0xFF011336);
            holder.etiNombre.setTextColor(0xFFFFFFFF);
            //holder.producto.setTextColor(0xFF000000);
        } else {
            // Para posiciones impares, otro color diferente
            holder.itemView.setBackgroundColor(0xFFEFE1AD);
            // o
            //holder.etiNombre.setTextColor(0xFF000000);
        };



        holder.foto.setImageResource(listaMenu.get(position).getFoto());
        if (position % 2 == 0) {
            // Para posiciones pares, aplica un tinte a la imagen
            holder.foto.setColorFilter(0xFFFFFFFF); // Color de tinte para pares
        } else {
            // Para posiciones impares, otro color de tinte
            holder.foto.setColorFilter(0xFF011336); // Color de tinte para impares
        }


    }

    @Override
    public int getItemCount() {
        return listaMenu.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }
    public class viewHolderMenu  extends RecyclerView.ViewHolder{

        TextView etiNombre, etiInformacion;
        ImageView foto;
        public viewHolderMenu(@NonNull View itemView) {
            super(itemView);

            etiNombre=(TextView) itemView.findViewById(R.id.idNombre);
            foto= (ImageView)itemView.findViewById(R.id.idImagen);
            //etiInformacion=(TextView) itemView.findViewById(R.id.idInfo);
            //foto= (ImageView)itemView.findViewById(R.id.idImagen);
        }
    }
}
