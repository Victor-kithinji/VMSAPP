package com.akr.vmsapp.uti;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.NetworkError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class Fxns {

    public static void checkErr(Context ctx, VolleyError err) {
        Log.e(Const.TAG, err.getMessage(), err);

        if (err instanceof NetworkError) tMsg(ctx, "Network Error!. Check your connection");
        else if (err instanceof TimeoutError) tMsg(ctx, "Timeout Error! Please Try Again");
        else tMsg(ctx, "Oops! An unknown Error occurred. Please Try Again");
    }

    public static void tMsg(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showProgress(Context ctx, String ttl, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ttl != null ? ttl : "Progress").setMessage(msg).setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }
}
