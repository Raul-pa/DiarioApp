package com.example.ej33;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;
    private DataBaseHelper db;
    private Entrada entrada, entradaseleccionada;
    private List<Entrada> entradaList;
    private ArrayAdapter adapter;
    public NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.floatingActionButton);
        db = new DataBaseHelper(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entradaseleccionada = entradaList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("titulo", entradaseleccionada.getTitulo());
                intent.putExtra("contenido", entradaseleccionada.getContenido());
                intent.putExtra("categoria", entradaseleccionada.getCategoria());
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);
        cargarLista();
    }

    public void cargarLista() {
        Cursor cursor = db.getAllEntries();
        entradaList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String contenido = cursor.getString(2);
            String categoria = cursor.getString(3);
            entrada = new Entrada(id, titulo, contenido, categoria);
            entradaList.add(entrada);
        }
        cursor.close();

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, entradaList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_all) {
            db.deleteAllEntries();
            cargarLista();
            adapter.notifyDataSetChanged();
            NotificationHelper.mostrar(this, "Todas las entradas eliminadas");
            return true;
        } else if (item.getItemId() == R.id.audio_activity) {
            Intent intent = new Intent(this, AudioActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.video_activity) {
            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        assert info != null;
        Entrada ent = entradaList.get(info.position);

        if (item.getItemId() == R.id.delete_entry) {
            db.deleteEntry(ent.getId());
            cargarLista();
            adapter.notifyDataSetChanged();
            NotificationHelper.mostrar(this, "Entrada eliminada");
            return true;
        }
        return super.onContextItemSelected(item);
    }
}