package com.akr.vmsapp.gen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.ada.RatingsAdapter;
import com.akr.vmsapp.mod.Rating;
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

public class MecRatingsAct extends AppCompatActivity {

    private ListView lvData;
    private List<Rating> data;
    private TextView tvNoData;
    private RatingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mec_ratings);
        setTitle("Mechanics rating");

        data = new ArrayList<>();
        lvData = findViewById(R.id.lv_mec_ratings);
        tvNoData = findViewById(R.id.tv_nodata);

        adapter = new RatingsAdapter(this, R.layout.lay_item_data, data);
        lvData.setAdapter(adapter);

        getMecRatings();
    }

    private void getMecRatings() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_MEC_RATINGS, response -> {
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
                            data.add(new Rating(
                                    usr.getString("ratingId"), usr.getString("ownerId"),
                                    usr.getString("makerId"), Float.parseFloat(usr.getString("stars")),
                                    usr.getString("comments"), usr.getString("addDate")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        lvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No garage ratings available. Please contact System Admin to add some");
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