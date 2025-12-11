package com.example.ej33;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewTitulo, textViewCategoria, textViewContenido;
    private Button buttonVolver;
    private DataBaseHelper db;
    private Entrada entrada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewCategoria = findViewById(R.id.textViewCategoria);
        textViewContenido = findViewById(R.id.textViewContenido);
        buttonVolver = findViewById(R.id.buttonVolverDetail);

        db = new DataBaseHelper(this);

        entrada = new Entrada();

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String contenido = intent.getStringExtra("contenido");
        String categoria = intent.getStringExtra("categoria");

        textViewTitulo.setText(titulo);
        textViewCategoria.setText(categoria);
        textViewContenido.setText(contenido);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
