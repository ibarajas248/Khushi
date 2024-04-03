package com.example.khushi.AdaptadoresRecycler;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import java.util.stream.Collectors;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khushi.Activity.agregarProducto;
import com.example.khushi.Activity.agregar_producto_oc;
import com.example.khushi.Activity.operaciones_lotes;
import com.example.khushi.R;
import com.example.khushi.clasesinfo.Empleado_clase;
import com.example.khushi.clasesinfo.operacionesFiltradas;
import com.example.khushi.clasesinfo.operaciones_lotes_clase;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Adapter_operaciones_lotes extends RecyclerView
        .Adapter<Adapter_operaciones_lotes.ViewHolderOperacionesLotes> implements View.OnClickListener{


    ArrayList<operaciones_lotes_clase> listOperaciones;
    ArrayList<operaciones_lotes_clase> buscador; //Array para el searchView
    private int selectedItem = RecyclerView.NO_POSITION;//almacena la posicion del elemento seleccionado


    private View.OnClickListener listener;

    private int selectedPosition = RecyclerView.NO_POSITION; //variable para cambiar de color de la fila

    private OnItemLongClickListener itemLongClickListener;





    // Agregar una lista para los datos del Spinner
    List<String> spinnerDataList;

    List<Integer> spinnerIdsList; // Lista para almacenar IDs

    List<Empleado_clase>listaEmpleados;

    int id_lotes_operaciones;
    private Context context;






    public Adapter_operaciones_lotes(Context context, ArrayList<operaciones_lotes_clase> listOperaciones,
                                     List<String> spinnerDataList, List<Integer> spinnerIdsList, ArrayList<Empleado_clase> listaEmpleados) {
        this.context = context;
        this.listOperaciones = listOperaciones;
        this.spinnerDataList = spinnerDataList; // Inicializar la lista para el Spinner
        this.spinnerIdsList = spinnerIdsList; // Inicializa la lista de IDs
        this.listaEmpleados = listaEmpleados; //inicializa el objeto empleados
        this.id_lotes_operaciones = -1;

        //inicializo el buscador
        buscador = new ArrayList<>();
        buscador.addAll(listOperaciones);
    }




    @NonNull
    @Override
    public Adapter_operaciones_lotes.ViewHolderOperacionesLotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_operaciones_lote,null,false);
        // escucha el evento de seleccion
        view.setOnClickListener(this);
        return new Adapter_operaciones_lotes.ViewHolderOperacionesLotes(view);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderOperacionesLotes holder, int position) {


        holder.habilitado.setVisibility(View.GONE);


        holder.producto.setText(listOperaciones.get(position).getProducto());

        holder.subparte.setText(listOperaciones.get(position).getSubparte());

        holder.operaciones.setText(listOperaciones.get(position).getOperaciones());
        holder.idLotesOperaciones.setText(String.valueOf(listOperaciones.get(position).getId_lotes_operaciones()));
        holder.cantidad.setText(String.valueOf(listOperaciones.get(position).getCantidad()));
        holder.empleado.setText(String.valueOf(listOperaciones.get(position).getEmpleado()));
        holder.id_operaciones_subparte_producto.setText(String.valueOf(listOperaciones.get(position).getId_operacione_subparte_producto()));
        holder.lote.setText(String.valueOf(listOperaciones.get(position).getLotes()));
        holder.id_producto_oc.setText(String.valueOf(listOperaciones.get(position).getId_producto_oc()));

        //para cambiar de color tendria que hacere otra referenca distinta custom_spinner_item
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(holder.itemView.getContext(),
                R.layout.spinner_gris, spinnerDataList);
        ArrayAdapter<String> selectedSpinnerAdapter = new ArrayAdapter<String>(holder.itemView.getContext(),
                R.layout.spinner_rojo, spinnerDataList);
        ArrayAdapter<String> completoSpinnerAdapter = new ArrayAdapter<String>(holder.itemView.getContext(),
                R.layout.spinner_verde, spinnerDataList);

        ArrayAdapter<String> Spinner_opHabilitado  = new ArrayAdapter<String>(holder.itemView.getContext(),
                R.layout.spinner_amarillo, spinnerDataList);
        ArrayAdapter<String> Spinner_Blanco  = new ArrayAdapter<String>(holder.itemView.getContext(),
                R.layout.spinner_blanco, spinnerDataList);

        holder.completado.setText(listOperaciones.get(position).getCompletado());

        int operacionPosition = holder.getAdapterPosition();


        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


                if (pos > 0 ) {

                    int selectedPosition = holder.getAdapterPosition();
                    //int selectedId = spinnerIdsList.get(pos-1);
                    String selectid = (listaEmpleados.get(pos-1).getId());
                    String selectnombre= listaEmpleados.get(pos-1).getNombre();

                    int id_operacione= listOperaciones.get(position).getId_lotes_operaciones();


                    Log.d("seleccion",selectid+" "+selectnombre);


                    id_lotes_operaciones= listOperaciones.get(position).getId_lotes_operaciones();
                    String id_lotes_operacionesString= String.valueOf(id_lotes_operaciones);
                    //operaciones_lotes instancia = new operaciones_lotes();
                    operaciones_lotes instancia = (operaciones_lotes) context;

                    //Toast.makeText(instancia, parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(instancia, id_lotes_operacionesString, Toast.LENGTH_SHORT).show();


                    String URL = "http://khushiconfecciones.com//app_khushi/consultas_lotes/asignar_empleado_lote.php?id_lotes_operaciones="+id_lotes_operacionesString;


                    instancia.agregarEmpleado(URL,Integer.parseInt(selectid),selectnombre, id_lotes_operacionesString);
                    holder.empleado.setText(String.valueOf(selectid));

                }


                else {
                    Log.d("Error", "Índice de Spinner fuera de rango");
                }



                // Realizar acciones con el elemento seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Acciones cuando no se selecciona nada
            }
        });





        if (position == selectedPosition) {
            //holder.habilitado.setBackgroundColor(0xFFE82900);
            holder.producto.setBackgroundColor(0xFFE82900);
            holder.subparte.setBackgroundColor(0xFFE82900);
            holder.operaciones.setBackgroundColor(0xFFE82900);
            holder.idLotesOperaciones.setBackgroundColor(0xFFE82900);
            holder.cantidad.setBackgroundColor(0xFFE82900);
            holder.empleado.setBackgroundColor(0xFFE82900);
            holder.id_operaciones_subparte_producto.setBackgroundColor(0xFFE82900);
            holder.lote.setBackgroundColor(0xFFE82900);
            holder.spinner.setAdapter(selectedSpinnerAdapter);//configuro el color del spinnr

            holder.completado.setBackgroundColor(0xFFE82900);

            if ("no asignado".equals(listOperaciones.get(position).getEmpleado())) {
                holder.spinner.setAdapter(selectedSpinnerAdapter);


            } else {
                holder.spinner.setAdapter(selectedSpinnerAdapter);
                // Toma el valor de la celda
                String empleado = listOperaciones.get(position).getEmpleado();


                int posicionPredeterminada = 0;
                // Iterar a través de la lista de empleados
                for (Empleado_clase empleado1 : listaEmpleados) {
                    // Obtener el valor del atributo 'id' para el empleado actual
                    String idEmpleadoActual = empleado1.getId();

                    // Comparar el atributo 'id' con la variable 'idBuscado'
                    if (empleado.equals(idEmpleadoActual)) {
                        // El atributo 'id' del empleado actual es igual a 'idBuscado'
                        // Realizar las acciones necesarias aquí
                        // ...
                        holder.spinner.setSelection(posicionPredeterminada+1);
                        break; // Puedes salir del bucle si encuentras una coincidencia, si lo deseas.
                    }
                    posicionPredeterminada=posicionPredeterminada+1;
                }



            }


        } else {

            if (position % 2 == 0) {
                // Si la posición es par
                holder.producto.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.subparte.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.operaciones.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.idLotesOperaciones.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.cantidad.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.empleado.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.id_operaciones_subparte_producto.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.lote.setBackgroundColor(0xFFD9D9D9); // Gris
                holder.completado.setBackgroundColor(0xFFD9D9D9); // Gris

                if ("no asignado".equals(listOperaciones.get(position).getEmpleado())) {
                    holder.spinner.setAdapter(spinnerAdapter);


                } else {
                    holder.spinner.setAdapter(spinnerAdapter);
                    // Toma el valor de la celda
                    String empleado = listOperaciones.get(position).getEmpleado();


                    int posicionPredeterminada = 0;
                    // Iterar a través de la lista de empleados
                    for (Empleado_clase empleado1 : listaEmpleados) {
                        // Obtener el valor del atributo 'id' para el empleado actual
                        String idEmpleadoActual = empleado1.getId();

                        // Comparar el atributo 'id' con la variable 'idBuscado'
                        if (empleado.equals(idEmpleadoActual)) {
                            // El atributo 'id' del empleado actual es igual a 'idBuscado'
                            // Realizar las acciones necesarias aquí
                            // ...
                            holder.spinner.setSelection(posicionPredeterminada+1);
                            break; // Puedes salir del bucle si encuentras una coincidencia, si lo deseas.
                        }
                        posicionPredeterminada=posicionPredeterminada+1;
                    }



                }
            } else {
                // Si la posición es impar
                holder.producto.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.subparte.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.operaciones.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.idLotesOperaciones.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.cantidad.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.empleado.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.id_operaciones_subparte_producto.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.lote.setBackgroundColor(0xFFFFFFFF); // Otro color
                holder.completado.setBackgroundColor(0xFFFFFFFF); // Otro color



                if ("no asignado".equals(listOperaciones.get(position).getEmpleado())) {
                    holder.spinner.setAdapter(Spinner_Blanco);


                } else {
                    holder.spinner.setAdapter(Spinner_Blanco);
                    // Toma el valor de la celda
                    String empleado = listOperaciones.get(position).getEmpleado();


                    int posicionPredeterminada = 0;
                    // Iterar a través de la lista de empleados
                    for (Empleado_clase empleado1 : listaEmpleados) {
                        // Obtener el valor del atributo 'id' para el empleado actual
                        String idEmpleadoActual = empleado1.getId();

                        // Comparar el atributo 'id' con la variable 'idBuscado'
                        if (empleado.equals(idEmpleadoActual)) {
                            // El atributo 'id' del empleado actual es igual a 'idBuscado'
                            // Realizar las acciones necesarias aquí
                            // ...
                            holder.spinner.setSelection(posicionPredeterminada+1);
                            break; // Puedes salir del bucle si encuentras una coincidencia, si lo deseas.
                        }
                        posicionPredeterminada=posicionPredeterminada+1;
                    }



                }
            }






            //colores para cuando este completo verde

            if (listOperaciones.get(position).getCompletado().equalsIgnoreCase("si")){

                holder.producto.setBackgroundColor(0xFFA6E196);
                holder.subparte.setBackgroundColor(0xFFA6E196);
                holder.operaciones.setBackgroundColor(0xFFA6E196);
                holder.idLotesOperaciones.setBackgroundColor(0xFFA6E196);
                holder.cantidad.setBackgroundColor(0xFFA6E196);
                holder.empleado.setBackgroundColor(0xFFA6E196);
                holder.id_operaciones_subparte_producto.setBackgroundColor(0xFFA6E196);
                holder.lote.setBackgroundColor(0xFFA6E196);
                holder.spinner.setAdapter(completoSpinnerAdapter);//configuro el color del spinnr
                holder.completado.setBackgroundColor(0xFFA6E196);

                if ("no asignado".equals(listOperaciones.get(position).getEmpleado())) {
                    holder.spinner.setAdapter(completoSpinnerAdapter);
                } else {
                    holder.spinner.setAdapter(completoSpinnerAdapter);
                    // Toma el valor de la celda
                    String empleado = listOperaciones.get(position).getEmpleado();
                    int posicionPredeterminada = 0;
                    // Iterar a través de la lista de empleados
                    for (Empleado_clase empleado1 : listaEmpleados) {
                        // Obtener el valor del atributo 'id' para el empleado actual
                        String idEmpleadoActual = empleado1.getId();

                        // Comparar el atributo 'id' con la variable 'idBuscado'
                        if (empleado.equals(idEmpleadoActual)) {
                            // El atributo 'id' del empleado actual es igual a 'idBuscado'
                            // Realizar las acciones necesarias aquí
                            // ...
                            holder.spinner.setSelection(posicionPredeterminada+1);
                            break; // Puedes salir del bucle si encuentras una coincidencia, si lo deseas.
                        }
                        posicionPredeterminada=posicionPredeterminada+1;
                    }

                }
            }
            //Amarillo cuando está en proceso la operacion, es decir habilitado
           if (listOperaciones.get(position).getHabilitado().equalsIgnoreCase("si")&&
                    listOperaciones.get(position).getCompletado().equalsIgnoreCase("no")){


                holder.producto.setBackgroundColor(0x60FFEB15);
                holder.subparte.setBackgroundColor(0x60FFEB15);
                holder.operaciones.setBackgroundColor(0x60FFEB15);
                holder.idLotesOperaciones.setBackgroundColor(0x60FFEB15);
                holder.cantidad.setBackgroundColor(0x60FFEB15);
                holder.empleado.setBackgroundColor(0x60FFEB15);
                holder.id_operaciones_subparte_producto.setBackgroundColor(0x60FFEB15);
                holder.lote.setBackgroundColor(0x60FFEB15);
                holder.spinner.setAdapter(Spinner_opHabilitado);//configuro el color del spinnr
                holder.completado.setBackgroundColor(0x60FFEB15);
               if ("no asignado".equals(listOperaciones.get(position).getEmpleado())) {
                       holder.spinner.setAdapter(Spinner_opHabilitado);
               } else {
                   holder.spinner.setAdapter(Spinner_opHabilitado);
                   // Toma el valor de la celda
                   String empleado = listOperaciones.get(position).getEmpleado();
                   int posicionPredeterminada = 0;
                   // Iterar a través de la lista de empleados
                   for (Empleado_clase empleado1 : listaEmpleados) {
                       // Obtener el valor del atributo 'id' para el empleado actual
                       String idEmpleadoActual = empleado1.getId();

                       // Comparar el atributo 'id' con la variable 'idBuscado'
                       if (empleado.equals(idEmpleadoActual)) {
                           // El atributo 'id' del empleado actual es igual a 'idBuscado'
                           // Realizar las acciones necesarias aquí
                           // ...
                           holder.spinner.setSelection(posicionPredeterminada+1);
                           break; // Puedes salir del bucle si encuentras una coincidencia, si lo deseas.
                       }
                       posicionPredeterminada=posicionPredeterminada+1;
                   }

               }

            }


            // Restaura el color de fondo del Spinner manipulando directamente sus elementos


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
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(listOperaciones.get(position));
                    return true;
                }
                return false;
            }
        });




    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return listOperaciones.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
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




    public class ViewHolderOperacionesLotes extends RecyclerView.ViewHolder {

        TextView habilitado, producto,subparte, operaciones,idLotesOperaciones,
                cantidad, empleado, id_operaciones_subparte_producto,
                lote,id_producto_oc, completado ;
        Spinner spinner;
        String dato="holesa";



        public ViewHolderOperacionesLotes(@NonNull View itemView) {
            super(itemView);


            habilitado=itemView.findViewById(R.id.tvHabilitado);
            producto=itemView.findViewById(R.id.producto);
            subparte=itemView.findViewById(R.id.subparte);
            operaciones=itemView.findViewById(R.id.operaciones);
            idLotesOperaciones=itemView.findViewById(R.id.id_lotes_operaciones);
            cantidad=itemView.findViewById(R.id.cantidad);
            empleado= itemView.findViewById(R.id.empleado);
            id_operaciones_subparte_producto=itemView.findViewById(R.id.id_operaciones_subparte_producto);
            spinner = itemView.findViewById(R.id.spinner2);
            lote= itemView.findViewById(R.id.edtlote);
            id_producto_oc= itemView.findViewById(R.id.id_producto_oc);
            completado= itemView.findViewById(R.id.edtcompletado);



        }
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
                            String.valueOf(i.getNombre()).toLowerCase().contains(txtBuscar.toLowerCase())||
                            String.valueOf(i.getApellido()).toLowerCase().contains(txtBuscar.toLowerCase())||
                            String.valueOf(i.getLotes()).toLowerCase().contains(txtBuscar.toLowerCase())||
                            i.getCompletado().toLowerCase().contains(txtBuscar.toLowerCase())

                    ).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void filtradoHabilitado(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i ->
                            i.getHabilitado().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());

            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
    }

    public void filtradoProducto(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> i.getProducto().toLowerCase().contains(txtBuscar.toLowerCase())

                    ).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }
    public void filtradoSeccion(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> i.getSubparte().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }
    public void filtradoOperacion(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> i.getOperaciones().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void filtradoCantidad(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> String.valueOf(i.getCantidad()).toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void filtradoNombre(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void filtradoApellido(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> i.getApellido().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void filtradoCompletado(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> i.getCompletado().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();
    }
    public void filtradoLote(String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listOperaciones.clear();
            listOperaciones.addAll(buscador);
        } else {
            List<operaciones_lotes_clase> coleccion = listOperaciones.stream().filter
                    (i -> String.valueOf(i.getLotes()).toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listOperaciones.clear();
            listOperaciones.addAll(coleccion);
        }
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener {
        void onItemLongClick(operaciones_lotes_clase asignacion);
    }









}
