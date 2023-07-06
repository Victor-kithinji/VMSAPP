package com.akr.vmsapp.own;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.gen.CountriesAct;
import com.akr.vmsapp.mec.MecSignInAct;
import com.akr.vmsapp.mod.Owner;
import com.akr.vmsapp.uti.Const;
import com.akr.vmsapp.uti.MySingleton;
import com.akr.vmsapp.uti.PrefManager;
import com.akr.vmsapp.uti.URLs;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vmsapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OwnSignUpAct extends AppCompatActivity {

    private static final int LAUNCH_COUNTRIES = 12;
    private TextInputEditText tietFN, tietLN, tietSN, tietCC, tietPN, tietDoB, tietUN, tietEml, tietID, tietLoc, tietPwd, tietCPw;
    private RadioGroup rgG;
    private RadioButton rbM, rbF, rbO;
    private String pcod = null, ccod = null;
    private long pdob = 0;
    private PrefManager pm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pm = PrefManager.getInstance(this);
        findViewById(R.id.ll_hide).setVisibility(View.GONE);
        findViews();
    }

    private void findViews() {
        tietFN = findViewById(R.id.tiet_fname);
        tietLN = findViewById(R.id.tiet_lname);
        tietSN = findViewById(R.id.tiet_sname);

        rgG = findViewById(R.id.rg_gender);
        rbM = findViewById(R.id.rb_male);
        rbF = findViewById(R.id.rb_female);
        rbO = findViewById(R.id.rb_other);

        tietCC = findViewById(R.id.tiet_ccode);

        tietPN = findViewById(R.id.tiet_phone);
        tietDoB = findViewById(R.id.tiet_dob);
        tietUN = findViewById(R.id.tiet_uname);
        tietEml = findViewById(R.id.tiet_email);
        tietID = findViewById(R.id.tiet_idnumber);
        tietLoc = findViewById(R.id.tiet_locname);
        tietPwd = findViewById(R.id.tiet_password);
        tietCPw = findViewById(R.id.tiet_cpassword);

        tietCC.setOnClickListener(v -> {
            Intent sui = new Intent(this, CountriesAct.class);
            startActivityForResult(sui, LAUNCH_COUNTRIES);
        });
        findViewById(R.id.btn_pickdob).setOnClickListener(v -> pickDoB());
        findViewById(R.id.btn_signup).setOnClickListener(v -> validateInp());
        findViewById(R.id.tv_signin).setOnClickListener(v -> startActivity(new Intent(this, MecSignInAct.class)));
    }

    private void pickDoB() {
        int mYear, mMonth, mDay, hod, min, sec;

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        hod = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);

        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            tietDoB.setText(String.format(Locale.US, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year));

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.set(Calendar.HOUR_OF_DAY, hod);
            cal.set(Calendar.MINUTE, min);
            cal.set(Calendar.SECOND, sec);

            pdob = cal.getTimeInMillis();
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void validateInp() {
        String fnem = tietFN.getText().toString().trim();
        String lnem = tietLN.getText().toString().trim();
        String snem = tietSN.getText().toString().trim();
        String mfo;
        String pnum = tietPN.getText().toString().trim();
        String dob = tietDoB.getText().toString().trim();
        String unem = tietUN.getText().toString().trim();
        String mail = tietEml.getText().toString().trim();
        String idno = tietID.getText().toString().trim();
        String locn = tietLoc.getText().toString().trim();
        String pwd = tietPwd.getText().toString().trim();
        String cpw = tietCPw.getText().toString().trim();

        if (fnem.isEmpty()) {
            tietFN.requestFocus();
            tietFN.setError("Enter your firstname");
            return;
        } else if (lnem.isEmpty()) {
            tietLN.requestFocus();
            tietLN.setError("Enter your lastname");
            return;
        } else if (snem.isEmpty()) {
            tietSN.requestFocus();
            tietSN.setError("Enter your surname");
            return;
        }

        int radioId = rgG.getCheckedRadioButtonId();
        RadioButton crb = findViewById(radioId);
        if (crb == rbM) {
            mfo = "M";
        } else if (crb == rbF) {
            mfo = "F";
        } else if (crb == rbO) {
            mfo = "O";
        } else {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ccod == null || pcod == null) {
            Intent sui = new Intent(this, CountriesAct.class);
            startActivityForResult(sui, LAUNCH_COUNTRIES);
            return;
        }

        if (pnum.isEmpty()) {
            tietPN.requestFocus();
            tietPN.setError("Enter your phone number");
            return;
        } else if (pdob <= 0 || dob.isEmpty()) {
            tietDoB.requestFocus();
            tietDoB.setError("Enter/Pick your Date of Birth");
            return;
        } else if (unem.isEmpty()) {
            tietUN.requestFocus();
            tietUN.setError("Enter your username");
            return;
        } else if (mail.isEmpty()) {
            tietEml.requestFocus();
            tietEml.setError("Enter your email address");
            return;
        } else if (idno.isEmpty()) {
            tietID.requestFocus();
            tietID.setError("Enter your ID number");
            return;
        } else if (locn.isEmpty()) {
            tietLoc.requestFocus();
            tietLoc.setError("Enter your location");
            return;
        }

        if (pwd.isEmpty()) {
            tietPwd.requestFocus();
            tietPwd.setError("Enter your password");
            return;
        } else if (cpw.isEmpty()) {
            tietCPw.requestFocus();
            tietCPw.setError("Enter confirm password");
            return;
        } else if (!pwd.equals(cpw)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> pms = new HashMap<>();
        pms.put("username", unem);
        pms.put("firstname", fnem);
        pms.put("lastname", lnem);
        pms.put("surname", snem);
        pms.put("ccode", ccod);
        pms.put("phone", pnum);
        pms.put("email", mail);
        pms.put("idnumber", idno);
        pms.put("locname", locn);
        pms.put("gender", mfo);
        pms.put("dob", dob);
        pms.put("dobmillis", "" + pdob);
        pms.put("password", pwd);
        pms.put("cpassword", cpw);
        pms.put("lat", pm.getLat() != null ? pm.getLat() : "");
        pms.put("lng", pm.getLng() != null ? pm.getLng() : "");

        signUp(pms);
    }

    private void signUp(Map<String, String> pms) {
        StringRequest request = new StringRequest(Request.Method.POST, URLs.OWN_REGISTER, response -> {
            Log.i(Const.TAG, response);
            try {
                JSONObject obj = new JSONObject(response);
                if (!obj.getBoolean("err")) {
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                    JSONObject usr = obj.getJSONObject("data");
                    Owner own = new Owner(
                            usr.getString("ownerId"), usr.getString("username"),
                            usr.getString("firstname"), usr.getString("lastname"),
                            usr.getString("surname"), usr.getString("cCode"),
                            usr.getString("phone"), usr.getString("email"),
                            usr.getString("idNumber"), usr.getString("gender"),
                            usr.getString("dob"), usr.getString("photoUrl"),
                            usr.getString("lat"), usr.getString("lng"),
                            usr.getString("locName"), usr.getString("status"),
                            usr.getString("regDate"), usr.getString("password")
                    );
                    PrefManager.getInstance(this).saveOwner(own);

                    startActivity(new Intent(this, OwnDashAct.class));
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
            Toast.makeText(this, "Oops! An error occurred", Toast.LENGTH_SHORT).show();
            Log.e(Const.TAG, error.getMessage(), error);
        }) {
            @Override
            protected Map<String, String> getParams() {
                return pms;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == LAUNCH_COUNTRIES) {
            if (resCode == Activity.RESULT_OK) {
                if (data != null) {
                    pcod = data.getStringExtra("pcode");
                    ccod = data.getStringExtra("ccode");
                    tietCC.setText(String.format("(%s) %s", ccod, pcod));
                }
            }// else if (resCode == Activity.RESULT_CANCELED) {}
        }
    }
}