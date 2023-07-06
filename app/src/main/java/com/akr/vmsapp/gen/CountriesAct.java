package com.akr.vmsapp.gen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.ada.CountriesAdapter;
import com.akr.vmsapp.mod.Country;
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

public class CountriesAct extends AppCompatActivity {

    private ListView lvData;
    private List<Country> data;
    private CountriesAdapter adapter;
    private TextView tvNoData;
    private final String[] ctrs = new String[]{
            "Algeria (+213)", "Angola (+244)", "Benin (+229)", "Botswana (+267)", "Burkina Faso (+226)",
            "Burundi (+257)", "Cameroon (+237)", "Cape Verde (+238)", "Central African Republic (+236)",
            "Comoros (+269)", "Democratic Republic of Congo (+243)", "Djibouti (+253)", "Egypt (+20)",
            "Equatorial Guinea (+240)", "Eritrea (+291)", "Ethiopia (+251)", "Gabon (+241)", "Gambia (+220)",
            "Ghana (+233)", "Guinea (+224)", "Guinea-Bissau (+245)", "Ivory Coast (+225)", "Kenya (+254)",
            "Lesotho (+266)", "Liberia (+231)", "Libya (+218)", "Madagascar (+261)", "Malawi (+265)",
            "Mali (+223)", "Mauritania (+222)", "Mauritius (+230)", "Morocco (+212)", "Mozambique (+258)",
            "Namibia (+264)", "Niger (+227)", "Nigeria (+234)", "Republic of the Congo (+242)", "Reunion (+262)",
            "Rwanda (+250)", "Saint Helena (+290)", "Sao Tome and Principe (+239)", "Senegal (+221)",
            "Seychelles (+248)", "Sierra Leone (+232)", "Somalia (+252)", "South Africa ( +27)", "South Sudan (+211)",
            "Sudan (+249)", "Swaziland (+268)", "Tanzania (+255)", "Togo (+228)", "Tunisia (+216)",
            "Uganda (+256)", "Western Sahara (+212)", "Zambia (+260)", "Zimbabwe (+263)"
    };
    private final String[] cods = new String[]{
            "+213", "+244", "+229", "+267", "+226", "+257", "+237", "+238", "+236", "+269", "+243", "+253", "+20",
            "+240", "+291", "+251", "+241", "+220", "+233", "+224", "+245", "+225", "+254", "+266", "+231", "+218",
            "+261", "+265", "+223", "+222", "+230", "+212", "+258", "+264", "+227", "+234", "+242", "+262", "+250",
            "+290", "+239", "+221", "+248", "+232", "+252", " +27", "+211", "+249", "+268", "+255", "+228", "+216",
            "+256", "+212", "+260", "+263"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_countries);
        setTitle("Countries");

        data = new ArrayList<>();
        lvData = findViewById(R.id.lv_countries);
        tvNoData = findViewById(R.id.tv_nodata);
        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        adapter = new CountriesAdapter(this, R.layout.lay_country, data);
        lvData.setAdapter(adapter);
        adapter.setListener(pos -> {
            Intent ri = new Intent();
            ri.putExtra("ccode", data.get(pos).getIsoCode2());
            ri.putExtra("pcode", data.get(pos).getPhonePrefix());
            setResult(Activity.RESULT_OK, ri);
            finish();
        });

        getCountries();
    }

    private void getCountries() {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_COUNTRIES, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    JSONArray arr = obj.getJSONArray("data");
                    int dc = arr.length();
                    if (dc > 0) {
                        JSONObject dt;
                        for (int i = 0; i < dc; i++) {
                            dt = arr.getJSONObject(i);

                            data.add(new Country(
                                    dt.getString("countryId"), dt.getString("name"),
                                    dt.getString("isoCode2"), null,
                                    dt.getString("phonePrefix"), null, null, null
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        lvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No countries available. Please contact System Admin to add some countries");
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