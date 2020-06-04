package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RutaFragment extends Fragment {

    // propiedades
    View vista;
    ListView lista;

    public RutaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.vista = inflater.inflate(R.layout.fragment_ruta, container, false);

        // crear el adaptador
        this.lista = (ListView) this.vista.findViewById(R.id.id_lista_ruta);

        // Crear al array list
        ArrayList<String> listaRuta = new ArrayList<>();
        listaRuta.add("Ruta R185");
        listaRuta.add("Ruta R187");
        listaRuta.add("Ruta R205");
        listaRuta.add("Ruta R13");
        listaRuta.add("Ruta R44");
        listaRuta.add("Ruta R3");
        // nuevo comentario

        // crear el adaptador de array
        // porque se esta en un fragment se llama a vista.getContext() y no solo a this
        ArrayAdapter adaptador = new ArrayAdapter(vista.getContext(), android.R.layout.simple_list_item_1, listaRuta);

        // añadimos el adaptador a la lilsta de la vista
        this.lista.setAdapter(adaptador);

        // crearle los eventos a la lista
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Selecciono la ruta: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return this.vista;
    }
}
