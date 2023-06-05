package com.raysk.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class ActivityReporte extends AppCompatActivity {
    private EditText ETTema;
    private EditText ETContenido;
    private Button BTNEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        ETTema = findViewById(R.id.ETTema);
        ETContenido = findViewById(R.id.ETContenido);
        BTNEnviar = findViewById(R.id.BTNEnviar);

        BTNEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bodyMail = ETContenido.getText().toString();
                // Aqui debe de ir el correo del usuario
                String mail = "Correo@gmail.com";
                String subject = ETTema.getText().toString();

                if (bodyMail.isEmpty() || subject.isEmpty()) {
                    Toast.makeText(ActivityReporte.this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mail));
                    Intent intentMailFull = new Intent(Intent.ACTION_SEND);
                    intentMailFull.setType("message/rfc822");
                    intentMailFull.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intentMailFull.putExtra(Intent.EXTRA_TEXT, bodyMail);
                    intentMailFull.putExtra(Intent.EXTRA_EMAIL, new String[]{mail, mail});
                    startActivity((intentMailFull));
                }
            }
        });

    }


}