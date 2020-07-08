package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
 * Use the {@link textAvozFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class textAvozFragment extends Fragment {

    TextToSpeech tts;
    TextView Texto;
    Button BtnPlay;
    Button BtnSave;
    private int numarch=0;
    Retrofit retrofit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public textAvozFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment textAvozFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static textAvozFragment newInstance(String param1, String param2) {
        textAvozFragment fragment = new textAvozFragment();
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
        View vista=inflater.inflate(R.layout.fragment_text_avoz, container, false);
        Texto=vista.findViewById(R.id.edtText2Speech);
        BtnPlay = (Button) vista.findViewById(R.id.btnText2SpeechPlay);
        BtnSave = (Button) vista.findViewById(R.id.btnText2SpeechSave);
        tts = new TextToSpeech(getActivity(),OnInit);
        BtnPlay.setOnClickListener(onClick);
        BtnSave.setOnClickListener(onClick);

        return vista;
    }
    TextToSpeech.OnInitListener OnInit = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            // TODO Auto-generated method stub
            if (TextToSpeech.SUCCESS==status){
                tts.setLanguage(new Locale("spa","ESP"));
            }
            else
            {
                Toast.makeText(getActivity(), "TTS no disponible",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener onClick=new View.OnClickListener() {
        @SuppressLint("SdCardPath")
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId()==R.id.btnText2SpeechPlay){
                tts.speak(Texto.getText().toString(), TextToSpeech.QUEUE_ADD, null);


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
                            // rutaprueba.add(listaRuta.get(i).getNOMBRE());

                            String u = listaRuta.get(i).getNOMBRE().toString();
                            String p = Texto.getText().toString();
                            if(u.equals( p )) {
                                bandera = 1;
                            } else {

                            }
                        }

                        if(bandera == 1) {
                            tts.speak("ruta encontrada", TextToSpeech.QUEUE_ADD, null);

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
                            editor.putString("nomRuta", Texto.getText().toString()); // Storing string

                            editor.commit(); // commit changes
                            /**
                             *
                             * fin de guardar datos temporales
                             *
                             */
                        } else {
                            tts.speak("No se encontro la ruta en la base de datos", TextToSpeech.QUEUE_ADD, null);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Ruta>> call, Throwable t) {
                        // Toast.makeText(vista.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });









                Toast.makeText(getContext(), "" + Texto.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            if (v.getId()==R.id.btnText2SpeechSave){
                tts.speak(Texto.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                HashMap<String, String> myHashRender = new HashMap<String, String>();
                String Texto_tts =Texto.getText().toString();
                //Cada vez que guarde hara un nuevo archivo wav
                 numarch = numarch + 1;
                String destFileName =
                        Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/Download/tts"+numarch+".wav";
                myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,Texto_tts);
                tts.synthesizeToFile(Texto_tts, myHashRender, destFileName);
                Toast.makeText(getActivity(), "guardado en el cel", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
