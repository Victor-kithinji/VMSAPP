package com.akr.vmsapp.uti;

import android.content.Context;
import android.content.SharedPreferences;

import com.akr.vmsapp.mod.Admin;
import com.akr.vmsapp.mod.Maker;
import com.akr.vmsapp.mod.Owner;

public class PrefManager {
    private static final String PREF_FILE_KEY = "com.akr.vmsapp.makernowner";
    private static final String LATI_LONG_KEY = "com.akr.vmsapp.lat_and_lng";
    private static PrefManager mInstance;
    private static Context mCtx;

    private PrefManager(Context ctx) {
        mCtx = ctx;
    }

    public static synchronized PrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new PrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveLatLng(Double lat, Double lng) {
        SharedPreferences pref = mCtx.getSharedPreferences(LATI_LONG_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString(Const.ALL_SESS_LAT, "" + lat);
        edt.putString(Const.ALL_SESS_LNG, "" + lng);
        edt.apply();
    }

    public String getLat() {
        SharedPreferences pref = mCtx.getSharedPreferences(LATI_LONG_KEY, Context.MODE_PRIVATE);
        return pref.getString(Const.ALL_SESS_LAT, null);
    }
    public String getLng() {
        SharedPreferences pref = mCtx.getSharedPreferences(LATI_LONG_KEY, Context.MODE_PRIVATE);
        return pref.getString(Const.ALL_SESS_LNG, null);
    }

    public void saveMaker(Maker us) {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.clear();
        edt.apply();
        edt.putString(Const.MEC_SESS_MECID, us.getMecId());
        edt.putString(Const.MEC_SESS_GRGID, us.getMecId());
        edt.putString(Const.MOO_SESS_UNAME, us.getUsername());
        edt.putString(Const.MOO_SESS_FNAME, us.getFirstname());
        edt.putString(Const.MOO_SESS_LNAME, us.getLastname());
        edt.putString(Const.MOO_SESS_SNAME, us.getSurname());
        edt.putString(Const.MOO_SESS_CCODE, us.getCountryCode());
        edt.putString(Const.MOO_SESS_PHONE, us.getPhone());
        edt.putString(Const.MOO_SESS_EMAIL, us.getEmail());
        edt.putString(Const.MOO_SESS_IDNUM, us.getIdNumber());
        edt.putString(Const.MOO_SESS_GENDA, us.getGender());
        edt.putString(Const.MOO_SESS_DOBAT, us.getDob());
        edt.putString(Const.MOO_SESS_PHOTO, us.getPhotoUrl());
        edt.putString(Const.MOO_SESS_LAT, us.getLat());
        edt.putString(Const.MOO_SESS_LNG, us.getLng());
        edt.putString(Const.MOO_SESS_LOCNAME, us.getLocName());
        edt.putString(Const.MOO_SESS_STATUS, us.getStatus());
        edt.putString(Const.MOO_SESS_REGDATE, us.getRegDate());
        edt.putString(Const.MOO_SESS_PASSWORD, us.getPassword());
        edt.putString(Const.MOO_SESS_DISTANCE, null);
        edt.apply();
    }

    public void saveOwner(Owner us) {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.clear();
        edt.apply();
        edt.putString(Const.OWN_SESS_OWNID, us.getOwnId());
        edt.putString(Const.MOO_SESS_UNAME, us.getUsername());
        edt.putString(Const.MOO_SESS_FNAME, us.getFirstname());
        edt.putString(Const.MOO_SESS_LNAME, us.getLastname());
        edt.putString(Const.MOO_SESS_SNAME, us.getSurname());
        edt.putString(Const.MOO_SESS_CCODE, us.getCountryCode());
        edt.putString(Const.MOO_SESS_PHONE, us.getPhone());
        edt.putString(Const.MOO_SESS_EMAIL, us.getEmail());
        edt.putString(Const.MOO_SESS_IDNUM, us.getIdNumber());
        edt.putString(Const.MOO_SESS_GENDA, us.getGender());
        edt.putString(Const.MOO_SESS_DOBAT, us.getDob());
        edt.putString(Const.MOO_SESS_PHOTO, us.getPhotoUrl());
        edt.putString(Const.MOO_SESS_LAT, us.getLat());
        edt.putString(Const.MOO_SESS_LNG, us.getLng());
        edt.putString(Const.MOO_SESS_LOCNAME, us.getLocName());
        edt.putString(Const.MOO_SESS_STATUS, us.getStatus());
        edt.putString(Const.MOO_SESS_REGDATE, us.getRegDate());
        edt.putString(Const.MOO_SESS_PASSWORD, us.getPassword());
        edt.apply();
    }

    public void saveAdmin(Admin us) {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.clear();
        edt.apply();
        edt.putString(Const.ADM_SESS_ADMID, us.getAdmId());
        edt.putString(Const.MOO_SESS_UNAME, us.getUsername());
        edt.putString(Const.MOO_SESS_FNAME, us.getFirstname());
        edt.putString(Const.MOO_SESS_LNAME, us.getLastname());
        edt.putString(Const.MOO_SESS_SNAME, us.getSurname());
        edt.putString(Const.MOO_SESS_CCODE, us.getCountryCode());
        edt.putString(Const.MOO_SESS_PHONE, us.getPhone());
        edt.putString(Const.MOO_SESS_EMAIL, us.getEmail());
        edt.putString(Const.MOO_SESS_IDNUM, us.getIdNumber());
        edt.putString(Const.MOO_SESS_GENDA, us.getGender());
        edt.putString(Const.MOO_SESS_DOBAT, us.getDob());
        edt.putString(Const.MOO_SESS_PHOTO, us.getPhotoUrl());
        edt.putString(Const.MOO_SESS_STATUS, us.getStatus());
        edt.putString(Const.MOO_SESS_REGDATE, us.getRegDate());
        edt.putString(Const.MOO_SESS_PASSWORD, us.getPassword());
        edt.apply();
    }

    public Maker getMaker() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        return new Maker(
                pref.getString(Const.MEC_SESS_MECID, null),
                pref.getString(Const.MEC_SESS_GRGID, null),
                pref.getString(Const.MOO_SESS_UNAME, null),
                pref.getString(Const.MOO_SESS_FNAME, null),
                pref.getString(Const.MOO_SESS_LNAME, null),
                pref.getString(Const.MOO_SESS_SNAME, null),
                pref.getString(Const.MOO_SESS_CCODE, null),
                pref.getString(Const.MOO_SESS_PHONE, null),
                pref.getString(Const.MOO_SESS_EMAIL, null),
                pref.getString(Const.MOO_SESS_IDNUM, null),
                pref.getString(Const.MOO_SESS_GENDA, null),
                pref.getString(Const.MOO_SESS_DOBAT, null),
                pref.getString(Const.MOO_SESS_PHOTO, null),
                pref.getString(Const.MOO_SESS_LAT, null),
                pref.getString(Const.MOO_SESS_LNG, null),
                pref.getString(Const.MOO_SESS_LOCNAME, null),
                pref.getString(Const.MOO_SESS_STATUS, null),
                pref.getString(Const.MOO_SESS_REGDATE, null),
                pref.getString(Const.MOO_SESS_PASSWORD, null), null
        );
    }

