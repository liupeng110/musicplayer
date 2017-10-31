package com.andlp.musicplayer.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.andlp.musicplayer.MyApp;
import com.andlp.musicplayer.R;
import com.andlp.musicplayer.activity.Activity_Group;
import com.andlp.musicplayer.config.Constant;
import com.andlp.musicplayer.util.L;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/22 11:18
 */
public class Service_Play extends Service {
    String title,content;
    NotificationCompat.Builder builder;
    Notification notification;
    private boolean ci_press=false,play_press=true;
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        L.d("进入playservice--onCreate()");
        HermesEventBus.getDefault().register(this);
        HermesEventBus.getDefault().post("我是service发的");
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        L.d("进入playservice--onStartCommand()");
        notifi_my("弹剑记.落魄江湖十五载","于魁智","于魁智-弹剑记.落魄江湖十五载");
        return super.onStartCommand(intent, flags, startId);
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void rec_fromReceiver(String rec) {
        L.e("接收到event---->" + rec);
        switch (rec){
            case Constant.exit :   close();     break;
            case Constant.left:       left();      break;
            case Constant.play:   pause();     break;
            case Constant.right:   right();      break;
            case Constant.ci:            ci();       break;
            case Constant.open :   open();    break;
            case Constant.other:   other();    break;
            default: break;
        }
    }


    private void notifi_my(String title_temp, String content_temp, String ticker){//自定义通知
        L.e( "进入norifi_my---->" + title_temp+",content_temp:"+content_temp+",ticker:"+ticker);
         title=title_temp;content=content_temp;
        builder = new NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setTicker(ticker);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setShowWhen(false);
        notify_small();//初始化小通知栏
       // notify_big();   //初始化大通知栏

          notification = builder.build();
        startForeground(Constant.service_ID, notification);// 服务 id,取消时用

    }
    private RemoteViews notify_small(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.service_notifi_small);
        remoteViews.setTextViewText(R.id.title_tv, title);
        remoteViews.setTextViewText(R.id.content_tv, content);
        remoteViews.setImageViewResource(R.id.icon_iv, R.mipmap.img_notification);
        remoteViews.setInt(R.id.notifi_close, "setColorFilter", getIconColor());

        Intent intent_left = new Intent(Constant.left);//监听 上一曲
        PendingIntent pendingIntent_left = PendingIntent.getBroadcast(this, 1, intent_left, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_left, pendingIntent_left);


        Intent intent_pause = new Intent(Constant.play);//监听 暂停
        PendingIntent pendingIntent_pause = PendingIntent.getBroadcast(this, 2, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_pause, pendingIntent_pause);

        Intent intent_right = new Intent(Constant.right);//监听 下一曲
        PendingIntent pendingIntent_right = PendingIntent.getBroadcast(this, 3, intent_right, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_right, pendingIntent_right);


        Intent intent1 = new Intent(Constant.exit);//监听关闭
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 4, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_close, pendingIntent1);


        Intent intent_Main = new Intent(Intent.ACTION_MAIN);//监听logo事件
        intent_Main.addCategory(Intent.CATEGORY_LAUNCHER);
        intent_Main.setClass(this, Activity_Group.class);
        intent_Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent_open = PendingIntent.getActivity(this, 4, intent_Main, PendingIntent.FLAG_UPDATE_CURRENT);
