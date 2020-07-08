package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Clases.Ruta;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.ApiServices;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.UrlBase;


/**
 * A simple {@link Fragment} subclass.
 */
public class RutaFragment extends Fragment {

    // propiedades x
    View vista;
    ListView lista;
    Retrofit retrofit;

    public RutaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.vista = inflater.inflate(R.layout.fragment_ruta, container, false);







        return this.vista;
    }

    // este metodo se ejecuta exactamente despues de onCreateView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // crear el adaptador
        this.lista = (ListView) this.vista.findViewById(R.id.id_lista_ruta);

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlBase.UrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<List<Ruta>> call = apiServices.obtenerRutas();

        call.enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {

                /*Para realizar el testo de los datos recuperados
                Log.e("Test",String.valueOf(response));
                Log.e("Test",String.valueOf(response.body().getClass().getName()));
                Log.e("Test",String.valueOf(response.body().size()));
                */

                //Lista para recuperar los elementos de la API
                List<Ruta> listaRuta = response.body();

                //Definición de lista que se mostrarán en el adapter
                ArrayList<String> rutaprueba = new ArrayList<>();

                for (int i=0; i<response.body().size();i++){
                    //     Log.e("Test",String.valueOf(listaRuta.get(i).getNOMBRE())); //Prueba para traer los nombres
                    rutaprueba.add(listaRuta.get(i).getNOMBRE());
                }

                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(vista.getContext(), android.R.layout.simple_list_item_1, rutaprueba);
                lista.setAdapter(itemsAdapter);
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
                Toast.makeText(vista.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(parent.getContext(), "Seleccionó la ruta: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                // navegar al fragmen mapa
                Navigation.findNavController(v).navigate(R.id.mapsFragment);

                /**
                 *
                 * guardar datos temporales
                 *
                 */
                // almacenar valores remporales
                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                // insertar un valor
                editor.putString("nomRuta", parent.getItemAtPosition(position).toString()); // Storing string

                editor.commit(); // commit changes
                /**
                 *
                 * fin de guardar datos temporales
                 *
                 */
            }
        });
    }
}
