package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

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

public class GraficaFragment extends Fragment {
    private LineChart lineChart;
    private BarChart barChart;
    private RadarChart radarChart;
    private LineDataSet lineDataSet;
    private BarDataSet barDataSet;
    private RadarDataSet radarDataSet;
    private List<Ruta> rutaGraficos;

    public GraficaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_grafica, container, false);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(UrlBase.UrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices=retrofit.create(ApiServices.class);

        Call<List<Ruta>> call=apiServices.obtenerRutas();

        call.enqueue(new Callback<List<Ruta>>() {
            @Override
            public void onResponse(Call<List<Ruta>> call, Response<List<Ruta>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Error response",Toast.LENGTH_LONG).show();
                    Log.d("ERROR", "onResponse: "+response.code());
                    return;
                }

                rutaGraficos =response.body();
                graficarLinea(vista);
                graficarBarra(vista);
                graficarRadio(vista);
            }

            @Override
            public void onFailure(Call<List<Ruta>> call, Throwable t) {
                Toast.makeText(getContext(),"Error failure",Toast.LENGTH_LONG).show();
                Log.d("ERROR", "onFailure: "+t.getMessage());
            }
        });


        return vista;
    }

    public void graficarLinea(View vista){
        lineChart = vista.findViewById(R.id.lineChart);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();

        for (Ruta rutaGraficos : this.rutaGraficos) {
            lineEntries.add(new Entry((Integer) rutaGraficos.getIDRUTAS(), (Integer) rutaGraficos.getCANTIDAD()));
        }

        lineDataSet = new LineDataSet(lineEntries, "AppBus");

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
    }

    public void graficarBarra(View vista){
        barChart=vista.findViewById(R.id.barChart);

        ArrayList<BarEntry> barEntries=new ArrayList<BarEntry>();

        for (Ruta rutaGraficos : this.rutaGraficos) {
            barEntries.add(new BarEntry((Integer) rutaGraficos.getIDRUTAS(), (Integer) rutaGraficos.getCANTIDAD()));
        }

        barDataSet=new BarDataSet(barEntries,"AppBus");

        BarData barData=new BarData();
        barData.addDataSet(barDataSet);
        barChart.setData(barData);
    }

    public void graficarRadio(View vista){
        radarChart=vista.findViewById(R.id.radarChart);

        ArrayList<RadarEntry> radarEntries=new ArrayList<RadarEntry>();

        for (Ruta rutaGraficos : this.rutaGraficos) {
            radarEntries.add(new RadarEntry((Integer) rutaGraficos.getIDRUTAS(), (Integer) rutaGraficos.getCANTIDAD()));
        }

        radarDataSet=new RadarDataSet(radarEntries,"AppBus");

        RadarData radarData=new RadarData();
        radarData.addDataSet(radarDataSet);
        radarChart.setData(radarData);
    }
}