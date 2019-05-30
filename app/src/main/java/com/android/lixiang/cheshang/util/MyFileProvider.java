package com.android.lixiang.cheshang.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.android.lixiang.cheshang.R;

/**
 * Created by LaoZhao on 2017/11/19.
 */

public class MyFileProvider extends FileProvider{}