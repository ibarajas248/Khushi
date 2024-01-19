package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;

public class Adapter_menu extends RecyclerView.Adapter<Adapter_menu.viewHolderMenu>  implements View.OnClickListener{

    private String[] menuItems;
    private View.OnClickListener listener;


    private AdapterView.OnItemClickListener onItemClickListener;
    public Adapter_menu(String[] menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public viewHolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_menu, parent, false);
        view.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

    }


    public class viewHolderMenu extends RecyclerView.ViewHolder {
        TextView menuItem;

        public viewHolderMenu(@NonNull View itemView) {
            super(itemView);
            menuItem = itemView.findViewById(R.id.idNombre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }

                }
            });

        }

        public void bind(String item) {
            menuItem.setText(item);
        }


    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
