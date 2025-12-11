package com.example.ej33;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private ImageButton imageButtonPlay, imageButtonPause, imageButtonStop, imageButtonVolver;
    private Spinner spinner;
    private int[] music = {
            R.raw.paramore_hard_times,
            R.raw.sam_gellaitry_assumptions
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        imageButtonPlay = findViewById(R.id.imageButtonPlay);
        imageButtonPause = findViewById(R.id.imageButtonPause);
        imageButtonStop = findViewById(R.id.imageButtonStop);
        imageButtonVolver = findViewById(R.id.imageButtonVolver);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.music, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mp = MediaPlayer.create(this, music[spinner.getSelectedItemPosition()]);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reproducir(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imageButtonPlay.setOnClickListener(view -> {
            if (!mp.isPlaying()) {
                mp.start();
            } else {
                Toast.makeText(this, "Ya se está reproduciendo", Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonPause.setOnClickListener(view -> {
            if (mp.isPlaying()) {
                mp.pause();
            } else {
                Toast.makeText(this, "Ya está pausado", Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonStop.setOnClickListener(view -> {
            if (mp.isPlaying()) {
                mp.stop();
                try {
                    mp.prepare();
                } catch (IOException e) {
                    Toast.makeText(this, "Error al reproducir", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No se está reproduciendo", Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonVolver.setOnClickListener(view -> {
            mp.stop();
            finish();
        });

    }

    private void reproducir(int position) {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
        mp = MediaPlayer.create(this, music[position]);
        mp.setLooping(true);
    }

    @Override
    protected void onDestroy() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
        super.onDestroy();
    }
}
