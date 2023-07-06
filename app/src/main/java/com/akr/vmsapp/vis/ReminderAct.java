package com.akr.vmsapp.vis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akr.vmsapp.WelcomeAct;
import com.example.vmsapp.R;

public class ReminderAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reminder);

        findViewById(R.id.btn).setOnClickListener(v -> onBackPressed());
    }

    private void cancelReminder() {
        AlarmManager aMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 147, intent, 0);
        aMgr.cancel(pendingIntent);

        Toast.makeText(this, "Insurance reminder canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelReminder();
        startActivity(new Intent(this, WelcomeAct.class));
    }
}