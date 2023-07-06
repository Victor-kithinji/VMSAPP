package com.akr.vmsapp.mec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class AddSpareShopAct extends AppCompatActivity {

    private TextInputEditText tietNm, tietBx, tietZC, tietTw, tietBL, tietT1, tietT2, tietT3, tietE1, tietE2, tietFx, tietLo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_garage);

        tietNm = findViewById(R.id.tiet_name);
        tietBx = findViewById(R.id.tiet_box);
        tietZC = findViewById(R.id.tiet_zipcode);
        tietTw = findViewById(R.id.tiet_town);
        tietBL = findViewById(R.id.tiet_box_locdesc);
        tietT1 = findViewById(R.id.tiet_tel1);
        tietT2 = findViewById(R.id.tiet_tel2);
        tietT3 = findViewById(R.id.tiet_tel3);
        tietE1 = findViewById(R.id.tiet_email1);
        tietE2 = findViewById(R.id.tiet_email2);
        tietFx = findViewById(R.id.tiet_fax);
        tietLo = findViewById(R.id.tiet_location);

        findViewById(R.id.btn_submit).setOnClickListener(v -> validateInp());
    }

    private void validateInp() {
        String nem = tietNm.getText().toString().trim();
        String box = tietBx.getText().toString().trim();
        String zcd = tietZC.getText().toString().trim();
        String tow = tietTw.getText().toString().trim();
        String bld = tietBL.getText().toString().trim();
        String te1 = tietT1.getText().toString().trim();
        String te2 = tietT2.getText().toString().trim();
        String te3 = tietT3.getText().toString().trim();
        String em1 = tietE1.getText().toString().trim();
        String em2 = tietE2.getText().toString().trim();
        String fax = tietFx.getText().toString().trim();
        String loc = tietLo.getText().toString().trim();

        if (nem.isEmpty()) {
            tietNm.requestFocus();
            tietNm.setError("Enter garage name");
            return;
        } else if (box.isEmpty()) {
            tietBx.requestFocus();
            tietBx.setError("Enter box number");
            return;
        } else if (zcd.isEmpty()) {
            tietZC.requestFocus();
            tietZC.setError("Enter zip code number");
            return;
        } else if (tow.isEmpty()) {
            tietTw.requestFocus();
            tietTw.setError("Enter P.O Box town");
            return;
        } else if (te1.isEmpty()) {
            tietT1.requestFocus();
            tietT1.setError("Enter telephone 1: (required)");
            return;
        } else if (em1.isEmpty()) {
            tietE1.requestFocus();
            tietE1.setError("Enter email 1: (required)");
            return;
        } else if (loc.isEmpty()) {
            tietLo.requestFocus();
            tietLo.setError("Enter garage location");
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("name", nem);
        pms.put("ccode", "KE");
        pms.put("pobox", "P.O Box " + box + "-" + zcd + ", " + ((bld != null) ? bld : "") + " " + tow);
        pms.put("box", box);
        pms.put("zipcode", zcd);
        pms.put("locdesc", bld);
        pms.put("town", tow);
        pms.put("telone", te1);
        pms.put("teltwo", te2);
        pms.put("telthree", te3);
        pms.put("emailone", em1);
        pms.put("emailtwo", em2);
        pms.put("faxnumber", fax);
        pms.put("locname", loc);
        submit(pms);
    }

    private void submit(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADD_SPARE_SHOP, response -> {
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