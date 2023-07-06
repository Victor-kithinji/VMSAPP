package com.akr.vmsapp.own;

import android.graphics.Color;
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
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.Fxns;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.URLs;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vmsapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddExpenseAct extends AppCompatActivity {

    private TextInputEditText tietAm, tietDe;
    private LinearLayout llHd;
    private int spp = 0, gspp = 0, mspp = 0, sspp = 0;
    private String[][] dat, datg, datm, dats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_expense);

        tietAm = findViewById(R.id.tiet_amount);
        tietDe = findViewById(R.id.tiet_description);
        llHd = findViewById(R.id.ll_hide);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());

        getVehicles();
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

        getGarages();
        getMakers();
        getSpareParts();
    }

    private void validateInp() {
        String amt = tietAm.getText().toString().trim();
        String des = tietDe.getText().toString().trim();

        if (spp <= 0) {
            Toast.makeText(this, "Please select your vehicle", Toast.LENGTH_SHORT).show();
            return;
        } else if (amt.isEmpty()) {
            tietAm.requestFocus();
            tietAm.setError("Enter amount incurred");
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("vehicleid", dat[0][spp]);
        pms.put("garageid", gspp > 0 ? datg[0][gspp] : "");
        pms.put("mechanicid", mspp > 0 ? datm[0][mspp] : "");
        pms.put("sparepartid", sspp > 0 ? dats[0][sspp] : "");
        pms.put("amount", amt);
        pms.put("description", des);

        submit(pms);
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_EXPENSE, response -> {
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


    private void getGarages() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_GARAGES, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    datg = new String[2][dc + 1];
                    datg[0][0] = null;
                    datg[1][0] = "Select garage...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            datg[0][i + 1] = dt.getString("garageId");
                            datg[1][i + 1] = dt.getString("name");
                        }
                    }
                    initSpinnerGrg();
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

    private void initSpinnerGrg() {
        Spinner spGrg = findViewById(R.id.sp_garages);

        List<String> lofd = new ArrayList<>(Arrays.asList(datg[1]));
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
        spGrg.setAdapter(adapt);
        spGrg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gspp = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getMakers() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_MAKERS, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    datm = new String[2][dc + 1];
                    datm[0][0] = null;
                    datm[1][0] = "Select mechanic...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            datm[0][i + 1] = dt.getString("makerId");
                            datm[1][i + 1] = dt.getString("firstname") + " " + dt.getString("lastname");
                        }
                    }
                    initSpinnerMec();
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

    private void initSpinnerMec() {
        Spinner spMec = findViewById(R.id.sp_makers);

        List<String> lofd = new ArrayList<>(Arrays.asList(datm[1]));
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
        spMec.setAdapter(adapt);
        spMec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mspp = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getSpareParts() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_SPARE_PARTS, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    dats = new String[2][dc + 1];
                    dats[0][0] = null;
                    dats[1][0] = "Select spare part...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            dats[0][i + 1] = dt.getString("sparePartId");
                            dats[1][i + 1] = dt.getString("name");
                        }
                    }
                    initSpinnerSpp();
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

    private void initSpinnerSpp() {
        Spinner spSpp = findViewById(R.id.sp_spareparts);

        List<String> lofd = new ArrayList<>(Arrays.asList(dats[1]));
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
        spSpp.setAdapter(adapt);
        spSpp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                sspp = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}