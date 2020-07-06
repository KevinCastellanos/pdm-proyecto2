package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

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
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
