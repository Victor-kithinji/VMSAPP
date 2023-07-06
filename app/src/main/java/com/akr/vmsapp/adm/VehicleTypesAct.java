package com.akr.vmsapp.adm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.ada.VehicleTypesAdapter;
import com.akr.vmsapp.mod.VehicleType;
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

public class VehicleTypesAct extends AppCompatActivity {

    private RecyclerView rvData;
    private VehicleTypesAdapter mAdapter;
    private List<VehicleType> data;
    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_vehicle_types);
        setTitle("Vehicle types");

        buildRecycler();
    }

    private void buildRecycler() {
        tvNoData = findViewById(R.id.tv_nodata);
        rvData = findViewById(R.id.rv_vehicle_types);

        data = new ArrayList<>();

        rvData.setHasFixedSize(true);
        LinearLayoutManager layMgr = new LinearLayoutManager(this);
        rvData.setLayoutManager(layMgr);
        rvData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new VehicleTypesAdapter(data, this);
        rvData.setAdapter(mAdapter);

        getVehicleTypes();
    }

    private void getVehicleTypes() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_VEHICLE_TYPES, response -> {
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

                            data.add(new VehicleType(
                                    usr.getString("typeId"), usr.getString("name"),
                                    usr.getString("description"), usr.getString("addDate")
                            ));
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        rvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No vehicle types available. Please contact System Admin to add some");
                    }
                } else {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    rvData.setVisibility(View.GONE);
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