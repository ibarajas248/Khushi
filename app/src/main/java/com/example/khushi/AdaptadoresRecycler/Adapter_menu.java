package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;

public class Adapter_menu extends RecyclerView.Adapter<Adapter_menu.viewHolderMenu> {
    private String[] menuItems;

    public Adapter_menu(String[] menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public viewHolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_menu, parent, false);
        return new viewHolderMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderMenu holder, int position) {
        holder.bind(menuItems[position]);
    }

    @Override
    public int getItemCount() {
        return menuItems.length;
    }

    public class viewHolderMenu extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView menuItem;

        public viewHolderMenu(@NonNull View itemView) {
            super(itemView);
            menuItem = itemView.findViewById(R.id.idNombre);
            itemView.setOnClickListener(this);
        }

        public void bind(String item) {
            menuItem.setText(item);
        }

        @Override
        public void onClick(View v) {
            // Manejar clics en los elementos del menú si es necesario
        }
    }
}
