package sv.edu.ues.fia.eisi.pdm_proyecto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Login2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // logica para inicio de sesión
        Button boton = (Button) findViewById(R.id.ingresar);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String correo = ((EditText) findViewById(R.id.correo)).getText().toString();
                String contraseña = ((EditText) findViewById(R.id.contraseña)).getText().toString();

                if (correo.equals("admin") && contraseña.equals("admin")) {

                    final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "usuario logueado", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Usuario Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
