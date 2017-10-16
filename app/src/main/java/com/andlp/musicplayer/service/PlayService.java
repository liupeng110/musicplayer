package com.andlp.musicplayer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.andlp.musicplayer.MyApp;
import com.andlp.musicplayer.R;
import com.andlp.musicplayer.activity.Activity_Group;
import com.andlp.musicplayer.config.Constant;
import com.andlp.musicplayer.util.L;

/**
 * 717219917@qq.com  2017/9/22 11:18
 */
public class PlayService extends Service {
    String tag = "PlayService";
    private int num = 0;
    public static final int TYPE_Media = 7;
    PlayBroadcastReceiver  receiver=null;                            //接收,处理通知栏按键事件
    private MyBinder myBinder = new MyBinder(); //创建MyBinder实例  返回用
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onStartCommand()");
    if (receiver==null){registerBroadcastReceiver();}
        notifi_my("弹剑记.落魄江湖十五载","于魁智","于魁智-弹剑记.落魄江湖十五载");
        return super.onStartCommand(intent, flags, startId);
    }

    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static final String ACTION_CLOSE_NOTIFI= "com.andlp.musicplayer.action.close";

    private void notifi_my(String title,String content,String ticker){//自定义通知
      android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setTicker(ticker);
        builder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.service_notifi_small);
        remoteViews.setTextViewText(R.id.title_tv, title);
        remoteViews.setTextViewText(R.id.content_tv, content);
        remoteViews.setImageViewResource(R.id.icon_iv, R.mipmap.img_notification);
        remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());

//        Intent intent = new Intent(this, Activity_Group.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        int requestCode = (int) SystemClock.uptimeMillis();
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.notice_view_type_0, pendingIntent);//一个监听

        Intent intent1 = new Intent(ACTION_CLOSE_NOTIFI);//关闭
        int requestCode1 = (int) SystemClock.uptimeMillis();
        intent1.putExtra(NOTICE_ID_KEY, R.string.app_name);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, requestCode1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.close_iv, pendingIntent1);                  //又一个监听

        builder.setSmallIcon(R.mipmap.img_notification);
        builder.setContent(remoteViews);//通知栏小布局
        builder.setShowWhen(false);

        RemoteViews remoteViews2 = new RemoteViews(getPackageName(), R.layout.service_notifi_big);
        remoteViews2.setTextViewText(R.id.title_tv, title);
        remoteViews2.setTextViewText(R.id.content_tv, content);
        remoteViews2.setImageViewResource(R.id.icon_iv, R.mipmap.img_notification);
        remoteViews2.setInt(R.id.close_iv, "setColorFilter", getIconColor());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.img_notification));
        builder.setCustomBigContentView(remoteViews2);//通知栏大布局

//        Intent intent_big = new Intent(this, Activity_Group.class);
//        intent_big.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        int requestCode_big = (int) SystemClock.uptimeMillis();
//        PendingIntent pendingIntent_big = PendingIntent.getActivity(this, requestCode_big, intent_big, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews2.setOnClickPendingIntent(R.id.notice_view_type_0_big, pendingIntent_big);//一个监听

        Intent buttonIntent = new Intent(ACTION_CLOSE_NOTIFI);
        buttonIntent.putExtra(ACTION_CLOSE_NOTIFI, NOTICE_ID_KEY);
        PendingIntent intent_paly = PendingIntent.getBroadcast(this, 110, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.close_iv, intent_paly);



        Notification notification = builder.build();
        startForeground(Constant.SERVICE_ID, notification);// 服务 id,取消时用
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
        startForeground(Constant.SERVICE_ID, notification);// 开始前台服务 id作为标记  取消时用
    }


    private void registerBroadcastReceiver(){
        receiver = new PlayBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CLOSE_NOTIFI);
         registerReceiver(receiver, intentFilter);
    }


    public class PlayBroadcastReceiver extends BroadcastReceiver {

        @Override  public void onReceive(Context context, Intent intent) {
            init(context, intent.getAction());
        }


        private void init(Context context, String action) {
            L.i(tag,"收到广播:"+action);

            if (action.equals("com.andlp.musicplayer.action.play")) {

            }else if (action.equals("com.andlp.musicplayer.action.close")){
                L.i(tag,"进入close:"+action);

                       clear_notifi();
            }
        }
    }



    public void clear_notifi() {
        stopForeground(true);
        MyApp.exit();
//        if (receiver != null) {
//             unregisterReceiver(receiver);
//        }
//        NotificationManager noti_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        noti_manager.cancel(Constant.SERVICE_ID);
    }

    //获取系统通知栏字体默认颜色
    public static int getIconColor(){
        return Color.parseColor("#999999");
    }

        @Override public void onDestroy() {
        super.onDestroy();
        L.i(tag, "onDestroy()");
   try{if (receiver != null) { unregisterReceiver(receiver);  }}catch (Throwable t){t.printStackTrace();}
       stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知

    }

    //创建一个binder类，实例化之后就可以在Onbind中返回
    public class  MyBinder extends Binder {
        public int getNum(){
            return num;
        }
    }

}
