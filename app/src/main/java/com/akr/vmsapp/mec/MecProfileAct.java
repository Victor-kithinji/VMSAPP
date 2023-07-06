package com.akr.vmsapp.mec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.akr.vmsapp.mod.Maker;
import com.akr.vmsapp.uti.PrefManager;
import com.example.vmsapp.R;

public class MecProfileAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mec_profile);
        setTitle("Mechanic Profile");

        Maker usr = PrefManager.getInstance(this).getMaker();

        TextView tvNem = findViewById(R.id.tv_name);
        TextView tv1 = findViewById(R.id.tv_1);
        TextView tv2 = findViewById(R.id.tv_2);
        TextView tv3 = findViewById(R.id.tv_3);
        TextView tv4 = findViewById(R.id.tv_4);
        TextView tv5 = findViewById(R.id.tv_5);
        TextView tv6 = findViewById(R.id.tv_6);
        TextView tv7 = findViewById(R.id.tv_7);
        TextView tv8 = findViewById(R.id.tv_8);
        TextView tv9 = findViewById(R.id.tv_9);
        TextView tv10 = findViewById(R.id.tv_10);
        TextView tv11 = findViewById(R.id.tv_11);
        TextView tv12 = findViewById(R.id.tv_12);


        tvNem.setText(String.format("%s %s %s", usr.getFirstname(), usr.getLastname(), !usr.getSurname().isEmpty() || usr.getSurname() != null ? usr.getSurname() : ""));
        tv1.setText(usr.getUsername());
        tv2.setText(String.format("%s (%s)", usr.getPhone(), usr.getCountryCode()));
        tv3.setText(usr.getEmail());
        tv4.setText(usr.getIdNumber());
        tv5.setText(usr.getMecId());
        tv6.setText(String.format("DoB:%s RD:%s", usr.getDob(), usr.getRegDate()));
        tv7.setText(usr.getGender());
        tv8.setText(String.format("%s (%s, %s)", usr.getLocName(), usr.getLat(), usr.getLng()));
        tv9.setText(usr.getPhotoUrl());
        tv10.setText(usr.getStatus());
        //tv11.setText(DateFormat.getDateInstance(DateFormat.FULL).format(new Date(usr.getRegDate())));
        tv12.setText(usr.getPassword());

        // DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(millis));
    }
}