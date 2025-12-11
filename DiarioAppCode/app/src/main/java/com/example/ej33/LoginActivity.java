package com.example.ej33;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextContrasena;
    private Button buttonIniciarSesion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        if (!prefs.contains("user")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", "admin");
            editor.putString("pass", "admin");
            editor.apply();
        }

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextUsuario.getText().toString().trim();
                String pass = editTextContrasena.getText().toString().trim();

                String savedUser = prefs.getString("user", "");
                String savedPass = prefs.getString("pass", "");

                if (!user.isEmpty() && !pass.isEmpty()) {
                    if (user.equals(savedUser) && pass.equals(savedPass)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Ambos campos deben ser rellenados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
