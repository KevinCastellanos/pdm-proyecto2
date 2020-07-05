package sv.edu.ues.fia.eisi.pdm_proyecto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.ApiServices;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Clases.Ruta;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.DatosRuta;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.DatosUsuario;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.UrlBase;
import sv.edu.ues.fia.eisi.pdm_proyecto2.R;
import android.os.Bundle;

public class Sonido extends AppCompatActivity {
    Button mostrarNotificacion;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent i=new Intent(Sonido.this, Sonido.class);
                //Aquí hay que agregar la clase a la que se dirigirá el usuario al darle click a la notificación, PENDIENTE



                PendingIntent pendingIntent = PendingIntent.getActivity(Sonido.this, 0, i, 0);
                mBuilder =new NotificationCompat.Builder(getApplicationContext())
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icono)
                        .setContentTitle("Notificación Bus tracker")
                        .setSound(Uri.parse("android.resource://"+R.raw.beep))
                        .setContentText("El bus está cerca")
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setAutoCancel(true);



                mNotifyMgr.notify(1, mBuilder.build());

            }
        });
    }
}