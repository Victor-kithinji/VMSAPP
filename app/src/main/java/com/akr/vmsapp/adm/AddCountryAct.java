package com.akr.vmsapp.adm;

import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCountryAct extends AppCompatActivity {

    private TextInputEditText tietNm, tietI2, tietI3, tietPP, tietCu, tietCC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_country);

        tietNm = findViewById(R.id.tiet_name);
        tietI2 = findViewById(R.id.tiet_iso2);
        tietI3 = findViewById(R.id.tiet_iso3);
        tietPP = findViewById(R.id.tiet_phone_prefix);
        tietCu = findViewById(R.id.tiet_currency);
        tietCC = findViewById(R.id.tiet_capital_city);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());
    }

    private void validateInp() {
        String nem = tietNm.getText().toString().trim();
        String ic2 = tietI2.getText().toString().trim();
        String ic3 = tietI3.getText().toString().trim();
        String ppx = tietPP.getText().toString().trim();
        String cur = tietCu.getText().toString().trim();
        String ccy = tietCC.getText().toString().trim();

        if (nem.isEmpty()) {
            tietNm.requestFocus();
            tietNm.setError("Enter country name");
            return;
        } else if (ic2.isEmpty()) {
            tietI2.requestFocus();
            tietI2.setError("Enter iso code II");
            return;
        } else if (ic3.isEmpty()) {
            tietI3.requestFocus();
            tietI3.setError("Enter iso code III");
            return;
        } else if (ppx.isEmpty()) {
            tietPP.requestFocus();
            tietPP.setError("Enter phone prefix");
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("name", nem);
        pms.put("iso_code_2", ic2);
        pms.put("iso_code_3", ic3);
        pms.put("phone_prefix", ppx);
        pms.put("capital_city", ccy);
        pms.put("currency", cur);

        submit(pms);
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_COUNTRY, response -> {
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