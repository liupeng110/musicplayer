package com.andlp.musicplayer.util;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.andlp.musicplayer.BuildConfig;
import com.andlp.musicplayer.R;
import com.andlp.musicplayer.activity.Activity_Group;
import com.andlp.musicplayer.config.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.os.Build.BRAND;

//处理notification布局 创建等问题  解耦和playService
public class NotifUtil {
  static  String title,content;
  static NotificationCompat.Builder builder;
  static  Notification notification;
    static boolean ci_press=false,play_press=true;


    public static void sendNotification(String title_temp, String singer_temp , Service service){//自定义通知
        L.e( "进入norifi_my---->" + title_temp+",content_temp:"+singer_temp+",ticker:"+singer_temp+"-"+title_temp);
        title=title_temp;content=singer_temp;
        builder = new NotificationCompat.Builder(service);
        builder.setOngoing(true);
        builder.setTicker(singer_temp+"-"+title_temp);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setShowWhen(false);
        notify_small(service);//初始化小通知栏
         notify_big(service);   //初始化大通知栏
        notification = builder.build();
        service.startForeground(Constant.service_ID, notification);// 服务 id,取消时用
    }
    private static RemoteViews notify_small(Service service){
        RemoteViews remoteViews = new RemoteViews(service.getPackageName(), R.layout.service_notifi_small);

        remoteViews.setTextViewText(R.id.title_tv, title);
        remoteViews.setTextViewText(R.id.content_tv, content);
        remoteViews.setImageViewResource(R.id.icon_iv, R.mipmap.logo);
        remoteViews.setInt(R.id.notifi_close, "setColorFilter", Color.parseColor("#999999"));

        Intent intent_left = new Intent(Constant.left);//监听 上一曲
        PendingIntent pendingIntent_left = PendingIntent.getBroadcast(service, 1, intent_left, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_left, pendingIntent_left);


        Intent intent_pause = new Intent(Constant.play);//监听 暂停
        PendingIntent pendingIntent_pause = PendingIntent.getBroadcast(service, 2, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_pause, pendingIntent_pause);

        Intent intent_right = new Intent(Constant.right);//监听 下一曲
        PendingIntent pendingIntent_right = PendingIntent.getBroadcast(service, 3, intent_right, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_right, pendingIntent_right);


        Intent intent1 = new Intent(Constant.exit);//监听关闭
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(service, 4, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notifi_close, pendingIntent1);


        Intent intent_Main = new Intent(Intent.ACTION_MAIN);//监听logo事件
        intent_Main.addCategory(Intent.CATEGORY_LAUNCHER);
        intent_Main.setClass(service, Activity_Group.class);
        intent_Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent_open = PendingIntent.getActivity(service, 5, intent_Main, PendingIntent.FLAG_UPDATE_CURRENT);
//    PendingIntent pendingIntent_open = PendingIntent.getBroadcast(this, 4, intent_open, PendingIntent.FLAG_UPDATE_CURRENT);//
        remoteViews.setOnClickPendingIntent(R.id.icon_iv, pendingIntent_open);


        boolean isAboveLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        builder.setSmallIcon(isAboveLollipop ? R.drawable.img_playing_hint : R.drawable.notifi_small_color);//notifi_small_21

        builder.setContent(remoteViews);//通知栏小布局
        return remoteViews;
    }
    private static RemoteViews  notify_big(Service service){
        RemoteViews remoteViews2 = new RemoteViews(service.getPackageName(), R.layout.service_notifi_big);
        remoteViews2.setTextViewText(R.id.title_tv, title);
        remoteViews2.setTextViewText(R.id.content_tv, content);
        remoteViews2.setImageViewResource(R.id.icon_iv_big, R.mipmap.logo);
        remoteViews2.setInt(R.id.notifi_close_big, "setColorFilter", Color.parseColor("#999999"));

        try{  builder.setLargeIcon(BitmapFactory.decodeResource(service.getResources(),R.mipmap.logo));}catch (Throwable t){t.printStackTrace(); return null;}
      try{ builder.setCustomBigContentView(remoteViews2);}catch (Throwable t){t.printStackTrace();return  null;}//通知栏大布局


        Intent intent_left = new Intent(Constant.left);//监听 上一曲
        PendingIntent pendingIntent_left = PendingIntent.getBroadcast(service, 6, intent_left, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_left_big, pendingIntent_left);//一个监听


        Intent intent_pause = new Intent(Constant.play);//监听 暂停
        PendingIntent pendingIntent_pause = PendingIntent.getBroadcast(service, 7, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_pause_big, pendingIntent_pause);//一个监听

        Intent intent_right = new Intent(Constant.right);//监听 下一曲
        PendingIntent pendingIntent_right = PendingIntent.getBroadcast(service, 8, intent_right, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_right_big, pendingIntent_right);//一个监听


        Intent intent_close = new Intent(Constant.exit);//监听关闭
        PendingIntent pendingIntent_close = PendingIntent.getBroadcast(service, 9, intent_close, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_close_big, pendingIntent_close);                  //又一个监听

        Intent intent_ci = new Intent(Constant.ci);//监听关闭
        PendingIntent pendingIntent_ci = PendingIntent.getBroadcast(service, 10, intent_ci, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.notifi_ci_big, pendingIntent_ci);                                //又一个监听

        Intent intent_Main = new Intent(Intent.ACTION_MAIN);//监听logo事件
        intent_Main.addCategory(Intent.CATEGORY_LAUNCHER);
        intent_Main.setClass(service, Activity_Group.class);
        intent_Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent_open = PendingIntent.getActivity(service, 11, intent_Main, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews2.setOnClickPendingIntent(R.id.icon_iv_big, pendingIntent_open);

        return remoteViews2;
    }





  @SuppressLint("RestrictedApi")
  private static void  ci(Service service){
    if (builder.getBigContentView()!=null){
        if (ci_press){
            try{builder.getBigContentView().setImageViewResource(R.id.notifi_ci_big,R.mipmap.notifi_ci);}catch (Throwable t){t.printStackTrace();}
        }else {
            try{builder.getBigContentView().setImageViewResource(R.id.notifi_ci_big,R.mipmap.notifi_ci_p);}catch (Throwable t){t.printStackTrace();}
        }
        ci_press=!ci_press;
        notification = builder.build();
        service.startForeground(Constant.service_ID, notification);
    }
  }


  @SuppressLint("RestrictedApi")
  private static void play(Service service){
      L.i("pause中builder.big是否为null--:"+builder.getBigContentView());
      if (builder.getBigContentView()!=null){
          if (play_press) {
              try{ builder.getBigContentView().setImageViewResource(R.id.notifi_pause_big,R.mipmap.notifi_play);}catch (Throwable t){t.printStackTrace();}
          }else {
              try{builder.getBigContentView().setImageViewResource(R.id.notifi_pause_big,R.mipmap.notifi_pause);}catch (Throwable t){t.printStackTrace();}
          }
      }

         RemoteViews remoteViews =notify_small(service); //重新new因为从builder获取为空
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
      service.startForeground(Constant.service_ID, notification);//更新通知栏ui
    }

    //通知栏权限处理
    @SuppressLint("NewApi")
    public static boolean checkPermissions(Context context) {
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
    /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            if ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED){

            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否跳转到设置页面?");
                builder.setTitle("通知栏权限未开启!");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        switch_Type(context);//根据机型 分发通知栏权限处理
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }//1.检查是否有通知栏权限
    private static    void   switch_Type(Context context){ //跳转到通知栏开关
        if (BRAND.equals("OPPO")){
            goTo_OPPO(context);
        }else {
            goToOther(context);
        }
    }          //2.适配机型 跳转到具体权限页面
    private static void goTo_OPPO(Context context){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("pkg_name", context.getPackageName());
        intent.putExtra("app_name",context.getString(R.string.notifi_oppo) );
        intent.putExtra("class_name", "com.andlp.musicplayer.activity.Activity_Main");
        ComponentName comp = new ComponentName("com.coloros.notificationmanager", "com.coloros.notificationmanager.AppDetailPreferenceActivity");
        intent.setComponent(comp);
        context.startActivity(intent);
    }               //3.适配oppo color-3.0
    private static void goToOther(Context context){

            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
            return;
    }                 //4. 进入设置系统应用权限界面
   //权限处理 结束


}