//    PendingIntent pendingIntent_open = PendingIntent.getBroadcast(this, 4, intent_open, PendingIntent.FLAG_UPDATE_CURRENT);//
        remoteViews.setOnClickPendingIntent(R.id.icon_iv, pendingIntent_open);


        builder.setSmallIcon(R.mipmap.img_notification);
        builder.setContent(remoteViews);//通知栏小布局
        return remoteViews;
    }
    private RemoteViews  notify_big(){
        RemoteViews remoteViews2 = new RemoteViews(getPackageName(), R.layout.service_notifi_big);
        remoteViews2.setTextViewText(R.id.title_tv, title);
        remoteViews2.setTextViewText(R.id.content_tv, content);
        remoteViews2.setImageViewResource(R.id.icon_iv_big, R.mipmap.img_notification);
        remoteViews2.setInt(R.id.notifi_close_big, "setColorFilter", getIconColor());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.img_notification));
        builder.setCustomBigContentView(remoteViews2);//通知栏大布局


        Intent intent_left = new Intent(Constant.left);//监听 上一曲
        PendingIntent pendingIntent_left = PendingIntent.getBroadcast(this, 5, intent_left, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_left_big, pendingIntent_left);//一个监听


        Intent intent_pause = new Intent(Constant.play);//监听 暂停
        PendingIntent pendingIntent_pause = PendingIntent.getBroadcast(this, 6, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_pause_big, pendingIntent_pause);//一个监听

        Intent intent_right = new Intent(Constant.right);//监听 下一曲
        PendingIntent pendingIntent_right = PendingIntent.getBroadcast(this, 7, intent_right, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_right_big, pendingIntent_right);//一个监听


        Intent intent_close = new Intent(Constant.exit);//监听关闭
        PendingIntent pendingIntent_close = PendingIntent.getBroadcast(this, 8, intent_close, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_close_big, pendingIntent_close);                  //又一个监听

        Intent intent_ci = new Intent(Constant.ci);//监听关闭
        PendingIntent pendingIntent_ci = PendingIntent.getBroadcast(this, 8, intent_ci, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_ci_big, pendingIntent_ci);                                //又一个监听

        Intent intent_Main = new Intent(Intent.ACTION_MAIN);//监听logo事件
        intent_Main.addCategory(Intent.CATEGORY_LAUNCHER);
        intent_Main.setClass(this, Activity_Group.class);
        intent_Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent_open = PendingIntent.getActivity(this, 4, intent_Main, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.icon_iv, pendingIntent_open);

       return remoteViews2;
    }

    private void close() {
        L.i("service 进入close");
        stopForeground(false);
        Intent stopIntent = new Intent(this, Service_Play.class);
        stopService(stopIntent);
        MyApp.exit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
////  NotificationManager noti_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////  noti_manager.cancel(Constant.PLAYSERVICE_ID);
    }
     private void left(){
         L.i("service 进入left");
     }
     @SuppressLint("RestrictedApi")
     private void pause(){

         L.i("pause中builder.big是否为null--:"+builder.getBigContentView());
         if (builder.getBigContentView()!=null){
             if (play_press) {
                 try{ builder.getBigContentView().setImageViewResource(R.id.notifi_pause_big,R.mipmap.notifi_play);}catch (Throwable t){t.printStackTrace();}
             }else {
                 try{builder.getBigContentView().setImageViewResource(R.id.notifi_pause_big,R.mipmap.notifi_pause);}catch (Throwable t){t.printStackTrace();}
             }
         }

         RemoteViews remoteViews =notify_small(); //重新new因为从builder获取为空
         L.i("pause中smailview是否为null--:"+remoteViews);
         if (play_press) {
             remoteViews.setImageViewResource(R.id.notifi_pause,R.mipmap.notifi_play);
         }else {
             remoteViews.setImageViewResource(R.id.notifi_pause,R.mipmap.notifi_pause);
         }
         remoteViews.setViewVisibility(R.id.notice_view_type_0, View.VISIBLE);
//         remoteViews.setViewVisibility(R.id.notice_view_type_0, View.INVISIBLE);
//         remoteViews.setViewVisibility(R.id.notice_view_type_0, View.GONE);
         play_press=!play_press;
         builder.setContent(remoteViews);//通知栏小布局
         notification = builder.build();
         startForeground(Constant.service_ID, notification);//更新通知栏ui
     }
     private void right(){
          L.i("service 进入right");
     }
     @SuppressLint("RestrictedApi")
     private void ci(){  //需要持久化保存
         if (builder.getBigContentView()!=null){
             if (ci_press){
                 try{builder.getBigContentView().setImageViewResource(R.id.notifi_ci_big,R.mipmap.notifi_ci);}catch (Throwable t){t.printStackTrace();}
             }else {
                 try{builder.getBigContentView().setImageViewResource(R.id.notifi_ci_big,R.mipmap.notifi_ci_p);}catch (Throwable t){t.printStackTrace();}
             }
             ci_press=!ci_press;
             notification = builder.build();
             startForeground(Constant.service_ID, notification);
         }

     }
     private void open(){
     }
    private void other(){
    }

    public static int getIconColor(){
        return Color.parseColor("#999999");
    }  //获取系统通知栏字体默认颜色
     @Override public void onDestroy() {
        super.onDestroy();
        L.e( "销毁");
      HermesEventBus.getDefault().destroy();
       stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
    }

}
