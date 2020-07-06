
package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.

 */
public class RegistrarRuta extends Fragment {
    Retrofit retrofit;
    EditText Nombre;
    EditText Descripcion;


    public RegistrarRuta() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlBase.UrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        View vista=inflater.inflate(R.layout.fragment__agregar_ruta, container, false);
        Nombre=vista.findViewById(R.id.edit_IngresarRuta);
        Descripcion =vista.findViewById(R.id.edit_ingresarDescripcion);
        Button RegistrarRuta=vista.findViewById(R.id.button_RegistrarRuta);

        RegistrarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NOMBRE= Nombre.getText().toString();
                String DESCRIPCION=Descripcion.getText().toString();
                ApiServices service = retrofit.create(ApiServices.class);

                service.agregarRuta(NOMBRE, DESCRIPCION).
                        enqueue(new Callback<Ruta>() {
                            @Override
                            public void onResponse(Call<Ruta> call, Response<Ruta> response) {
                                if(response.isSuccessful()){
                                    Ruta respuesta=response.body();
                                    DatosRuta.NOMBRE =respuesta.getNOMBRE().toString();
                                    DatosRuta.DESCRIPCION=respuesta.getDESCRIPCION().toString();
                                    Toast.makeText(getActivity(),"ruta registrada",Toast.LENGTH_LONG).show();

                                    }
                            }
                            @Override
                            public void onFailure(Call<Ruta> call, Throwable t) {
                                Log.i("aquí","falló");
                                Toast.makeText(getActivity(),"falló en el WS",Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

        return vista;
    }

    private EditText getViewById(View v, int p) {
        return (EditText) v.findViewById(p);
    }
}
