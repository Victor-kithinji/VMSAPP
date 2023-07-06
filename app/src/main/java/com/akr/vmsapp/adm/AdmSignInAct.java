package com.akr.vmsapp.adm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.mod.Admin;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.Fxns;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.PrefManager;
import com.akr.vmsapp.uti.URLs;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vmsapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdmSignInAct extends AppCompatActivity {

    private TextInputEditText tietPass, tietUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tietUser = findViewById(R.id.et_user);
        tietPass = findViewById(R.id.et_password);
        Button btn = findViewById(R.id.btn_signin);
        TextView tv = findViewById(R.id.tv_signup);

        btn.setOnClickListener(v -> validateInp());
        tv.setOnClickListener(v -> startActivity(new Intent(this, AdmSignUpAct.class)));
    }

    private void validateInp() {
        String usr = tietUser.getText().toString().trim();
        String pwd = tietPass.getText().toString().trim();

        if (usr.isEmpty()) {
            tietUser.requestFocus();
            tietUser.setError("Provide the required data");
            return;
        }
        if (pwd.isEmpty()) {
            tietPass.requestFocus();
            tietPass.setError("Provide password");
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("user", usr);
        pms.put("password", pwd);

        signIn(pms);
    }

    private void signIn(Map<String, String> pms) {
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ADM_LOGIN, response -> {
            ad.dismiss();
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                    JSONObject usr = obj.getJSONObject("data");
                    Admin adm = new Admin(
                            usr.getString("adminId"), usr.getString("username"),
                            usr.getString("firstname"), usr.getString("lastname"),
                            usr.getString("surname"), usr.getString("cCode"),
                            usr.getString("phone"), usr.getString("email"),
                            usr.getString("idNumber"), usr.getString("gender"),
                            usr.getString("dob"), usr.getString("photoUrl"),
                            usr.getString("status"),
                            usr.getString("regDate"), usr.getString("password")
                    );
                    PrefManager.getInstance(this).saveAdmin(adm);

                    startActivity(new Intent(this, AdmDashAct.class));
                    finish();
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                return pms;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}