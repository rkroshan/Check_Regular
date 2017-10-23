package com.roshan_kumar.alternatingcurrent006.check_regular.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;


public class alarmservice extends Service {

    SharedPreferences preferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    //    Toast.makeText(getApplicationContext(), "Notification Starts", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        preferences = getSharedPreferences("Reminder",MODE_PRIVATE);

        int[] h={12,7,22},m={45,0,0};

        String r1=preferences.getString("setReminder1","");
        String r2=preferences.getString("setReminder2","");
        String r3=preferences.getString("setReminder3","");

        if(!r1.isEmpty()){
            String[] t = r1.split(" ");
            h[0]=Integer.parseInt(t[0]); m[0]=Integer.parseInt(t[1]);}

        if(!r2.isEmpty()){
            String[] t = r2.split(" ");
            h[1]=Integer.parseInt(t[0]); m[1]=Integer.parseInt(t[1]);}

        if(!r3.isEmpty()){
            String[] t = r3.split(" ");
            h[2]=Integer.parseInt(t[0]); m[2]=Integer.parseInt(t[1]);}

        for(int i=0;i<3;i++){

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, h[i]);
            calendar.set(Calendar.MINUTE, m[i]);
            calendar.set(Calendar.SECOND, 0);

            Intent intent1 = new Intent(getApplicationContext(), alarm_receiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), Integer.parseInt(String.valueOf(h[i])+String.valueOf(m[i])), intent1, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);



            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
