package com.akr.vmsapp.own;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.adm.AdmDashAct;
import com.akr.vmsapp.mod.Admin;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.Fxns;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.PrefManager;
import com.akr.vmsapp.uti.URLs;
import com.akr.vmsapp.vis.AlertReceiver;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vmsapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddInsuranceAct extends AppCompatActivity {

    private TextInputEditText tietAm, tietCo, tietAc, tietEx;
    private long millis = 0, adet = 0, edet = 0;
    private LinearLayout llHd;
    private int mYear, mMonth, mDay, hod, min, sec;
    private String[][] dat;
    private int spp = 0;
    private Calendar cal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_insurance);

        llHd = findViewById(R.id.ll_hide);

        tietAm = findViewById(R.id.tiet_amount);
        tietCo = findViewById(R.id.tiet_company);
        tietAc = findViewById(R.id.tiet_acquire);
        tietEx = findViewById(R.id.tiet_expiry);

        findViewById(R.id.btn_pickiad).setOnClickListener(v -> pickAcquire());
        findViewById(R.id.btn_pickied).setOnClickListener(v -> pickExpiry());

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());

        getVehicles();
        getToday();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cal = Calendar.getInstance();
        Log.i(Const.TAG, "onStart: " + cal.getTimeInMillis());
    }

    private void getVehicles() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_VEHICLES, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    dat = new String[2][dc + 1];
                    dat[0][0] = null;
                    dat[1][0] = "Select vehicle...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            dat[0][i + 1] = dt.getString("vehicleId");
                            dat[1][i + 1] = dt.getString("numberPlate");
                        }
                    }
                    initSpinner();
                } else {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Oops! Invalid data format", Toast.LENGTH_SHORT).show();
                Log.e(Const.TAG, e.getMessage(), e);
            }
        }, error -> {
            ad.dismiss();
            Fxns.checkErr(this, error);
        });
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void initSpinner() {
        Spinner spCar = findViewById(R.id.sp_vehicles);

        List<String> lofd = new ArrayList<>(Arrays.asList(dat[1]));
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.spinner_item, lofd) {
            @Override
            public boolean isEnabled(int pos) {
                return true;//pos != 0;
            }

            @Override
            public View getDropDownView(int pos, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(pos, convertView, parent);
                TextView tv = (TextView) view;
                if (pos == 0) tv.setTextColor(Color.GRAY);
                else tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        adapt.setDropDownViewResource(R.layout.spinner_item);
        spCar.setAdapter(adapt);
        spCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                spp = pos;
                if (pos > 0) llHd.setVisibility(View.VISIBLE);
                else llHd.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void validateInp() {
        String amt = tietAm.getText().toString().trim();
        String com = tietCo.getText().toString().trim();
        String acq = tietAc.getText().toString().trim();
        String exp = tietEx.getText().toString().trim();

        if (amt.isEmpty()) {
            tietAm.requestFocus();
            tietAm.setError("Enter amount payed");
            return;
        } else if (com.isEmpty()) {
            tietCo.requestFocus();
            tietCo.setError("Enter insurance company name");
            return;
        }

        if (adet != 0 && edet != 0) {
            if (adet < 0) {
                tietAc.requestFocus();
                tietAc.setError("Pick correct insurance acquired date");
                return;
            } else if (edet < 0) {
                tietEx.requestFocus();
                tietEx.setError("Pick correct insurance expiry date");
                return;
            } else if (adet >= edet) {
                Toast.makeText(this, "Please Check your inputs and pick correct dates *", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Log.i(Const.TAG, "validateInp: Ok: " + adet + " => " + edet);

                setReminder(cal);
            }
        } else {
            Log.i(Const.TAG, "validateInp: " + adet + " => : " + edet);
            Toast.makeText(this, "Please pick insurance acquire and expiry dates", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("vehicleid", dat[0][spp]);
        pms.put("amount", amt);
        pms.put("company", com);
        pms.put("acquiredate", acq);
        pms.put("acqmillis", "" + adet);
        pms.put("expirydate", exp);
        pms.put("expmillis", "" + edet);

        signUp(pms);
    }

    private void signUp(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_INSURANCE, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                /*if (!obj.getBoolean("err")) {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Oops! Invalid data format", Toast.LENGTH_SHORT).show();
                Log.e(Const.TAG, e.getMessage(), e);
            }
        }, error -> {
            ad.dismiss();
            Fxns.checkErr(this, error);
        }) {
            @Override
            protected Map<String, String> getParams() {
                return pms;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
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
            tietAc.setText(String.format(Locale.US, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year));

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
                tietAc.requestFocus();
                tietAc.setError("Please pick insurance acquire date");
            } else if (edet != 0 && adet >= edet) {
                Toast.makeText(this, "Please Check your inputs and pick correct dates", Toast.LENGTH_SHORT).show();
            }

        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void pickExpiry() {
        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            tietEx.setText(String.format(Locale.US, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year));

            Calendar cae = Calendar.getInstance();
            cae.set(Calendar.YEAR, year);
            cae.set(Calendar.MONTH, monthOfYear);
            cae.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            edet = cae.getTimeInMillis();
            Log.i(Const.TAG, "pickExpiry : " + adet + " => pickExpiry : " + edet);

            if (edet == 0) {
                tietEx.requestFocus();
                tietEx.setError("Please pick insurance acquire date");
            } else if (adet != 0 && adet >= edet) {
                Toast.makeText(this, "Please Check your inputs and pick correct dates", Toast.LENGTH_SHORT).show();
            }

        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void setReminder(Calendar c) {
        Calendar ca = Calendar.getInstance();
        //ca.add(Calendar.MINUTE, 1);
        ca.add(Calendar.SECOND, 10);
        String cds = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());

        AlarmManager aMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 147, intent, 0);
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
        builder.setMessage("Insurance reminder started for "+cds+" \nYou will be reminded to go and replace your insurance cover before it expires").setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cancelReminder() {
        AlarmManager aMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 147, intent, 0);
        aMgr.cancel(pendingIntent);

        Toast.makeText(this, "Insurance reminder canceled", Toast.LENGTH_SHORT).show();
    }
}