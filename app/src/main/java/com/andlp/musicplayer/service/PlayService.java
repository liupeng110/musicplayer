package com.andlp.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.activity.Activity_Group;

/**
 * 717219917@qq.com  2017/9/22 11:18
 */
public class PlayService extends Service {
    String tag = "PlayService";
    private int num = 0;
    public static final int TYPE_Media = 7;
    //创建MyBinder实例  返回用
    private MyBinder myBinder = new MyBinder();
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onStartCommand()");
//     Notification.Builder builder = new Notification.Builder(this.getApplicationContext());  // 在API11之后构建Notification的方式
//      Intent nfIntent = new Intent(this, Activity_Group.class);
//        builder.setContentIntent(PendingIntent.
//      getActivity(this, 0, nfIntent, 0))//设置PendingIntent
////       .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
////       .setContentTitle("下拉列表中的Title")          // 设置下拉列表里的标题
////       .setSmallIcon(R.mipmap.ic_launcher)          // 设置状态栏内的小图标
////       .setContentText("要显示的内容")                   // 设置上下文内容
//       .setWhen(System.currentTimeMillis());        // 设置该通知发生的时间
//        Notification notification = builder.build();       // 获取构建好的Notification



//        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

        notifi_0("标题","内容");
        return super.onStartCommand(intent, flags, startId);
    }

    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static final String ACTION_CLOSE_NOTICE = "cn.campusapp.action.closenotice";
    public static final int NOTICE_ID_TYPE_0 = R.string.app_name;
    private void notifi_0(String title,String content){
      android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.service_notifi_small);
        remoteViews.setTextViewText(R.id.title_tv, title);
        remoteViews.setTextViewText(R.id.content_tv, content);
//        remoteViews.setTextViewText(R.id.time_tv, getTime());
        remoteViews.setImageViewResource(R.id.icon_iv, R.mipmap.img_notification);
        remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());
        Intent intent = new Intent(this, Activity_Group.class);
        intent.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice_view_type_0, pendingIntent);
        int requestCode1 = (int) SystemClock.uptimeMillis();
        Intent intent1 = new Intent(ACTION_CLOSE_NOTICE);
        intent1.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, requestCode1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.close_iv, pendingIntent1);
        builder.setSmallIcon(R.mipmap.img_notification);
        builder.setContent(remoteViews);
        builder.setShowWhen(false);//不显示时间

        RemoteViews remoteViews2 = new RemoteViews(getPackageName(), R.layout.service_notifi_big);
        remoteViews2.setTextViewText(R.id.title_tv, title);
        remoteViews2.setTextViewText(R.id.content_tv, content);
//        remoteViews.setTextViewText(R.id.time_tv, getTime());
        remoteViews2.setImageViewResource(R.id.icon_iv, R.mipmap.img_notification);
        remoteViews2.setInt(R.id.close_iv, "setColorFilter", getIconColor());

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.img_notification));
        builder.setCustomBigContentView(remoteViews2);

        Notification notification = builder.build();

//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTICE_ID_TYPE_0, notification);

        startForeground(110, notification);// 开始前台服务
    }
    private void notifi_1(){  //默认通知
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);



        Notification notification = builder
                .setContentTitle("标题")
                .setContentText("这是内容")
                .setSmallIcon(R.mipmap.img_notification)
                .build();
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(R.string.app_name, notification);
        startForeground(110, notification);// 开始前台服务
    }

    //获取系统通知栏字体默认颜色
    public static int getIconColor(){
        return Color.parseColor("#999999");
    }


        @Override public void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy()");
       stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
    }

    //创建一个binder类，实例化之后就可以在Onbind中返回
    public class  MyBinder extends Binder {
        public int getNum(){
            return num;
        }
    }

}
