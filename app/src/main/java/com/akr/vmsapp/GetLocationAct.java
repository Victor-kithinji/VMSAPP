package com.akr.vmsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class GetLocationAct extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 123;
    private LocationRequest mLocationRequest;
    //private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    //private long FASTEST_INTERVAL = 2000; /* 2 sec */
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 10; // 10 minutes


    private String[] finecos = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    //private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean isGPSEnabled = false, isNetworkEnabled = false, canGetLocation = false;
    private Location location = null;
    private double lat = 0, lng = 0;
    private PrefManager pm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        pm = PrefManager.getInstance(this);

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                /*for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }*/
                onLocChanged(locationResult.getLastLocation());
            }
        };

        findViewById(R.id.btn_bypass).setOnClickListener(view -> {
            check();
        });
    }


    private void check() {
        LocationManager lMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = lMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = lMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            putOnGPS();
        } else {
            this.canGetLocation = true;

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, finecos, REQUEST_LOCATION);
            } else {
                /*// Create the location request to start receiving updates
                LocationRequest locReq = new LocationRequest();
                locReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locReq.setInterval(UPDATE_INTERVAL);
                locReq.setFastestInterval(FASTEST_INTERVAL);
                fusedLocationClient.requestLocationUpdates(locReq, locationCallback, Looper.getMainLooper());*/

                // First get location from Network Provider
                if (isNetworkEnabled) {
                    lMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this::onLocChanged);
                    Log.d(Const.TAG, "Network enabled");
                    if (lMgr != null) {
                        location = lMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();

                            Toast.makeText(this, "Your Location: " + "\n" + "Latitude: " + lat + "\n" + "Longitude: " + lng, Toast.LENGTH_SHORT).show();
                            Log.i(Const.TAG, "Your Location: " + "\n" + "Latitude: " + lat + "\n" + "Longitude: " + lng);

                            pm.saveLatLng(lat, lng);
                            startActivity(new Intent(this, WelcomeAct.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                            Log.i(Const.TAG, "Unable to find location");
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        //check the network permission
                        lMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this::onLocChanged);
                        Log.d(Const.TAG, "GPS Enabled");
                        if (lMgr != null) {
                            location = lMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lng = location.getLongitude();

                                Toast.makeText(this, "Your Location: " + "\n" + "Latitude: " + lat + "\n" + "Longitude: " + lng, Toast.LENGTH_SHORT).show();
                                Log.i(Const.TAG, "Your Location: " + "\n" + "Latitude: " + lat + "\n" + "Longitude: " + lng);

                                pm.saveLatLng(lat, lng);
                                startActivity(new Intent(this, WelcomeAct.class));
                                finish();
                            } else {
                                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                                Log.i(Const.TAG, "Unable to find location");
                            }
                        }
                    }
                }
            }
        }
    }

    private void putOnGPS() {
        // no network provider is enabled
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable GPS in settings").setMessage("No network provider is enabled. GPS is not enabled. Do you want to go to settings menu?").setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void onLocChanged(Location location) {
        // New location has now been determined
        lat = location.getLatitude();
        lng = location.getLongitude();

        String msg = "Updated Location: " + lat + "," + lng;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        Log.i(Const.TAG, msg + " || \nLat: " + lat + " => Lng: " + lng);

        pm.saveLatLng(lat, lng);
        startActivity(new Intent(this, WelcomeAct.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        check();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. Now you can do the Hack.", Toast.LENGTH_SHORT).show();
                Log.i(Const.TAG, "Permission granted. Now you can do the Hack.");
            } else {
                Toast.makeText(this, "Permission denied for location", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, finecos[0])) {
                    ActivityCompat.requestPermissions(this, finecos, REQUEST_LOCATION);
                }
            }
        }
    }
}