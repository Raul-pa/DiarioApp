package com.example.ej33;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextContenido;
    private Spinner spinnerCategoria;
    private Button buttonGuardar, buttonVolver;
    private DataBaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextContenido = findViewById(R.id.editTextContenido);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonVolver = findViewById(R.id.buttonVolverAdd);

        db = new DataBaseHelper(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString();
                String contenido = editTextContenido.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();

                if (!titulo.isEmpty() && !contenido.isEmpty()) {
                    Entrada entrada = new Entrada();
                    entrada.setTitulo(titulo);
                    entrada.setContenido(contenido);
                    entrada.setCategoria(categoria);
                    db.insertEntry(titulo, contenido, categoria);
                    NotificationHelper.mostrar(AddActivity.this, "Entrada guardada");
                    finish();
                }
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
