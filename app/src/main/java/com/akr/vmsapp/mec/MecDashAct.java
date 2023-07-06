package com.akr.vmsapp.mec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.gen.GaragesAct;
import com.akr.vmsapp.gen.HelpActivity;
import com.akr.vmsapp.gen.MakersAct;
import com.akr.vmsapp.gen.SparePartsAct;
import com.akr.vmsapp.gen.SpareShopsAct;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;

public class MecDashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mec_dash);
        setTitle("Mechanic");

        findViewById(R.id.cv_account).setOnClickListener(v -> startActivity(new Intent(this, MecProfileAct.class)));
        findViewById(R.id.cv_view_makers).setOnClickListener(v -> startActivity(new Intent(this, MakersAct.class)));

        findViewById(R.id.cv_view_garages).setOnClickListener(v -> startActivity(new Intent(this, GaragesAct.class)));
        findViewById(R.id.cv_view_spareparts).setOnClickListener(v -> startActivity(new Intent(this, SparePartsAct.class)));
        findViewById(R.id.cv_view_spareshops).setOnClickListener(v -> startActivity(new Intent(this, SpareShopsAct.class)));

        findViewById(R.id.cv_add_garage).setOnClickListener(v -> startActivity(new Intent(this, AddGarageAct.class)));
        findViewById(R.id.cv_add_sparepart).setOnClickListener(v -> startActivity(new Intent(this, AddSparePartAct.class)));
        findViewById(R.id.cv_add_spareshop).setOnClickListener(v -> startActivity(new Intent(this, AddSpareShopAct.class)));
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
            startActivity(new Intent(this, MecSignInAct.class));
            finish();
        } else if (item.getItemId() == R.id.mi_help) {
            startActivity(new Intent(this, HelpActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}