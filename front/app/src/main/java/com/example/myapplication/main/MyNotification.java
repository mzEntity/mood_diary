package com.example.myapplication.main;

import com.example.myapplication.R;
import com.example.myapplication.main.MainActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyNotification extends Service {

    private NotificationManager notificationManager;
    private Notification notification;
    private NotificationChannel notificationChannel;
    private final static String NOTIFICATION_CHANNEL_ID = "CHANNEL_ID";
    private final static String NOTIFICATION_CHANNEL_NAME = "CHANNEL_NAME";
    private final static int FOREGROUND_ID=1;

    private PendingIntent pendingIntent;
    private BroadcastReceiver receiver;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int score = intent.getIntExtra("score", 100);
                notification = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("您的心情分数已收到")
                        .setContentText(getResult(score))
                        .setContentIntent(pendingIntent)
                        .build();
                notification.flags |= Notification.FLAG_NO_CLEAR;
                startForeground(FOREGROUND_ID, notification);
            }
        };

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RATE_SCORE");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("MyNotification", "onStartCommand");
        notificationChannel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        intent = new Intent(getApplicationContext(), MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("快来为心情打分吧")
                .setContentText("")
                .setContentIntent(pendingIntent)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(FOREGROUND_ID, notification);
        return Service.START_STICKY;
    }

    private String getResult(int score){
        if(score > 90){
            return "快来将好心情分享给大家吧";
        } else if (score > 70){
            return "今天心情很不错";
        } else if(score > 50){
            return "有什么心事吗？分享给我吧！";
        } else {
            return "别难过了，我们一直在！";
        }
    }
}

