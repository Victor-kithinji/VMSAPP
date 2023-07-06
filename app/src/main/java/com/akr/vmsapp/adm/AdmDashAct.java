package com.akr.vmsapp.adm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.gen.GaragesAct;
import com.akr.vmsapp.gen.GrgRatingsAct;
import com.akr.vmsapp.gen.HelpActivity;
import com.akr.vmsapp.gen.MakersAct;
import com.akr.vmsapp.gen.MecRatingsAct;
import com.akr.vmsapp.gen.OwnersAct;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;

public class AdmDashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adm_dash);
        setTitle("Administrator");

        findViewById(R.id.cv_account).setOnClickListener(v -> startActivity(new Intent(this, AdmProfileAct.class)));
        findViewById(R.id.cv_view_admins).setOnClickListener(v -> startActivity(new Intent(this, AdminsAct.class)));

        findViewById(R.id.cv_view_makers).setOnClickListener(v -> startActivity(new Intent(this, MakersAct.class)));
        findViewById(R.id.cv_view_owners).setOnClickListener(v -> startActivity(new Intent(this, OwnersAct.class)));
        findViewById(R.id.cv_view_garages).setOnClickListener(v -> startActivity(new Intent(this, GaragesAct.class)));

        findViewById(R.id.cv_view_vehicles).setOnClickListener(v -> startActivity(new Intent(this, VehiclesAct.class)));
        findViewById(R.id.cv_view_vehicle_types).setOnClickListener(v -> startActivity(new Intent(this, VehicleTypesAct.class)));
        findViewById(R.id.cv_add_vehicle_type).setOnClickListener(v -> startActivity(new Intent(this, AddVehicleTypeAct.class)));

        findViewById(R.id.cv_view_grgratings).setOnClickListener(v -> startActivity(new Intent(this, GrgRatingsAct.class)));
        findViewById(R.id.cv_view_mecratings).setOnClickListener(v -> startActivity(new Intent(this, MecRatingsAct.class)));

        findViewById(R.id.cv_add_country).setOnClickListener(v -> startActivity(new Intent(this, AddCountryAct.class)));
        findViewById(R.id.cv_add_brand).setOnClickListener(v -> startActivity(new Intent(this, AddBrandAct.class)));
        findViewById(R.id.cv_add_model).setOnClickListener(v -> startActivity(new Intent(this, AddModelAct.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_logout) {
            PrefManager.getInstance(this).logout();
            startActivity(new Intent(this, AdmSignInAct.class));
            finish();
        } else if (item.getItemId() == R.id.mi_help){
            startActivity(new Intent(this, HelpActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}