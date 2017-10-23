package com.andlp.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.andlp.musicplayer.MyApp;
import com.andlp.musicplayer.R;
import com.andlp.musicplayer.activity.Activity_Group;
import com.andlp.musicplayer.config.Constant;
import com.andlp.musicplayer.util.L;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/22 11:18
 */
public class PlayService extends Service {
    public static final String ACTION="com.andlp.action";
    public static final String ACTION_CLOSE= ACTION+".close";
    public static final String ACTION_LEFT    =ACTION+ ".left";
    public static final String ACTION_PAUSE= ACTION+".pause";
    public static final String ACTION_RIGHT= ACTION+".right";
    public static final String ACTION_CI         = ACTION+".ci";
    public static final String ACTION_OPEN = ACTION+".open";//进入主界面
    String tag = "PlayService",title,content;
    private int num = 0;
    public static final int TYPE_Media = 7;
    PlayBroadcastReceiver  receiver=null;                            //接收,处理通知栏按键事件
    NotificationCompat.Builder builder;
    Notification notification;
    private boolean ci_press=false,play_press=true;
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        Log.d(tag, "进入playservice--onCreate()");
        HermesEventBus.getDefault().register(this);
        HermesEventBus.getDefault().post("我是service发的");
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "进入playservice--onStartCommand()");
        registerBroadcastReceiver();
        notifi_my("弹剑记.落魄江湖十五载","于魁智","于魁智-弹剑记.落魄江湖十五载");
        return super.onStartCommand(intent, flags, startId);
    }




    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void rec_activity(String rec) {
        L.e(tag+"接收到消息event---->" + rec);
    }


    private void notifi_my(String title_temp, String content_temp, String ticker){//自定义通知
         title=title_temp;content=content_temp;
        builder = new NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setTicker(ticker);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setShowWhen(false);
        notify_small();//初始化小通知栏
        notify_big();   //初始化大通知栏

          notification = builder.build();
        startForeground(Constant.PLAYSERVICE_ID, notification);// 服务 id,取消时用

    }

    private RemoteViews notify_small(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.service_notifi_small);
        remoteViews.setTextViewText(R.id.title_tv, title);
        remoteViews.setTextViewText(R.id.content_tv, content);
        remoteViews.setImageViewResource(R.id.icon_iv, R.mipmap.img_notification);
        remoteViews.setInt(R.id.notifi_close, "setColorFilter", getIconColor());

        Intent intent_left = new Intent(ACTION_LEFT);//监听 上一曲
        PendingIntent pendingIntent_left = PendingIntent.getBroadcast(this, 1, intent_left, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_left, pendingIntent_left);                  //一个监听


        Intent intent_pause = new Intent(ACTION_PAUSE);//监听 暂停
        PendingIntent pendingIntent_pause = PendingIntent.getBroadcast(this, 2, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_pause, pendingIntent_pause);      //一个监听

        Intent intent_right = new Intent(ACTION_RIGHT);//监听 下一曲
        PendingIntent pendingIntent_right = PendingIntent.getBroadcast(this, 3, intent_right, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_right, pendingIntent_right);           //一个监听


        Intent intent1 = new Intent(ACTION_CLOSE);//监听关闭
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 4, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_close, pendingIntent1);                  //又一个监听

        Intent intent_open = new Intent(ACTION_OPEN);//监听关闭
        PendingIntent pendingIntent_open = PendingIntent.getBroadcast(this, 4, intent_open, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.icon_iv, pendingIntent_open);           //又一个监听


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


        Intent intent_left = new Intent(ACTION_LEFT);//监听 上一曲
        PendingIntent pendingIntent_left = PendingIntent.getBroadcast(this, 5, intent_left, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_left_big, pendingIntent_left);//一个监听


        Intent intent_pause = new Intent(ACTION_PAUSE);//监听 暂停
        PendingIntent pendingIntent_pause = PendingIntent.getBroadcast(this, 6, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_pause_big, pendingIntent_pause);//一个监听

        Intent intent_right = new Intent(ACTION_RIGHT);//监听 下一曲
        PendingIntent pendingIntent_right = PendingIntent.getBroadcast(this, 7, intent_right, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_right_big, pendingIntent_right);//一个监听


        Intent intent_close = new Intent(ACTION_CLOSE);//监听关闭
        PendingIntent pendingIntent_close = PendingIntent.getBroadcast(this, 8, intent_close, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_close_big, pendingIntent_close);                  //又一个监听

        Intent intent_ci = new Intent(ACTION_CI);//监听关闭
        PendingIntent pendingIntent_ci = PendingIntent.getBroadcast(this, 8, intent_ci, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_ci_big, pendingIntent_ci);                                //又一个监听


        Intent intent_open = new Intent(ACTION_OPEN);//监听关闭
        PendingIntent pendingIntent_open = PendingIntent.getBroadcast(this, 9, intent_open, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.icon_iv_big, pendingIntent_open);           //又一个监听

       return remoteViews2;
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
        startForeground(Constant.PLAYSERVICE_ID, notification);// 开始前台服务 id作为标记  取消时用
    }

  //自发自收 模拟通知栏按钮事件的过滤器
    private void registerBroadcastReceiver(){
        L.i(tag+"注册registerBroadcastReceiver");
        receiver = new PlayBroadcastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(ACTION_CLOSE);
        filter.addAction(ACTION_LEFT);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_RIGHT);
        filter.addAction(ACTION_CI);
        filter.addAction(ACTION_OPEN);
       registerReceiver(receiver, filter);
    }
   //他处调用也发广播  兼容通知栏
    public class PlayBroadcastReceiver extends BroadcastReceiver {
        @Override  public void onReceive(Context context, Intent intent) {
            L.i(tag,"收到广播:"+ intent.getAction());
            switch ( intent.getAction()){
                case ACTION_CLOSE:   close(); break;
                case ACTION_LEFT:       left();     break;
                case ACTION_PAUSE:   pause();break;
                case ACTION_RIGHT:   right();break;
                case ACTION_CI:            ci();break;
                case ACTION_OPEN :   open();break;
            }
        }


    }

    private void close() {

        if (receiver != null) {
             unregisterReceiver(receiver);
        }
        stopForeground(false);
        Intent stopIntent = new Intent(this, PlayService.class);
        stopService(stopIntent);
        MyApp.exit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
////        NotificationManager noti_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////        noti_manager.cancel(Constant.PLAYSERVICE_ID);
    }
     private void left(){

     }
     private void pause(){
         if (play_press) {
            try{ builder.getBigContentView().setImageViewResource(R.id.notifi_pause_big,R.mipmap.notifi_play);}catch (Throwable t){t.printStackTrace();}
         }else {
             try{builder.getBigContentView().setImageViewResource(R.id.notifi_pause_big,R.mipmap.notifi_pause);}catch (Throwable t){t.printStackTrace();}
         }
         RemoteViews remoteViews =notify_small(); //重新new因为从builder获取为空
         if (play_press) {
             remoteViews.setImageViewResource(R.id.notifi_pause,R.mipmap.notifi_play);
         }else {
             remoteViews.setImageViewResource(R.id.notifi_pause,R.mipmap.notifi_pause);
         }
         remoteViews.setViewVisibility(R.id.notice_view_type_0, View.VISIBLE);
         remoteViews.setViewVisibility(R.id.notice_view_type_0, View.INVISIBLE);
         remoteViews.setViewVisibility(R.id.notice_view_type_0, View.GONE);
         play_press=!play_press;
         builder.setContent(remoteViews);//通知栏小布局
         notification = builder.build();
         startForeground(Constant.PLAYSERVICE_ID, notification);//更新通知栏ui
     }
     private void right(){

     }
     private void ci(){  //需要持久化保存
         if (ci_press){
             try{builder.getBigContentView().setImageViewResource(R.id.notifi_ci_big,R.mipmap.notifi_ci);}catch (Throwable t){t.printStackTrace();}
         }else {
           try{builder.getBigContentView().setImageViewResource(R.id.notifi_ci_big,R.mipmap.notifi_ci_p);}catch (Throwable t){t.printStackTrace();}
         }
         ci_press=!ci_press;
         notification = builder.build();
         startForeground(Constant.PLAYSERVICE_ID, notification);
     }

     private void open(){
         Intent intent = new Intent(this, Activity_Group.class);
       //  intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // intent.setAction(Intent.ACTION_MAIN);
         //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        // Intent intent = new Intent(this, Activity_Group.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
         //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(intent);
         try {
             Object statusBarManager =  getSystemService("statusbar");
             Method collapse;
             if (Build.VERSION.SDK_INT <= 16) {
                 collapse = statusBarManager.getClass().getMethod("collapse");
             } else {
                 collapse = statusBarManager.getClass().getMethod("collapsePanels");
             }
             collapse.invoke(statusBarManager);
         } catch (Exception localException) {
             localException.printStackTrace();
         }
     }




    //获取系统通知栏字体默认颜色
    public static int getIconColor(){
        return Color.parseColor("#999999");
    }

        @Override public void onDestroy() {
        super.onDestroy();
        L.i(tag, "onDestroy()");
      HermesEventBus.getDefault().destroy();
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
