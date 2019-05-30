package com.android.lixiang.cheshang.util;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import static com.baidu.mapapi.BMapManager.getContext;


public class AlarmService extends Service {
    private Handler mHanler = new Handler(Looper.getMainLooper());
    public static String ACTION_ALARM = "action_alarm";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHanler.post(() -> {
            NotificationUtils notificationUtils = new NotificationUtils(getContext());
            notificationUtils.sendNotification(intent.getStringExtra("DETAIL"), "将于"+intent.getStringExtra("TIME").split(" ")[1] + "提醒");
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
