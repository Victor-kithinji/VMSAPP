package com.akr.vmsapp.gen;

import android.content.Intent;
import android.net.Uri;
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

import com.akr.vmsapp.ada.GaragesAdapter;
import com.akr.vmsapp.mod.Garage;
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

public class GaragesAct extends AppCompatActivity {

    private RecyclerView rvData;
    private GaragesAdapter mAdapter;
    private List<Garage> data;
    private TextView tvNoData;
    private PrefManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_garages);
        setTitle("Garages");

        pm = PrefManager.getInstance(this);
        buildRecycler();
    }

    private void buildRecycler() {
        tvNoData = findViewById(R.id.tv_nodata);
        rvData = findViewById(R.id.rv_garages);

        data = new ArrayList<>();

        rvData.setHasFixedSize(true);
        LinearLayoutManager layMgr = new LinearLayoutManager(this);
        rvData.setLayoutManager(layMgr);
        rvData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new GaragesAdapter(data, this);
        rvData.setAdapter(mAdapter);

        mAdapter.setOnGarageEar(new GaragesAdapter.OnGarage() {
            @Override
            public void onCol(String fon) {
                doCall(fon);
            }

            @Override
            public void onMsg(String eml) {
                doMail(eml);
            }

            @Override
            public void onGml(String lat, String lng, String nem) {
                Intent gml = new Intent(GaragesAct.this, MapLocAct.class);
                gml.putExtra("lat", lat);
                gml.putExtra("lng", lng);
                gml.putExtra("name", nem);
                startActivity(gml);
            }
        });

        getGarages("ASC");
    }

    private void doCall(String fon) {
        String uri = "tel:" + fon.trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
        /*OR*/
        /*String uri = "tel:" + fon.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(GaragesAct.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(GaragesAct.this, "Permission Not Granted ", Toast.LENGTH_SHORT).show();

            final String[] ACP = {Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(GaragesAct.this, ACP, 9);
        } else {
            startActivity(intent);
        }*/
    }

    private void doMail(String eml) {
        String[] recipients = eml.split(",");
        Intent ist = new Intent(Intent.ACTION_SEND);
        ist.putExtra(Intent.EXTRA_EMAIL, recipients);
        ist.putExtra(Intent.EXTRA_SUBJECT, "INQUIRY");
        ist.putExtra(Intent.EXTRA_TEXT, "Hi, \n\n");
        ist.setType("message/rfc822");
        startActivity(Intent.createChooser(ist, "Choose an email client"));
    }

    private void getGarages(String so) {
        data.clear();
        AlertDialog ad = new AlertDialog.Builder(this).setView(R.layout.progress).setCancelable(false).create();
        ad.show();

        StringRequest request = new StringRequest(Request.Method.POST, URLs.GET_GARAGES + "&so=" + so, response -> {
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

                            data.add(new Garage(
                                    usr.getString("garageId"), usr.getString("name"),
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
                        mAdapter.notifyDataSetChanged();
                    } else {
                        rvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Sorry! No garages available. Please contact System Admin to add some garages");
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


    private void sendEMail(String eml) {
        Intent ist = new Intent(Intent.ACTION_SEND);//common intent
        // ist.setData(Uri.parse("mailto:")); // only email apps should handle this
        ist.setType("message/rfc822"); // "text/plain" "message/rfc822"
        ist.putExtra(Intent.EXTRA_EMAIL, eml);
        ist.putExtra(Intent.EXTRA_SUBJECT, "Your Subject Here");
        ist.putExtra(Intent.EXTRA_TEXT, "E-mail body");
        startActivity(Intent.createChooser(ist, "Send Email"));

        /*If you use ACTION_SENDTO, putExtra does not work to add subject and text to the intent.
        Use Uri to add the subject and body text.*/
        /*Intent ist = new Intent(Intent.ACTION_SENDTO);
        ist.setType("text/plain"); // "message/rfc822"
        ist.putExtra(Intent.EXTRA_EMAIL, eml);
        ist.putExtra(Intent.EXTRA_SUBJECT, "");
        ist.putExtra(Intent.EXTRA_TEXT, "Hi, I'm email body.\n\n");
        startActivity(Intent.createChooser(ist, "Send Email"));*/

        /*Intent iss = new Intent(Intent.ACTION_SEND);
        iss.setType("text/html"); // message/rfc822 is not indicating "only offer email clients"
        iss.putExtra(Intent.EXTRA_EMAIL, eml);
        iss.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
        iss.putExtra(Intent.EXTRA_TEXT, "Hi, I'm email body.\n\n");
        startActivity(Intent.createChooser(iss, "Send Email"));*/

        /*Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, eml);
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app to handle the action", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app to handle the action", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sr_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_refresh) {
            getGarages("ASC");
        } else if (item.getItemId() == R.id.mi_sort_asc) {
            getGarages("ASC");
        } else if (item.getItemId() == R.id.mi_sort_desc) {
            getGarages("DESC");
        }
        return super.onOptionsItemSelected(item);
    }
}