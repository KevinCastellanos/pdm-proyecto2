package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;

import static sv.edu.ues.fia.eisi.pdm_proyecto2.MainActivity.mainActivity;
import static sv.edu.ues.fia.eisi.pdm_proyecto2.camaraX.CamaraFragment.contador;

public class MapsFragment extends Fragment {

    public Marker markerBus;
    private String textoRef = "hola";

    private Socket mSocket;
    {
        try {
            mSocket=IO.socket("http://3.133.138.215:5000");
        }catch (URISyntaxException e){
            Toast.makeText(getContext(), "no se pudo conectar al socket io", Toast.LENGTH_SHORT).show();
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        @Override
        public void onMapReady(GoogleMap googleMap) {



            // marcador de bus
            markerBus = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(13.750055, -89.497186))
                    .title("San Francisco")
                    .snippet("Population: 776733"));

            Log.i( "datos","data.toString()");
            mSocket.on("mensaje-nuevo", getCoordenadas);
            /*mSocket.on("mensaje-nuevo", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject)args[0];
                    Toast.makeText(getContext(), "LatLng", Toast.LENGTH_SHORT).show();
                }
            });*/
            mSocket.connect();
            Log.i( "datos","data.toString()");
            System.out.println("datos: ");

            LatLng sydney = new LatLng(Coordenada.latitud,Coordenada.longitud);
            float zoom=16;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.addMarker(new MarkerOptions().position(sydney).title("El Salvador"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    //View imageView=mainActivity.findViewById(R.id.imageView);
                    ImageView myImage=new ImageView(mainActivity);

                    File imgFile = new  File(mainActivity.getDatabasePath(Environment.DIRECTORY_PICTURES) + "CameraX"+contador);

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        myImage.setImageBitmap(myBitmap);
                        myImage.setRotation(90);
                        myImage.setAdjustViewBounds(true);

                    return myImage;
                }
            });

            // markerBus.setPosition();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_maps, container, false);
        //conexion con el servidor


        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private Emitter.Listener getCoordenadas = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    // Log.i( "socket", args[0]);
                    Double latitud;
                    Double longitud;

                    // Toast.makeText(getContext(), "data: "+data, Toast.LENGTH_SHORT).show();
                    try {
                        latitud = data.getDouble("lat");
                        longitud = data.getDouble("lng");

                        System.out.println(args[0].toString());

                        // markerBus.setPosition(new LatLng(13.750055, -89.497186));
                        // textoRef.setText("hola mundo");
                        addMessage(latitud, longitud);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    @Override
    public void onDestroy(){
        super.onDestroy();

        // mSocket.disconnect();
        // mSocket.off("Coordenadas",getCoordenadas);
    }

    private void addMessage(Double lat, Double lng) {

        markerBus.setPosition(new LatLng(lat, lng));
        // textoRef = username;

        // Toast.makeText(getContext(), markerBus.getId(), Toast.LENGTH_SHORT).show();
    }
    
}
