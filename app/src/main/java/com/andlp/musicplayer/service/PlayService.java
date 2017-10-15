package com.andlp.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.activity.Activity_Group;

/**
 * 717219917@qq.com  2017/9/22 11:18
 */
public class PlayService extends Service {
    String tag = "PlayService";
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onStartCommand()");
     Notification.Builder builder = new Notification.Builder// 在API11之后构建Notification的方式
             (this.getApplicationContext()); //获取一个Notification构造器
      Intent nfIntent = new Intent(this, Activity_Group.class);
        builder.setContentIntent(PendingIntent.
      getActivity(this, 0, nfIntent, 0))//设置PendingIntent
       .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
       .setContentTitle("下拉列表中的Title")         // 设置下拉列表里的标题
       .setSmallIcon(R.mipmap.ic_launcher)          // 设置状态栏内的小图标
       .setContentText("要显示的内容")               // 设置上下文内容
       .setWhen(System.currentTimeMillis());        // 设置该通知发生的时间

        Notification notification = builder.build();       // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

       // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, notification);// 开始前台服务

        return super.onStartCommand(intent, flags, startId);
    }


    @Override public void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy()");
       stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知

    }
}
