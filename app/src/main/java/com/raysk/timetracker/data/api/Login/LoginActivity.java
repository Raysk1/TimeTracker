package com.raysk.timetracker.data.api.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.raysk.timetracker.R;

public class LoginActivity extends AppCompatActivity {
    EditText etUserName, etPassword;
    Button btnLogin;
    TextView tvPassword,tvRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName  = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvPassword = findViewById(R.id.btnLogin);
        tvRegistrar = findViewById(R.id.tvRegistrar);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}