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

public class AddBrandAct extends AppCompatActivity {

    private TextInputEditText tietNm, tietCo, tietDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_brand);

        tietNm = findViewById(R.id.tiet_name);
        tietCo = findViewById(R.id.tiet_company);
        tietDe = findViewById(R.id.tiet_description);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());
    }

    private void validateInp() {
        String nem = tietNm.getText().toString().trim();
        String com = tietCo.getText().toString().trim();
        String des = tietDe.getText().toString().trim();

        if (nem.isEmpty()) {
            tietNm.requestFocus();
            tietNm.setError("Enter vehicle type name");
            return;
        } else if (com.isEmpty()) {
            tietCo.requestFocus();
            tietCo.setError("Enter brand parent company");
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("name", nem);
        pms.put("company", com);
        pms.put("description", des);

        submit(pms);
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_BRAND, response -> {
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