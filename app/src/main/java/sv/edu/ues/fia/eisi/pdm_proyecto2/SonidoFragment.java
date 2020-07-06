package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.view.View;
import android.widget.Button;

public class SonidoFragment extends FragmentActivity {
    Button mostrarNotificacion;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sonido);
        mostrarNotificacion=(Button)findViewById(R.id.btnMostrarNotificacion);
        mp = MediaPlayer.create(this, R.raw.beep);

        mostrarNotificacion.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                mp = MediaPlayer.create(getBaseContext(), R.raw.beep);
                mp.start();

                int icono = R.mipmap.ic_launcher;
                Intent i=new Intent(SonidoFragment.this, SonidoFragment.class);
                //Aquí hay que agregar la clase a la que se dirigirá el usuario al darle click a la notificación, PENDIENTE



                PendingIntent pendingIntent = PendingIntent.getActivity(SonidoFragment.this, 0, i, 0);
                mBuilder =new NotificationCompat.Builder(getApplicationContext())
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icono)
                        .setContentTitle("Notificación Bus tracker")
                        .setSound(Uri.parse("android.resource://"+R.raw.beep))
                        .setContentText("El bus está cerca")
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setAutoCancel(true);

                mp.stop();


                mNotifyMgr.notify(1, mBuilder.build());

            }
        });
    }
}