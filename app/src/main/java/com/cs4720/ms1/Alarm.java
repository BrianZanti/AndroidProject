package com.cs4720.ms1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Brian on 11/28/2015.
 */
public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("================", "STARTING SERVICE");
        Intent i = new Intent(context, TrackCoords.class);
        i.putExtra("name", intent.getStringExtra("name"));
        context.startService(i);
    }

    public void setAlarm(Context context, String name, long start)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        i.putExtra("name",name);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, start, pi); // Millisec * Second * Minute
    }
}
