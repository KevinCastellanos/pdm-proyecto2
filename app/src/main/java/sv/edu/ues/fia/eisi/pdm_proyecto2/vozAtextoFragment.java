package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.TextoReconocido;
import sv.edu.ues.fia.eisi.pdm_proyecto2.Interfaces.UrlBase;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link vozAtextoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vozAtextoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView lv;
    static final int check=1111;
    Button Voice;
    Retrofit retrofit;
    View vista;


    public vozAtextoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vozAtextoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static vozAtextoFragment newInstance(String param1, String param2) {
        vozAtextoFragment fragment = new vozAtextoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_voz_atexto, container, false);
        lv=vista.findViewById(R.id.lvVoiceReturn);
        Voice=vista.findViewById(R.id.bvoice);
        Voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
                try {
                    startActivityForResult(i, check);

                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getActivity(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
                Handler handler=new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                                TextoReconocido.listavoz));


                    }
                };
                handler.postDelayed(r
,1000
                );

            }
        });







        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==check && resultCode==RESULT_OK){
            ArrayList<String> results =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            TextoReconocido.listavoz=results;
            lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                    results));
            // Toast.makeText(getActivity(),"texto guardado",Toast.LENGTH_SHORT).show();
           // Log.i("voz",TextoReconocido.listavoz.get(1));
            Toast.makeText(getContext(), ""+ TextoReconocido.listavoz.get(0), Toast.LENGTH_SHORT).show();




            // evaluamos si la ruta esta en la lista registrada
            // libreria de retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlBase.UrlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiServices apiServices = retrofit.create(ApiServices.class);

            Call<List<Ruta>> call = apiServices.obtenerRutas();

            call.enqueue(new Callback<List<Ruta>>() {
                @Override
                public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {


                    //Lista para recuperar los elementos de la API
                    List<Ruta> listaRuta = response.body();

                    //Definición de lista que se mostrarán en el adapter
                    //  ArrayList<String> rutaprueba = new ArrayList<>();
                    int bandera = 0;
                    for (int i=0; i< response.body().size();i++){

                        //     Log.e("Test",String.valueOf(listaRuta.get(i).getNOMBRE())); //Prueba para traer los nombres
                        // rutaprueba.add(listaRuta.get(i).getNOMBRE());s=s.toUpperCase();

                        String u = listaRuta.get(i).getNOMBRE().toString();
                        String p = TextoReconocido.listavoz.get(0).toUpperCase();
                        if(u.equals( p )) {
                            bandera = 1;
                        } else {

                        }
                    }

                    if(bandera == 1) {
                        // tts.speak("ruta encontrada", TextToSpeech.QUEUE_ADD, null);
                        Toast.makeText(getContext(), "Ruta encontrada", Toast.LENGTH_SHORT).show();
                        // navegar al fragmen mapa
                        Navigation.findNavController(vista).navigate(R.id.mapsFragment);

                        /**
                         *
                         * guardar datos temporales
                         *
                         */
                        // almacenar valores remporales
                        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();

                        // insertar un valor
                        editor.putString("nomRuta", TextoReconocido.listavoz.get(0)); // Storing string

                        editor.commit(); // commit changes
                        /**
                         *
                         * fin de guardar datos temporales
                         *
                         */
                    } else {
                        // tts.speak("No se encontro la ruta en la base de datos", TextToSpeech.QUEUE_ADD, null);
                        Toast.makeText(getContext(), "No se encontro la ruta en la base de datos", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Ruta>> call, Throwable t) {
                    // Toast.makeText(vista.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });





        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){
        super.onDestroy();
    }
}