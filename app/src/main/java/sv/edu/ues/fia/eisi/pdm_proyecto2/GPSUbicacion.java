package sv.edu.ues.fia.eisi.pdm_proyecto2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPSUbicacion implements LocationListener{

    @Override
    public void onLocationChanged(Location location) {
        Coordenada.latitud=location.getLatitude();
        Coordenada.longitud=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
