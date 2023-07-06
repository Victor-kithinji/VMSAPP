package com.akr.vmsapp.gen;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.ada.MakersAdapter;
import com.akr.vmsapp.mod.Maker;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.Fxns;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.PrefManager;
import com.akr.vmsapp.uti.URLs;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vmsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakersAct extends AppCompatActivity {

    private RecyclerView rvData;
    private MakersAdapter mAdapter;
    private List<Maker> data;
    private TextView tvNoData;
    private PrefManager pm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_makers);
        setTitle("Mechanics");

        pm = PrefManager.getInstance(this);
        buildRecycler();
    }

    private void buildRecycler() {
        rvData = findViewById(R.id.rv_makers);
        tvNoData = findViewById(R.id.tv_nodata);

        data = new ArrayList<>();

        rvData.setHasFixedSize(true);
        LinearLayoutManager layMgr = new LinearLayoutManager(this);
        rvData.setLayoutManager(layMgr);
        rvData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new MakersAdapter(data, this);
        rvData.setAdapter(mAdapter);

        getMakers("ASC");
    }

    private void getMakers(String so) {
        data.clear();
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_MAKERS + "&so=" + so, response -> {
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

                            data.add(new Maker(
                                    usr.getString("makerId"), usr.getString("garageId"),
                                    usr.getString("username"),
                                    usr.getString("firstname"), usr.getString("lastname"),
                                    usr.getString("surname"), usr.getString("cCode"),
                                    usr.getString("phone"), usr.getString("email"),
                                    usr.getString("idNumber"), usr.getString("gender"),
                                    usr.getString("dob"), usr.getString("photoUrl"),
                                    usr.getString("lat"), usr.getString("lng"),
                                    usr.getString("locName"), usr.getString("status"),
                                    usr.getString("regDate"), usr.getString("password"),
                                    usr.getString("distance")
                            ));
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        rvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No mechanics available. Please contact System Admin to add some mechanics");
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> pms = new HashMap<>();
                pms.put("lat", pm.getLat() != null ? pm.getLat() : "");
                pms.put("lng", pm.getLng() != null ? pm.getLng() : "");
                return pms;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sr_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_refresh) {
            getMakers("ASC");
        } else if (item.getItemId() == R.id.mi_sort_asc) {
            getMakers("ASC");
        } else if (item.getItemId() == R.id.mi_sort_desc) {
            getMakers("DESC");
        }
        return super.onOptionsItemSelected(item);
    }
}