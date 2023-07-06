package com.akr.vmsapp.vis;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.akr.vmsapp.vis.ReminderAct;
import com.example.vmsapp.R;
public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "com.akr.vmsapp.channelID";
    public static final String channelName = "com.akr.vmsapp.ChannelName";
    private NotificationManager mManager;
    private Context mCtx;

    public NotificationHelper(Context base) {
        super(base);
        mCtx = base;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //createChannel();

            NotificationChannel channel = new NotificationChannel(
                    channelID, channelName, NotificationManager.IMPORTANCE_HIGH
            );
            getManager().createNotificationChannel(channel);
        }
    }

    /*private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelID, channelName, NotificationManager.IMPORTANCE_HIGH
            );
            getManager().createNotificationChannel(channel);
        }
    }*/

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        PendingIntent contentIntent = PendingIntent.getActivity(mCtx, 101, new Intent(mCtx, ReminderAct.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx, channelID)
                .setSmallIcon(R.drawable.ic_alarm_on)
                .setContentTitle("Alert! Alert! Alert!")
                .setContentText("You should go and renew insurance. Your Insurance will expire soon na makanjo wanaeza kushika alaf kuumane")
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return builder;
    }
}
