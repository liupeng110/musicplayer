package com.andlp.musicplayer.activity;

import android.app.ActivityGroup;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Constant;
import com.andlp.musicplayer.service.PlayService;
import com.sds.android.ttpod.media.MediaTag;
import com.andlp.musicplayer.util.L;
import com.sds.android.ttpod.media.player.TTMediaPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.List;


/**
 * 717219917@qq.com  2017/9/27 14:56
 */
@ContentView(R.layout.activity_group)
public class Activity_Group extends ActivityGroup{
    @ViewInject(R.id.cc) LinearLayout content ;
    @ViewInject(R.id.bottom) Button bottom ;

    @ViewInject(R.id.group_zuo) Button zuo ;
    @ViewInject(R.id.group_you) Button you ;
    View myview;
    TTMediaPlayer player;
//    private PlayService.MyBinder mbinder;//service中返回


    @Override protected void onCreate(Bundle savedInstanceState) {
        x.view().inject(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }



    @Event(value = R.id.group_zuo,type = View.OnClickListener.class)
    private void zuo(View view) {
        player.setPosition(player.getPosition()-4500,0);
    }

    @Event(value = R.id.group_you,type = View.OnClickListener.class)
    private void yuo(View view) {
        player.setPosition(player.getPosition()+4500,0);
    }

    @Event(value = R.id.bottom,type = View.OnClickListener.class)//底部按钮事件监听
    private void bottom(View view){

//        content.removeAllViews();//打开子activity
//        myview=getLocalActivityManager().startActivity(
//                "Module1",
//                new Intent(Activity_Group.this,Activity_Player.class)//FLAG_ACTIVITY_CLEAR_TOP
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ))//FLAG_ACTIVITY_BROUGHT_TO_FRONT
//                .getDecorView();
//        myview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//        content.addView(myview);

//           mediaTag("/mnt/sdcard/aaa.ape");//测试mediatag
//           mediaPlay();
        startService();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rec_chiled(String str) {
       if(str.startsWith("finish")){
           str=str.substring(7,str.length());
           switch (str){
               case "welcome":content.removeView(myview);myview=null;
           }
           L.i("group接收到消息--->"+str);
       }
    }


    private void startService(){//启动服务  先判定是否已开启
        if (!isServiceWork(this,"com.andlp.musicplayer.service.PlayService")){
            Intent  intent = new Intent();
            intent.setClass(Activity_Group.this, PlayService.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Activity_Group.this.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            startService(intent);
        }

    }

    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            L.i("当前运行:"+mName);
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


    private void clearNotifi(){   //清空
         NotificationManager noti_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti_manager.cancel(Constant.SERVICE_ID);
    }


    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override public void onServiceDisconnected(ComponentName name)
        {
            Log.v("service","断开"+name.toString());
        }

        @Override public void onServiceConnected(ComponentName name, IBinder service)
        {
            // bindService成功的时候返回service的引用
            Log.v("service","链接"+name.toString());
            //参数service就是服务onBind方法的返回值，取出service就可以在活动中使用
//            mbinder = (PlayService.MyBinder)service;
        }
    } ;

    private void mediaPlay(){
        player = TTMediaPlayer.instance(mda_byte(this), "/data/data/"+ "com.andlp.musicplayer" + "/lib");
        player.setOnMediaPlayerNotifyEventListener(this.notifyEventListener);
        player.setDataSource("/mnt/sdcard/aaa.ape",0);
        player.play();

    }


    private TTMediaPlayer.OnMediaPlayerNotifyEventListener notifyEventListener = new TTMediaPlayer.OnMediaPlayerNotifyEventListener(){
        @Override public void onMediaPlayerNotify(int MsgId, int i2, int i3, Object obj) {
            L.i("MediaPlayerProxy", "MsgId:" + MsgId);
            L.i("tag中MsgId："+MsgId+",i2:"+i2+",i3:"+i3+",obj:"+obj);
        }
    };

    private static byte[] mda_byte(Context context){
            byte[] toByteArray;
            Exception e;
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.andlp.musicplayer", PackageManager.GET_SIGNATURES);
                MessageDigest instance = MessageDigest.getInstance("MD5");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (Signature toByteArray2 : packageInfo.signatures) {
                    L.i("签名信息："+toByteArray2.toCharsString());
                    byteArrayOutputStream.write(instance.digest(toByteArray2.toByteArray()));
                }
                toByteArray = byteArrayOutputStream.toByteArray();
                L.i("签名信息--size："+toByteArray.length);
                for (byte b:toByteArray){
                    L.i("签名信息--："+b);
                }

                try {
                    byteArrayOutputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return toByteArray;
                }
            } catch (Exception e3) {
                Exception exception = e3;
                toByteArray = null;
                e = exception;
                e.printStackTrace();
                return toByteArray;
            }
            return toByteArray;

    }

    private void mediaTag(String path){
        MediaTag tag =  MediaTag.createMediaTag(path,true);
        L.i("tag:"+tag.getAlbum());
        L.i("tag:"+tag.getArtist());
        L.i("tag:"+tag.getComment());
        L.i("tag:"+tag.getGenre());
        L.i("tag:"+tag.getTitle());
        L.i("tag:"+tag.bitRate());
        L.i("tag:"+tag.channels());
        L.i("tag:"+tag.duration());
        L.i("tag:"+tag.track());
        L.i("tag:"+tag.year());
        L.i("tag:"+tag.sampleRate());
        L.i("tag:"+tag.cover());
        tag.setAlbum("测试");
        tag.setComment("这是注释");
        tag.save();
        L.i("tag----------------------------------");
        L.i("tag:"+tag.getAlbum());
        L.i("tag:"+tag.getArtist());
        L.i("tag:"+tag.getComment());
        L.i("tag:"+tag.getGenre());
        L.i("tag标题:"+tag.getTitle());
        L.i("tag:"+tag.bitRate());
        L.i("tag声道:"+tag.channels());
        L.i("tag时间:"+tag.duration());//
        L.i("tag格式化后时间:"+formatTime(tag.duration()));
        L.i("tag:"+tag.track());
        L.i("tag年代:"+tag.year());
        L.i("tag:"+tag.sampleRate());
        L.i("tag:"+tag.cover());
        tag.save();
        tag.close();
    }

    public String formatTime(long time)
    {
        time = time/ 1000;
        String strHour = "" + (time/3600);
        String strMinute = "" + time%3600/60;
        String strSecond = "" + time%3600%60;

        strHour = strHour.length() < 2? "0" + strHour: strHour;
        strMinute = strMinute.length() < 2? "0" + strMinute: strMinute;
        strSecond = strSecond.length() < 2? "0" + strSecond: strSecond;

        String strRsult = "";

        if (!strHour.equals("00"))
        {
            strRsult += strHour + ":";
        }

        if (!strMinute.equals("00"))
        {
            strRsult += strMinute + ":";
        }

        strRsult += strSecond;

        return strRsult;
    }

    @Override public void onBackPressed() {
        L.i("group---back");
        if (myview==null){
            super.onBackPressed();
        }else {
            content.removeView(myview);
            myview = null;
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        L.i("group","mServiceConnection:"+mServiceConnection);
        L.i("group","player:"+player);
        try{ unbindService(mServiceConnection);mServiceConnection=null; }catch (Throwable t){t.printStackTrace();}
//        try{ if(player!=null){ player.pause(); }}catch (Throwable t){t.printStackTrace();}//播放--后台服务

    }
}
