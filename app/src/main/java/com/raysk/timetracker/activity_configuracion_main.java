package com.raysk.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_configuracion_main extends AppCompatActivity {
    Button btnConfigurancionNombre, btnConfigurancionEmail, getBtnConfigurancionContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_main);
        btnConfigurancionNombre = findViewById(R.id.btnNombre);
        btnConfigurancionEmail = findViewById(R.id.btnConfigurancionEmail);
        getBtnConfigurancionContraseña = findViewById(R.id.btnConfigurancionContraseña);
        btnConfigurancionNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_configuracion_main.this, configurancionnombre.class);
                startActivity(intent);
            }
        });
        btnConfigurancionEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_configuracion_main.this, ConfigurancionEMail.class);
                startActivity(intent);
            }
        });
        getBtnConfigurancionContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_configuracion_main.this,ConfigurancionPassword.class);
                startActivity(intent);
            }
        });
    }
}