package com.akr.vmsapp.gen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapLocAct extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String lat = null, lng = null, name = null;
    private PrefManager pm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map_loc);

        pm = PrefManager.getInstance(this);

        // do the hack
        Intent ii = getIntent();
        lat = ii.getStringExtra("lat");
        lng = ii.getStringExtra("lng");
        name = ii.getStringExtra("name");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        doSyd();
        doMyl(); // my location
        doGml(); // garage map location
    }

    private void doSyd() {
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void doGml() {
        if (lat == null || lng == null || lat.equals("") || lng.equals("") || lat.equals("null") || lng.equals("null")) {
            return;
        }
        LatLng gml = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(gml).title("" + name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        );

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(gml));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f)); // 17.0f

        /* Move the camera to the user's location and zoom in! */
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gml, 12.0f)); // 14
    }

    private void doMyl() {
        if (pm == null) return;
        String lat = pm.getLat() != null ? pm.getLat() : null;
        String lng = pm.getLng() != null ? pm.getLng() : null;
        if (lat == null || lng == null) return;

        LatLng myl = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(myl).title("Your current location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myl));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f)); // 17.0f

        /* Move the camera to the user's location and zoom in! */
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myl, 12.0f)); // 14
    }
}