package com.akr.vmsapp.mec;

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

public class AddSparePartAct extends AppCompatActivity {

    private TextInputEditText tietNm, tietPz, tietRC;
    private LinearLayout llHd;
    private int spp = 0, sspp = 0;
    private String[][] dat, dats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_spare_part);

        tietNm = findViewById(R.id.tiet_name);
        tietPz = findViewById(R.id.tiet_price);
        tietRC = findViewById(R.id.tiet_roscost);
        llHd = findViewById(R.id.ll_hide);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());

        getModels();
        getSpareShops();
    }

    private void getModels() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_MODELS, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    dat = new String[2][dc + 1];
                    dat[0][0] = null;
                    dat[1][0] = "Select brand...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            dat[0][i + 1] = dt.getString("modelId");
                            dat[1][i + 1] = dt.getString("name");
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

    private void getSpareShops() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_SPARE_SHOPS, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    dats = new String[2][dc + 1];
                    dats[0][0] = null;
                    dats[1][0] = "Select spare parts shop...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            dats[0][i + 1] = dt.getString("spareShopId");
                            dats[1][i + 1] = dt.getString("name");
                        }
                    }
                    initSpinnerSps();
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
        Spinner spMod = findViewById(R.id.sp_models);

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
        spMod.setAdapter(adapt);
        spMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initSpinnerSps() {
        Spinner spSps = findViewById(R.id.sp_spareshops);

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
        spSps.setAdapter(adapt);
        spSps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                sspp = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void validateInp() {
        String nem = tietNm.getText().toString().trim();
        String prz = tietPz.getText().toString().trim();
        String ros = tietRC.getText().toString().trim();

        if (nem.isEmpty()) {
            tietNm.requestFocus();
            tietNm.setError("Enter spare part name");
            return;
        } else if (prz.isEmpty()) {
            tietPz.requestFocus();
            tietPz.setError("Enter price");
            return;
        } else if (ros.isEmpty()) {
            tietRC.requestFocus();
            tietRC.setError("Enter replacement or service cost");
            return;
        } else if (spp <= 0) {
            Toast.makeText(this, "Please select vehicle model", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("name", nem);
        pms.put("price", prz);
        pms.put("roscost", ros);
        pms.put("spareshopid", (sspp > 0) ? dats[0][sspp] : "");
        pms.put("carmodelid", (spp > 0) ? dat[0][spp] : "");

        submit(pms);
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_SPARE_PART, response -> {
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
}