    public Owner getOwner() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        return new Owner(
                pref.getString(Const.OWN_SESS_OWNID, null),
                pref.getString(Const.MOO_SESS_UNAME, null),
                pref.getString(Const.MOO_SESS_FNAME, null),
                pref.getString(Const.MOO_SESS_LNAME, null),
                pref.getString(Const.MOO_SESS_SNAME, null),
                pref.getString(Const.MOO_SESS_CCODE, null),
                pref.getString(Const.MOO_SESS_PHONE, null),
                pref.getString(Const.MOO_SESS_EMAIL, null),
                pref.getString(Const.MOO_SESS_IDNUM, null),
                pref.getString(Const.MOO_SESS_GENDA, null),
                pref.getString(Const.MOO_SESS_DOBAT, null),
                pref.getString(Const.MOO_SESS_PHOTO, null),
                pref.getString(Const.MOO_SESS_LAT, null),
                pref.getString(Const.MOO_SESS_LNG, null),
                pref.getString(Const.MOO_SESS_LOCNAME, null),
                pref.getString(Const.MOO_SESS_STATUS, null),
                pref.getString(Const.MOO_SESS_REGDATE, null),
                pref.getString(Const.MOO_SESS_PASSWORD, null)
        );
    }

    public Admin getAdmin() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        return new Admin(
                pref.getString(Const.ADM_SESS_ADMID, null),
                pref.getString(Const.MOO_SESS_UNAME, null),
                pref.getString(Const.MOO_SESS_FNAME, null),
                pref.getString(Const.MOO_SESS_LNAME, null),
                pref.getString(Const.MOO_SESS_SNAME, null),
                pref.getString(Const.MOO_SESS_CCODE, null),
                pref.getString(Const.MOO_SESS_PHONE, null),
                pref.getString(Const.MOO_SESS_EMAIL, null),
                pref.getString(Const.MOO_SESS_IDNUM, null),
                pref.getString(Const.MOO_SESS_GENDA, null),
                pref.getString(Const.MOO_SESS_DOBAT, null),
                pref.getString(Const.MOO_SESS_PHOTO, null),
                pref.getString(Const.MOO_SESS_STATUS, null),
                pref.getString(Const.MOO_SESS_REGDATE, null),
                pref.getString(Const.MOO_SESS_PASSWORD, null)
        );
    }

    public String getMecId() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        return (pref.contains(Const.MEC_SESS_MECID)) ? pref.getString(Const.MEC_SESS_MECID, null) : null;
    }

    public String getAdmId() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        return pref.getString(Const.ADM_SESS_ADMID, null);
    }

    public String getOwnId() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        return pref.getString(Const.OWN_SESS_OWNID, null);
    }

    public void logout() {
        SharedPreferences pref = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.clear();
        edt.apply();
        // mCtx.startActivity(new Intent(mCtx, toClass));
    }
}