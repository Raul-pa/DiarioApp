package com.example.ej33;

import android.app.NotificationManager;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends AppCompatActivity {

    public static void mostrar(Context context, String titulo) {
        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Diario")
                .setContentText(titulo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        nm.notify((int) System.currentTimeMillis(), builder.build());
    }
}
