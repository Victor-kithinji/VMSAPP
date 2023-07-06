package com.akr.vmsapp.own;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.gen.GaragesAct;
import com.akr.vmsapp.gen.HelpActivity;
import com.akr.vmsapp.gen.MakersAct;
import com.akr.vmsapp.gen.OwnersAct;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;

public class OwnDashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_own_dash);
        setTitle("Vehicle Owner");

        findViewById(R.id.cv_account).setOnClickListener(v -> startActivity(new Intent(this, OwnProfileAct.class)));
        findViewById(R.id.cv_view_owners).setOnClickListener(v -> startActivity(new Intent(this, OwnersAct.class)));

        findViewById(R.id.cv_view_makers).setOnClickListener(v -> startActivity(new Intent(this, MakersAct.class)));
        findViewById(R.id.cv_view_garages).setOnClickListener(v -> startActivity(new Intent(this, GaragesAct.class)));
        findViewById(R.id.cv_view_expenses).setOnClickListener(v -> startActivity(new Intent(this, ExpensesAct.class)));

        findViewById(R.id.cv_view_insurances).setOnClickListener(v -> startActivity(new Intent(this, InsurancesAct.class)));
        findViewById(R.id.cv_rate_grg).setOnClickListener(v -> startActivity(new Intent(this, AddGrgRatingAct.class)));
        findViewById(R.id.cv_rate_mec).setOnClickListener(v -> startActivity(new Intent(this, AddMecRatingAct.class)));

        findViewById(R.id.cv_add_vehicle).setOnClickListener(v -> startActivity(new Intent(this, AddVehicleAct.class)));
        findViewById(R.id.cv_add_expense).setOnClickListener(v -> startActivity(new Intent(this, AddExpenseAct.class)));
        findViewById(R.id.cv_add_insurance).setOnClickListener(v -> startActivity(new Intent(this, AddInsuranceAct.class)));
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
            startActivity(new Intent(this, OwnSignInAct.class));
            finish();
        } else if (item.getItemId() == R.id.mi_help) {
            startActivity(new Intent(this, HelpActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}