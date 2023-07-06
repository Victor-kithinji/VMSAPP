package com.akr.vmsapp.gen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.ada.SpareShopsAdapter;
import com.akr.vmsapp.mod.SpareShop;
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

public class SpareShopsAct extends AppCompatActivity {

    private ListView lvData;
    private List<SpareShop> data;
    private TextView tvNoData;
    private SpareShopsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_spare_shops);
        setTitle("Spare shops");

        data = new ArrayList<>();
        lvData = findViewById(R.id.lv_spareshops);
        tvNoData = findViewById(R.id.tv_nodata);

        adapter = new SpareShopsAdapter(this, R.layout.lay_item_data, data);
        lvData.setAdapter(adapter);

        getSpareShops();
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
                    if (dc > 0) {
                        JSONObject usr;
                        for (int i = 0; i < dc; i++) {
                            usr = arr.getJSONObject(i);
                            data.add(new SpareShop(
                                    usr.getString("spareShopId"), usr.getString("name"),
                                    usr.getString("cCode"), usr.getString("POBox"),
                                    usr.getString("box"), usr.getString("zipCode"),
                                    usr.getString("boxDesc"), usr.getString("town"),
                                    usr.getString("tel1"), usr.getString("tel2"),
                                    usr.getString("tel3"), usr.getString("email1"),
                                    usr.getString("email2"), usr.getString("faxNumber"),
                                    usr.getString("photoUrl"), usr.getString("lat"),
                                    usr.getString("lng"), usr.getString("locName"),
                                    usr.getString("status"), usr.getString("addDate")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        lvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No spare parts shops available. Please contact System Admin to add some");
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