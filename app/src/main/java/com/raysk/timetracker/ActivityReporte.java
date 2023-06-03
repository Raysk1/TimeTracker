package com.raysk.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class ActivityReporte extends AppCompatActivity {
    private EditText ETTema;
    private EditText ETContenido;
    private Button BTNEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETTema.findViewById(R.id.ETTema);
        ETContenido.findViewById(R.id.ETContenido);

        BTNEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new intent(MainActivity.this, ().class);
              //  startActivity(intent);
            }
        });

    }


}