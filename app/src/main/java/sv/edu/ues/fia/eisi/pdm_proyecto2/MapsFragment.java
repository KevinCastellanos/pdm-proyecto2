package sv.edu.ues.fia.eisi.pdm_proyecto2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;

import static sv.edu.ues.fia.eisi.pdm_proyecto2.MainActivity.mainActivity;
import static sv.edu.ues.fia.eisi.pdm_proyecto2.camaraX.CamaraFragment.contador;

public class MapsFragment extends Fragment {

    private Socket mSocket;
    {
        try {
            mSocket=IO.socket("");
        }catch (URISyntaxException e){}
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
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //conexion con el servidor
        mSocket.on("Coordenadas",getCoordenadas);
        mSocket.connect();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private Emitter.Listener getCoordenadas=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data=(JSONObject) args[0];
                    Double latitud;
                    Double longitud;

                    try {
                        latitud=data.getDouble("latitud");
                        longitud=data.getDouble("longitud");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            });
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("Coordenadas",getCoordenadas);
    }
    
}
