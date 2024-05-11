package com.example.khushi.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.khushi.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ingresar_fecha#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ingresar_fecha extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private static final String ARG_FECHA_INI = "fechaInicial";
    private static final String ARG_FECHA_FIN = "fecha_fin";

    private String mParam1;
    private String mParam2;



    String fechaInicial;
    String fechaFinal;

    EditText edtFechaInicial;
    EditText edtFechaFin;
    private Calendar calendar;
    private Button btnAceptar;

    public Fragment_ingresar_fecha() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    public static Fragment_ingresar_fecha newInstance(String fechaInicial, String fechaFinal) {
        Fragment_ingresar_fecha fragment = new Fragment_ingresar_fecha();
        Bundle args = new Bundle();
        args.putString(ARG_FECHA_INI, fechaInicial);
        args.putString(ARG_FECHA_FIN, fechaFinal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fechaInicial= getArguments().getString(ARG_FECHA_INI);
            fechaFinal = getArguments().getString(ARG_FECHA_FIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingresar_fecha, container, false);
        // Obtener referencias de los EditText
        edtFechaInicial = view.findViewById(R.id.fecha_inicio);
        edtFechaFin = view.findViewById(R.id.fecha_final);
        Button btnConfirmar = view.findViewById(R.id.BtnAceptar);
        // Configurar el OnClickListener para el botón
        btnConfirmar.setOnClickListener(v -> confirmarFecha());


        configEdtFechaInicial();
        configEdtFechaFinal();

        // Retornar la vista inflada
        return view;




    }

    private void configEdtFechaFinal() {
        edtFechaFin.setFocusable(false);
        // Crear un objeto Calendar
        calendar = Calendar.getInstance();
        //obtiene la fecha actual
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // Formatear la fecha actual
        String fechaActual = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
        // Establecer la fecha actual en el EditText
        edtFechaFin.setText(fechaActual);

        // Configurar el OnClickListener para el EditText
        edtFechaFin.setOnClickListener(v -> showDatePickerDialog(edtFechaFin));
    }

    private void configEdtFechaInicial() {
        edtFechaInicial.setFocusable(false);
        // Crear un objeto Calendar
        calendar = Calendar.getInstance();
        //obtiene la fecha actual
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Formatear la fecha actual
        String fechaActual = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);

        // Establecer la fecha actual en el EditText
        edtFechaInicial.setText(fechaActual);

        // Configurar el OnClickListener para el EditText
        edtFechaInicial.setOnClickListener(v -> showDatePickerDialog(edtFechaInicial));

    }

    private void showDatePickerDialog(EditText edt) {
        // Obtener el año, mes y día actuales
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear un DatePickerDialog con la fecha actual como predeterminada
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Formatear la fecha seleccionada al formato AAAA-MM-DD
                if (edt==edtFechaInicial){
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    Fragment_ingresar_fecha.this.edtFechaInicial.setText(fechaSeleccionada);
                }else if (edt==edtFechaFin){
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    Fragment_ingresar_fecha.this.edtFechaFin.setText(fechaSeleccionada);
                }

            }
        }, year, month, dayOfMonth);

        // Mostrar el diálogo de selección de fecha
        datePickerDialog.show();
    }






    // Define una interfaz para comunicarse con el activity principal
    public interface OnConfirmarClickListener {
        void onConfirmarClick(String fechaInicio, String fechaFin);
    }

    // Variable para almacenar la referencia del listener
    private OnConfirmarClickListener confirmarClickListener;

    // Método para establecer el listener
    public void setOnConfirmarClickListener(OnConfirmarClickListener listener) {
        this.confirmarClickListener = listener;
    }

    // Método para confirmar las fechas y enviarlas al activity principal
    private void confirmarFecha() {
        String fechaInicio = edtFechaInicial.getText().toString();
        String fechaFin = edtFechaFin.getText().toString();

        // Verificar que el listener no sea nulo y enviar las fechas al activity principal
        if (confirmarClickListener != null) {
            confirmarClickListener.onConfirmarClick(fechaInicio, fechaFin);
            //ce cierra el fragment
            getFragmentManager().beginTransaction().remove(Fragment_ingresar_fecha.this).commit();
        }
    }
}