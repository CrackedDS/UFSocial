package com.example.deepa.ufsocial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationTest extends AppCompatActivity {

    private LocationManager lm;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private JSONObject obj;
    @RequiresApi(api = Build.VERSION_CODES.M)

    public JSONObject getGPS() throws JSONException {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsOn(lm)) {
            Toast.makeText(LocationTest.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
            SystemClock.sleep(500);
            openGPS();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request location access for Android 6.0 and above
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateShow(lc);
        //update GPS info every 500ms or 2 meters
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, GPSLocationListener);
        return obj;
    }

    LocationListener GPSLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            try {
                updateShow(location);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

            try {
                updateShow(null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void updateShow(Location location) throws JSONException {
        if (location != null) {
            obj.put("header", "updateGPS");
            obj.put("longitude", Double.toString(location.getLongitude()));
            obj.put("latitude", Double.toString(location.getLatitude()));
        }
    }

    private boolean isGpsOn(LocationManager lm) {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
    }

    private void openGPS() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }
}
