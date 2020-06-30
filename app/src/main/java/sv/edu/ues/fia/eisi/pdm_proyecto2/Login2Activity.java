package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.ApiServices;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.DatosUsuario;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.UrlBase;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Clases.Usuario;

public class Login2Activity extends Activity {
Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlBase.UrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // logica para inicio de sesión
        Button boton = (Button) findViewById(R.id.button_ingresar);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String correo = ((EditText) findViewById(R.id.correo)).getText().toString();
                String contraseña = ((EditText) findViewById(R.id.contraseña)).getText().toString();
                ApiServices service = retrofit.create(ApiServices.class);

                if (correo.equals("") && contraseña.equals("")) {


                    Toast.makeText(getApplicationContext(), "hay datos no llenados", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(getApplicationContext(), "Usuario Incorrecto", Toast.LENGTH_SHORT).show();

                    service.login(correo,contraseña).
                            enqueue(new Callback<Usuario>() {
                                @Override
                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                    if(response.isSuccessful()){
                                        Usuario respuesta=response.body();
                                        DatosUsuario.IDCOORDENADAUSUARIO=respuesta.getIDCOORDENADAUSUARIO().toString();
                                        DatosUsuario.IDUSUARIO=respuesta.getIDUSUARIO().toString();
                                   /*     if(respuesta.getIDVEHICULO().toString().isEmpty()){

                                        }else{
                                        DatosUsuario.IDVEHICULO=respuesta.getIDVEHICULO().toString();} */
                                        DatosUsuario.NOMBRE=respuesta.getNOMBRE().toString();
                                        DatosUsuario.PWD=respuesta.getPWD().toString();
                                        DatosUsuario.USUARIO=respuesta.getUSUARIO().toString();
                                        Toast.makeText(getApplicationContext(), "usuario logueado", Toast.LENGTH_SHORT).show();

                                        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();



                                    }else{
                                        Toast.makeText(Login2Activity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Usuario> call, Throwable t) {
                                    Toast.makeText(Login2Activity.this, "Verifique los datos o su conexion a internet", Toast.LENGTH_SHORT).show();
                                }
                            });
                }



            }
        });
    }
}
