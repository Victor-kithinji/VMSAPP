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

import com.akr.vmsapp.mod.Model;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.Fxns;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.PrefManager;
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

public class AddVehicleAct extends AppCompatActivity {

    private TextInputEditText tietPl, tietWe, tietCl, tietSe;
    private LinearLayout llHd;
    private int spp = 0;
    private String[] dat;
    private List<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_vehicle);

        tietPl = findViewById(R.id.tiet_plate);
        tietWe = findViewById(R.id.tiet_weight);
        tietCl = findViewById(R.id.tiet_color);
        tietSe = findViewById(R.id.tiet_sector);
        llHd = findViewById(R.id.ll_hide);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());

        getModels();
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

                    list = new ArrayList<>();
                    list.add(new Model(null, null, "Select model...", null, null, null));

                    dat = new String[dc + 1];
                    dat[0] = "Select model...";
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            list.add(new Model(
                                    dt.getString("modelId"), dt.getString("brandId"),
                                    dt.getString("name"), dt.getString("typeId"),
                                    null, null
                            ));
                            dat[i + 1] = dt.getString("name");
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
        Spinner spMod = findViewById(R.id.sp_models);

        List<String> lofd = new ArrayList<>(Arrays.asList(dat));
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

    private void validateInp() {
        String pla = tietPl.getText().toString().trim();
        String wei = tietWe.getText().toString().trim();
        String col = tietCl.getText().toString().trim();
        String sec = tietSe.getText().toString().trim();

        if (spp <= 0) {
            Toast.makeText(this, "Please select vehicle model", Toast.LENGTH_SHORT).show();
            return;
        } else if (pla.isEmpty()) {
            tietPl.requestFocus();
            tietPl.setError("Enter number plate");
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("clientid", PrefManager.getInstance(this).getOwnId() + "");
        pms.put("numberplate", pla);
        pms.put("modelid", list.get(spp).getModId());
        pms.put("weight", wei);
        pms.put("color", col);
        pms.put("sector", sec);
        pms.put("typeid", list.get(spp).getTypId());

        submit(pms);
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_VEHICLE, response -> {
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