    package com.example.khushi.AdaptadoresRecycler;
    
    import android.app.Activity;
    import android.content.Context;
    import android.Manifest;
    
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.Bitmap;
    import android.os.Bundle;
    import android.os.Environment;
    import android.provider.MediaStore;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import androidx.annotation.NonNull;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;
    import androidx.recyclerview.widget.RecyclerView;


    import com.android.volley.Response;
    import com.bumptech.glide.request.target.Target;
    import com.example.khushi.Activity.Home;
    import com.example.khushi.Activity.consultar_tareas_asignadas;
    import com.example.khushi.Activity.imagen_producto;
    import com.example.khushi.R;
    import com.example.khushi.clasesinfo.nuevoProducto;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.ArrayList;
    import com.bumptech.glide.Glide;
    import com.bumptech.glide.request.RequestOptions;

    import org.json.JSONArray;

    public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos>implements View.OnClickListener {
        private static final int CODIGO_RESULTADO_CAMARA = 1; // Definir el código de resultado para la cámara
        private static final int CODIGO_DE_SOLICITUD_DE_CAMARA = 2; // Definir el código de solicitud de permisos de la cámara
        ArrayList<nuevoProducto> listDatos;
        private View.OnClickListener listener;
        private OnItemLongClickListener itemLongClickListener;
        private String ROL;
        private Context context; // Añadir un campo de Context




        public interface OnItemLongClickListener {
            void onItemLongClick(nuevoProducto producto);
        }
    
        public void setOnItemLongClickListener(OnItemLongClickListener listener) {
            this.itemLongClickListener = listener;
        }
        public AdapterDatos(ArrayList<nuevoProducto> listDatos) {
            this.listDatos = listDatos;
        }
        public AdapterDatos(ArrayList<nuevoProducto> listDatos, String ROL) {
            this.listDatos = listDatos;
            this.ROL=ROL;
        }
        public AdapterDatos(Context context,ArrayList<nuevoProducto> listDatos, String ROL) {
            this.context = context; // Inicializar el campo de Context
            this.listDatos = listDatos;
            this.ROL=ROL;
        }
    
        @NonNull
        @Override
        public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    
            //Context context = parent.getContext(); // Obtén el contexto desde el ViewGroup
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_producto,parent,false);

    
            // escucha el evento de seleccion
            view.setOnClickListener(this);
            return new ViewHolderDatos(view);
    
        }
    
        @Override
        public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
    

            holder.id_producto.setText(String.valueOf(listDatos.get(position).getId_producto()));
            holder.producto.setText(listDatos.get(position).getProducto());
            holder.precio.setText(String.valueOf(listDatos.get(position).getPrecio()));
            if (ROL.equalsIgnoreCase("SUPERVISOR")||ROL.equalsIgnoreCase("OPERARIO")){
                holder.precio.setVisibility(View.GONE);
            }
            // Verificar si la posición es par o impar
            if (position % 2 == 0) {
                // Para posiciones pares, cambia el color de fondo o el color del texto
                holder.itemView.setBackgroundColor(0xFFACA89C);
    
                holder.producto.setTextColor(0xFF000000);
            } else {
                // Para posiciones impares, otro color diferente
                holder.itemView.setBackgroundColor(0xFFEFE1AD);
                // o
                holder.producto.setTextColor(0xFF000000);
            };
    
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemLongClickListener != null) {
                        itemLongClickListener.onItemLongClick(listDatos.get(position));
                        return true;
                    }
                    return false;
                }
            });
    
            holder.imagenProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // abrirCamara(holder.itemView.getContext(), listDatos.get(holder.getAdapterPosition()));

                    Intent intent = new Intent(v.getContext(), imagen_producto.class);
                    intent.putExtra("id_producto",String.valueOf(listDatos.get(position).getId_producto()));
                    //intent.putExtra("id_producto",String.valueOf(listDatos.get(recycler.getChildAdapterPosition(v)).getId_producto()));
                    v.getContext().startActivity(intent);
                }
            });

            // Cargar imagen desde Internet usando Glide y redimensionar
            String imageUrl = listDatos.get(position).getFoto();
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_menu_camera)
                            .error(R.drawable.ic_menu_camera))
                    .into(holder.imagenProducto);

            // ...





        }
    
        @Override
        public int getItemCount() {
            return listDatos.size();
        }
    
        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
    
    
        }
        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onClick(view);
            }
    
        }
    
        public class ViewHolderDatos extends RecyclerView.ViewHolder {
    
    
            TextView id_producto,producto,precio;
            ImageButton imagenProducto;
            public ViewHolderDatos(@NonNull View itemView) {
                super(itemView);
                //hago referencia a los datos que le llegan
                id_producto= itemView.findViewById(R.id.idproducto);
                producto=itemView.findViewById(R.id.producto);
                precio=itemView.findViewById(R.id.precioProducto);
                imagenProducto = itemView.findViewById(R.id.imageButton);
    
    
            }
            public View getItemView(){
                return itemView;
            }
    
    
        }
    
        private void abrirCamara(Context context, nuevoProducto producto) {
            // Verifica si el permiso de la cámara ya está concedido
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Si no está concedido, solicita el permiso
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CODIGO_DE_SOLICITUD_DE_CAMARA);
            } else {
                // El permiso ya está concedido, abre la cámara
                iniciarActividadCamara(context);
            }
        }
    
        private void iniciarActividadCamara(Context context) {
            Intent intentAbrirCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentAbrirCamara.resolveActivity(context.getPackageManager()) != null) {
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intentAbrirCamara, CODIGO_RESULTADO_CAMARA);
                }
            }
        }
    
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CODIGO_RESULTADO_CAMARA && resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // Guardar la imagen en un archivo
                //File imageFile = guardarImagenEnArchivo(imageBitmap);

                // Ahora puedes enviar este archivo al servidor
                //enviarImagenAlServidor(imageFile);
            }
        }


    }