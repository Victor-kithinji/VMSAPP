package com.akr.vmsapp.adm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.mod.Admin;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;

public class AdmProfileAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adm_profile);
        setTitle("Admin Profile");

        Admin usr = PrefManager.getInstance(this).getAdmin();

        TextView tvFL = findViewById(R.id.tv_name);
        TextView tvPh = findViewById(R.id.tv_phone);
        TextView tvEm = findViewById(R.id.tv_email);
        TextView tvRn = findViewById(R.id.tv_regno);
        TextView tvDe = findViewById(R.id.tv_department);
        TextView tvGe = findViewById(R.id.tv_gender);

        //String cds = DateFormat.getDateInstance(DateFormat.FULL).format(new Date(usr.getDob()));

        tvFL.setText(String.format("%s %s %s", usr.getFirstname(), usr.getLastname(), usr.getSurname()));
        tvPh.setText(usr.getPhone());
        tvEm.setText(usr.getEmail());
        tvRn.setText(usr.getDob());
        tvDe.setText(usr.getCountryCode());
        tvGe.setText(usr.getGender());
    }
}