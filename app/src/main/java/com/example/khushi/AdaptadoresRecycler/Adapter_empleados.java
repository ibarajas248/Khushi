package com.example.khushi.AdaptadoresRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.Filtro;
import com.example.khushi.clasesinfo.nuevoProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter_empleados extends RecyclerView.Adapter<Adapter_empleados.ViewHolderempleados> implements View.OnClickListener {

    ArrayList<Empleado_clase> listaEmpleados;
    ArrayList<Empleado_clase> listaEmpleadosFiltrado;
    private View.OnClickListener listener;

    public Adapter_empleados (ArrayList<Empleado_clase>listaEmpleados){
        this.listaEmpleados=listaEmpleados;
        this.listaEmpleadosFiltrado = new ArrayList<>(listaEmpleados); // Inicializa la lista filtrada
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public Adapter_empleados.ViewHolderempleados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empleados,null,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empleados, parent, false);

        view.setOnClickListener(this);

        return new ViewHolderempleados(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_empleados.ViewHolderempleados holder, int position) {
        holder.nombre.setText(String.valueOf(listaEmpleados.get(position).getNombre()));
        holder.Apellidos.setText(String.valueOf(listaEmpleados.get(position).getApellidos()));
        holder.correo.setText(String.valueOf(listaEmpleados.get(position).getCorreoElectronico()));
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;

    }

    public class ViewHolderempleados extends RecyclerView.ViewHolder {
        TextView nombre, Apellidos, correo;
        public ViewHolderempleados(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre);
            Apellidos= itemView.findViewById(R.id.Apellidos);
            correo= itemView.findViewById(R.id.Correo_Electronico);
        }
    }

    //filtrado general

    public void filtrado(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaEmpleados.clear();
            listaEmpleados.addAll( listaEmpleadosFiltrado);
        } else {
            List<Empleado_clase> coleccion = listaEmpleados.stream().filter
                    (i ->
                    i.getApellidos().toLowerCase().contains(txtBuscar.toLowerCase()) ||
                    i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()) ||
                    i.getCorreoElectronico().toLowerCase().contains(txtBuscar.toLowerCase())
                    ).collect(Collectors.toList());

            listaEmpleados.clear();
            listaEmpleados.addAll(coleccion);
        }
        notifyDataSetChanged();
    }


    public void filtrado(List<Filtro> filtros) {
        if (filtros.isEmpty()) {
            // Si no hay filtros, restaurar la lista original
            listaEmpleados.clear();
            listaEmpleados.addAll(listaEmpleadosFiltrado);
        } else {
            // Filtrar según los criterios
            List<Empleado_clase> coleccion = listaEmpleadosFiltrado.stream()
                    .filter(empleado -> {
                        boolean matches = true;  // Asumimos que coincide inicialmente
                        for (Filtro filtro : filtros) {
                            switch (filtro.getCampo().toLowerCase()) {  // Convertimos el campo a minúsculas
                                case "apellido":
                                    matches &= empleado.getApellidos().toLowerCase().contains(filtro.getValor().toLowerCase());
                                    break;
                                case "nombre":
                                    matches &= empleado.getNombre().toLowerCase().contains(filtro.getValor().toLowerCase());
                                    break;
                                case "correo":
                                    matches &= empleado.getCorreoElectronico().toLowerCase().contains(filtro.getValor().toLowerCase());
                                    break;

                                // Agrega más criterios según sea necesario
                            }
                        }
                        return matches;  // Retorna si coincide con todos los filtros
                    })
                    .collect(Collectors.toList());

            listaEmpleados.clear();
            listaEmpleados.addAll(coleccion);
        }
        notifyDataSetChanged();
    }
    // Dentro de la clase Adapter_empleados
    public void filtrado(String txtBuscar, String criterio) {
        txtBuscar = txtBuscar.trim().toLowerCase(); // Eliminar espacios y convertir a minúsculas
        List<Empleado_clase> coleccion = new ArrayList<>(listaEmpleadosFiltrado); // Copiar la lista original

        if (txtBuscar.isEmpty()) {
            listaEmpleados.clear();
            listaEmpleados.addAll(listaEmpleadosFiltrado); // Muestra todos los empleados si no hay texto de búsqueda
        } else {
            listaEmpleados.clear(); // Limpiar la lista actual

            for (Empleado_clase empleado : coleccion) {
                boolean match = false; // Variable para verificar si hay coincidencia

                // Filtrar según el criterio
                switch (criterio) {
                    case "Nombre":
                        if (empleado.getNombre() != null) {
                            match = empleado.getNombre().toLowerCase().contains(txtBuscar);
                        }
                        break;
                    case "Apellido":
                        if (empleado.getApellidos() != null) {
                            match = empleado.getApellidos().toLowerCase().contains(txtBuscar);
                        }
                        break;
                    case "Correo":
                        if (empleado.getCorreoElectronico() != null) {
                            match = empleado.getCorreoElectronico().toLowerCase().contains(txtBuscar);
                        }
                        break;
                }

                // Si hay coincidencia, agregar a la lista
                if (match) {
                    listaEmpleados.add(empleado);
                }
            }
        }

        notifyDataSetChanged(); // Notificar cambios en el adaptador
    }
    public void filtrado234(String txtBuscar, List<String> criterios) {
        txtBuscar = txtBuscar.trim().toLowerCase(); // Eliminar espacios y convertir a minúsculas
        List<Empleado_clase> coleccion = new ArrayList<>(listaEmpleadosFiltrado); // Copiar la lista original

        if (txtBuscar.isEmpty()) {
            listaEmpleados.clear();
            listaEmpleados.addAll(listaEmpleadosFiltrado); // Muestra todos los empleados si no hay texto de búsqueda
        } else {
            listaEmpleados.clear(); // Limpiar la lista actual

            for (Empleado_clase empleado : coleccion) {
                boolean match = false; // Variable para verificar si hay coincidencia

                // Filtrar según los criterios
                for (String criterio : criterios) {
                    switch (criterio) {
                        case "Nombre":
                            if (empleado.getNombre() != null && empleado.getNombre().toLowerCase().contains(txtBuscar)) {
                                match = true; // Coincide con el criterio
                            }
                            break;
                        case "Apellido":
                            if (empleado.getApellidos() != null && empleado.getApellidos().toLowerCase().contains(txtBuscar)) {
                                match = true; // Coincide con el criterio
                            }
                            break;
                        case "Correo":
                            if (empleado.getCorreoElectronico() != null && empleado.getCorreoElectronico().toLowerCase().contains(txtBuscar)) {
                                match = true; // Coincide con el criterio
                            }
                            break;
                    }
                    // Si ya hay una coincidencia, no es necesario seguir revisando más criterios
                    if (match) {
                        break;
                    }
                }

                // Si hay coincidencia, agregar a la lista
                if (match) {
                    listaEmpleados.add(empleado);
                }
            }
        }

        notifyDataSetChanged(); // Notificar cambios en el adaptador
    }






}
