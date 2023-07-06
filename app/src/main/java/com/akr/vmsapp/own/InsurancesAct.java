package com.akr.vmsapp.own;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akr.vmsapp.ada.InsurancesAdapter;
import com.akr.vmsapp.mod.Insurance;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.Fxns;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.URLs;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vmsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InsurancesAct extends AppCompatActivity {

    private ListView lvData;
    private List<Insurance> data;
    private TextView tvNoData;
    private InsurancesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_insurances);

        data = new ArrayList<>();
        lvData = findViewById(R.id.lv_insurances);
        tvNoData = findViewById(R.id.tv_nodata);

        adapter = new InsurancesAdapter(this, R.layout.lay_item_data, data);
        lvData.setAdapter(adapter);

        getInsurances();
    }

    private void getInsurances() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_INSURANCES, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    if (dc > 0) {
                        JSONObject usr;
                        for (int i = 0; i < dc; i++) {
                            usr = arr.getJSONObject(i);
                            data.add(new Insurance(
                                    usr.getString("insuranceId"), usr.getString("vehicleId"), usr.getString("acquiryDate"),
                                    usr.getString("expiryDate"), usr.getString("company"), usr.getString("amount"),
                                    usr.getString("photoUrl"), usr.getString("status"), usr.getString("addDate")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        lvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No insurances available. Please contact System Admin to add some");
                    }
                } else {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    lvData.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(obj.getString("msg"));
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
}