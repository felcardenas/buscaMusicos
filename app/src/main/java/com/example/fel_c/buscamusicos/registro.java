package com.example.fel_c.buscamusicos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class registro extends AppCompatActivity {

    EditText etAlias;
    EditText etCorreo;
    ImageButton ibFecha;
    TextView tvFecha;

    int dia,mes,año;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etAlias = findViewById(R.id.etAlias);
        etCorreo = findViewById(R.id.etCorreo);
        ibFecha = findViewById(R.id.ibFechaDeNacimiento);
        tvFecha = findViewById(R.id.tvFechaDeNacimiento);

        //EJECUTA EL BOTÓN DE EDITAR FECHA
        ibFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                año = c.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(registro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String stringMonth, stringDay;


                        month = month + 1;
                        stringMonth = ""+month;
                        stringDay = ""+dayOfMonth;

                        if(month < 10){

                            stringMonth = "0" + month;

                        }
                        if(dayOfMonth < 10){

                            stringDay  = "0" + dayOfMonth;
                        }

                        tvFecha.setText(stringDay + "/" + stringMonth + "/" + year);
                    }
                },dia,mes,año);
                datePickerDialog.show();

            }
        });


    }
}
