package com.raysk.timetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

public class ActivityFormulario extends AppCompatActivity {
    private Button btnFecha,btnHora, btnSolicitar;

    private EditText etHora, etFecha;
    private TextView tvSeleccion,tvMateria,tvMaestro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnHora);
        btnSolicitar = findViewById(R.id.btnSolicitar);
        etHora = findViewById(R.id.etHora);
        etFecha = findViewById(R.id.etFecha);
        tvSeleccion = findViewById(R.id.tvSeleccion);
        tvMateria = findViewById(R.id.etMateria);
        tvMaestro = findViewById(R.id.etNombreMaestro);


        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECCIONA EL DIA");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                etFecha.setText(materialDatePicker.getHeaderText());
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityFormulario.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                                etHora.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityFormulario.this, "Solicitud Enviada", Toast.LENGTH_LONG).show();
            }
        });
    }
}