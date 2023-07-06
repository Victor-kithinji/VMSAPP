package com.akr.vmsapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.vis.AlertReceiver;
import com.example.vmsapp.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvAc, tvEx;
    private int mYear, mMonth, mDay, hod, min, sec;
    private long millis = 0, adet = 0, edet = 0;
    private Calendar cal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAc = findViewById(R.id.tv_1);
        tvEx = findViewById(R.id.tv_2);

        findViewById(R.id.btn_1).setOnClickListener(v -> pickAcquire());
        findViewById(R.id.btn_2).setOnClickListener(v -> pickExpiry());

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());

        getToday();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cal = Calendar.getInstance();
        Log.i(Const.TAG, "onStart: " + cal.getTimeInMillis());
    }

    private void getToday() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        hod = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);

        millis = c.getTimeInMillis();
    }

    private void pickAcquire() {
        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            tvAc.setText(String.format(Locale.US, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year));

            Calendar caa = Calendar.getInstance();
            caa.set(Calendar.YEAR, year);
            caa.set(Calendar.MONTH, monthOfYear);
            caa.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            caa.set(Calendar.HOUR_OF_DAY, hod);
            caa.set(Calendar.MINUTE, min);
            caa.set(Calendar.SECOND, sec);

            adet = caa.getTimeInMillis();
            Log.i(Const.TAG, "pickAcquire: " + adet + " => pickAcquire: " + edet);

            if (adet == 0) {
                tvAc.requestFocus();
                tvAc.setError("Please pick insurance acquire date");
            } else if (edet != 0 && adet >= edet) {
                Toast.makeText(this, "Please Check your inputs and pick correct dates", Toast.LENGTH_SHORT).show();
            }

        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void pickExpiry() {
        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            tvEx.setText(String.format(Locale.US, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year));

            Calendar cae = Calendar.getInstance();
            cae.set(Calendar.YEAR, year);
            cae.set(Calendar.MONTH, monthOfYear);
            cae.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            edet = cae.getTimeInMillis();
            Log.i(Const.TAG, "pickExpiry : " + adet + " => pickExpiry : " + edet);

            if (edet == 0) {
                tvAc.requestFocus();
                tvAc.setError("Please pick insurance acquire date");
            } else if (adet != 0 && adet >= edet) {
                Toast.makeText(this, "Please Check your inputs and pick correct dates", Toast.LENGTH_SHORT).show();
            }

        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void validateInp() {
        if (adet != 0 && edet != 0) {
            if (adet >= edet) {
                tvAc.setText(String.format(Locale.US, " : Check your input (%d)", adet));
                tvEx.setText(String.format(Locale.US, " : Check your input (%d)", edet));
                Toast.makeText(this, "Please Check your inputs and pick correct dates *", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(Const.TAG, "validateInp: Ok: " + adet + " => " + edet);

                setReminder(cal);
            }
        } else {
            Log.i(Const.TAG, "validateInp: " + adet + " => : " + edet);
            Toast.makeText(this, "Please pick insurance acquire and expiry dates", Toast.LENGTH_SHORT).show();
        }
    }

    private void setReminder(Calendar c) {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.SECOND, 10);
        String cds = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());

        AlarmManager aMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        /*if (c.before(ca)) {
            c.add(Calendar.DATE, 1);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            aMgr.setExact(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), pendingIntent);
        } else {
            aMgr.set(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Insurance reminder started", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notif: Alarm started");
        builder.setMessage("Insurance reminder started for "+cds+" \nYou will be reminded to go and replace your insurance before it expires").setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cancelReminder() {
        AlarmManager aMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        aMgr.cancel(pendingIntent);

        Toast.makeText(this, "Insurance reminder canceled", Toast.LENGTH_SHORT).show();
    }
}