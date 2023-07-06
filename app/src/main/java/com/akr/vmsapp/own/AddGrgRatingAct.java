package com.akr.vmsapp.own;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class AddGrgRatingAct extends AppCompatActivity {

    private TextInputEditText tietCo;
    private LinearLayout llHd;
    private int gspp = 0;
    private String[][] datg;
    private RatingBar rbGrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_grg_rating);

        rbGrg = findViewById(R.id.rb_stars);
        tietCo = findViewById(R.id.tiet_comments);
        llHd = findViewById(R.id.ll_hide);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());

        getGarages();
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
                if (pos > 0) llHd.setVisibility(View.VISIBLE);
                else llHd.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void validateInp() {
        float sta = rbGrg.getRating();
        String com = tietCo.getText().toString().trim();

        if (gspp <= 0) {
            Toast.makeText(this, "Please select garage", Toast.LENGTH_SHORT).show();
            return;
        } else if (sta < 0) {
            Toast.makeText(this, "You have not rated the garage selected", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("clientid", PrefManager.getInstance(this).getOwnId() + "");
        pms.put("garageid", datg[0][gspp]);
        pms.put("stars", "" + sta);
        pms.put("comments", com);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification").setMessage("Your rating is " + sta + " \nWould you like to change the rating?").setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, id) -> dialog.dismiss()).setNegativeButton("No", (dialog, which) -> submit(pms));
        builder.create().show();//dialog.dismiss()
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_GRG_RATING, response -> {
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