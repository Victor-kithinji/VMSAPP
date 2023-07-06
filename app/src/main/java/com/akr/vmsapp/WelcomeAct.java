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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.akr.vmsapp.adm.AdmDashAct;
import com.akr.vmsapp.adm.AdmSignInAct;
import com.akr.vmsapp.mec.MecDashAct;
import com.akr.vmsapp.mec.MecSignInAct;
import com.akr.vmsapp.own.OwnDashAct;
import com.akr.vmsapp.own.OwnSignInAct;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

public class WelcomeAct extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 123;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 10; // 10 minutes
    // fine and coarse location permissions
    private String[] fnclp = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private LocationCallback locationCallback;
    private boolean isGPSEnabled = false, isNetworkEnabled = false, canGetLocation = false;
    private Location location = null;
    private double lat = 0, lng = 0;
    private PrefManager pm = null;
    private LinearLayout llHide;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d("TESTCODE", null + "");
        String me = null;
        Log.d("TESTCODE",  me == null ? "true" : "false");

        pm = PrefManager.getInstance(this);

        locationCallback = new LocationCallback() {

            public void onLocationResult(LocationResult locRes) {
                if (locRes == null) {
                    return;
                }
                onLocChanged(locRes.getLastLocation());
            }
        };

        llHide = findViewById(R.id.ll_hide);
        tvError = findViewById(R.id.tv_error);

        findViewById(R.id.btn_admin).setOnClickListener(view -> {
            if (pm.getAdmId() != null) {
                startActivity(new Intent(this, AdmDashAct.class));
                finish();
            } else {
                startActivity(new Intent(this, AdmSignInAct.class));
            }
        });
        findViewById(R.id.btn_mechanic).setOnClickListener(view -> {
            if (pm.getMecId() != null) {
                startActivity(new Intent(this, MecDashAct.class));
                finish();
            } else {
                startActivity(new Intent(this, MecSignInAct.class));
            }
        });
        findViewById(R.id.btn_owner).setOnClickListener(view -> {
            if (pm.getOwnId() != null) {
                startActivity(new Intent(this, OwnDashAct.class));
                finish();
            } else {
                startActivity(new Intent(this, OwnSignInAct.class));
            }
        });

        findViewById(R.id.btn_bypass).setOnClickListener(view -> {
            if (lat == 0 && lng == 0) {
                tvError.setText("Please close and restart the app. Make sure your phone is having an internet connection");
                putOnGPS();
            } else {
                check();
            }
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
                ActivityCompat.requestPermissions(this, fnclp, REQUEST_LOCATION);
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

                            Log.i(Const.TAG, "Your Location: " + "\n" + "Latitude: " + lat + "\n" + "Longitude: " + lng);

                            pm.saveLatLng(lat, lng);
                            llHide.setVisibility(View.VISIBLE);
                        } else {
                            tvError.append("Unable to find location\n");
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

                                Log.i(Const.TAG, "Your Location: " + "\n" + "Latitude: " + lat + "\n" + "Longitude: " + lng);

                                pm.saveLatLng(lat, lng);
                                llHide.setVisibility(View.VISIBLE);
                            } else {
                                tvError.append("\nUnable to find location");
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

    public void onLocChanged(Location loc) {
        // New location has now been determined
        lat = loc.getLatitude();
        lng = loc.getLongitude();

        String msg = "Updated Location: " + lat + "," + lng;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        Log.i(Const.TAG, msg + " || \nLat: " + lat + " => Lng: " + lng);

        pm.saveLatLng(lat, lng);

        llHide.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        check();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Now you can share the Hack.", Toast.LENGTH_SHORT).show();
                Log.i(Const.TAG, "Permission granted. Now you can do the Hack.");
            } else {
                tvError.append("Permission denied for location\n");
                Toast.makeText(this, "Permission denied for location", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, fnclp[0])) {
                    ActivityCompat.requestPermissions(this, fnclp, REQUEST_LOCATION);
                }
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_two);
        View view = MenuItemCompat.getActionView(menuItem);

        CircleImageView profileImage = view.findViewById(R.id.toolbar_profile_image);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1478070531059-3db579c041d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80")
                .into(profileImage);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_one:
                Toast.makeText(WelcomeActivity.this, "Menu One Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                break;
            //When we use layout for menu then this case will not work
            case R.id.menu_two:
                Toast.makeText(WelcomeActivity.this, "Menu Two Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_three:
                Toast.makeText(WelcomeActivity.this, "Menu Three Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}