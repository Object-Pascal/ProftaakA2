package com.example.schatrijk_app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class Compass implements LocationListener {
    private Context context;
    private LocationManager locationManager;
    private Location phoneLocation;
    private Location kinderdijk;
    private Location sliedrecht;

    private Location destination;

    public Compass(Context context) {

        this.context = context;

        sliedrecht = new Location("Sliedrecht");
        sliedrecht.setLatitude(51.823939);
        sliedrecht.setLongitude(4.790521);

        kinderdijk = new Location("Kinderdijk");
        kinderdijk.setLatitude(51.8883743);
        kinderdijk.setLongitude(4.6325058);

        this.phoneLocation = new Location("Phone");
        this.destination = sliedrecht;
    }

    @Override
    public void onLocationChanged(Location location) {

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

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnabled) {
            try {

                phoneLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return phoneLocation;
            } catch (Exception e) {
                Log.d("Exception error:\n",e.toString());
            }

        } else {
            Toast.makeText(context, "Please Enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }


    public float angleBetweenLocations(Location phoneLocation) {
        float value = phoneLocation.bearingTo(this.destination);
        return value;
    }

    public float distanceBetweenLocations(Location phoneLocation){
        float value = phoneLocation.distanceTo(this.destination);
        return value;
    }
}
