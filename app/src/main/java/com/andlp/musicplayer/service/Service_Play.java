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
import com.andlp.musicplayer.util.DateUtil;
import com.andlp.musicplayer.util.L;
import com.andlp.musicplayer.util.NotifUtil;
import com.andlp.musicplayer.util.SignUtil;
import com.sds.android.ttpod.media.MediaTag;
import com.sds.android.ttpod.media.player.TTMediaPlayer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/22 11:18
 */
public class Service_Play extends Service {
    TTMediaPlayer player;
    String first;

    @Override public IBinder onBind(Intent intent) {
        return null;
    }
    @Override public void onCreate() {
        super.onCreate();
        L.d("service进入onCreate()");
        HermesEventBus.getDefault().register(this);
        HermesEventBus.getDefault().post("我是service发的-onCreate()");
        if (first!=null){
            L.d("第一次播放"+first);
            play();
        }
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        L.d("service进入onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void rec_fromReceiver(String rec) {
        L.e("接收到event---->" + rec);
        switch (rec){
            case Constant.exit :   close();     break;
            case Constant.left:       left();      break;
            case Constant.play:   play();     break;
            case Constant.right:   right();      break;
            case Constant.ci:            ci();       break;
            case Constant.open :   open();    break;
            case Constant.other:   other();    break;
            default: break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true) //在ui线程执行
    public void rec_frist(String rec) {
        L.e("接收到粘连事件event---->" + rec);
        first=rec;
    }


    private void close() {
        L.i("service 进入close");
        stopForeground(false);
        Intent stopIntent = new Intent(this, Service_Play.class);
        stopService(stopIntent);
        MyApp.exit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
     private void left(){
         L.i("service 进入left");
         player.setPosition(player.getPosition()-2500,0);
     }
//     private void pause(){
////         mediaTag("/mnt/sdcard/aaa.ape");//测试mediatag
//         play() ;
//     }
     private void right(){
          L.i("service 进入right");
         player.setPosition(player.getPosition()+2500,0);
     }
     private void ci(){

     }//是否显示歌词 需要持久化保存
     private void open(){
     }
     private void other(){
    }
  @Override public void onDestroy() {
        super.onDestroy();
        L.e( "service销毁");
        HermesEventBus.getDefault().destroy();
       stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
    }

    private void play(){//playing==2，pause==3
        NotifUtil.send("弹剑记.落魄江湖十五载","于魁智","于魁智-弹剑记.落魄江湖十五载",this);
        if (player==null) {
            player = TTMediaPlayer.instance(SignUtil.mda_byte(this), getApplicationContext().getFilesDir().getParentFile().getPath() + "/lib");//getApplicationContext().getFilesDir().getParentFile().getPath()
            player.setOnMediaPlayerNotifyEventListener(this.notifyEventListener);
            player.setDataSource("/mnt/sdcard/aaa.ape", 0);
            player.play();
          return;
        }
        switch (player.getStatus()){
            case TTMediaPlayer.MEDIASTATUS_STARTING:     L.i("start当前状态");                           break;
            case TTMediaPlayer.MEDIASTATUS_PLAYING:        player.pause();                                           break;
            case TTMediaPlayer.MEDIASTATUS_PAUSED:         player.resume();                                     break;
            case TTMediaPlayer.MEDIASTATUS_STOPPED:       player.setPosition(player.getPosition()-player.getPosition(),0);player.play(); L.i("stop当前状态");      break;
            case TTMediaPlayer.MEDIASTATUS_PREPARED:     L.i("prepared当前状态");                     break;
        }

    }

    //播放监听器
    private TTMediaPlayer.OnMediaPlayerNotifyEventListener notifyEventListener = (MsgId, i2, i3, obj) -> {
        L.i("MediaPlayerProxy", "MsgId:" + MsgId);
        L.i("tag中MsgId："+MsgId+",i2:"+i2+",i3:"+i3+",obj:"+obj);
    };
    private void mediaTag(String path){
        MediaTag tag =  MediaTag.createMediaTag(path,true);
        L.i("tag:"+tag.getAlbum());
        L.i("tag:"+tag.getArtist());
        L.i("tag:"+tag.getComment());
        L.i("tag:"+tag.getTitle());
        L.i("tag:"+tag.duration());
        L.i("tag:"+tag.year());
        tag.setAlbum("测试");
        tag.setComment("这是注释");
        tag.save();
        L.i("tag----------------------------------");
        L.i("tag标题:"+tag.getTitle());
        L.i("tag声道:"+tag.channels());
        L.i("tag时间:"+tag.duration());//
        L.i("tag格式化后时间:"+ DateUtil.formatMp4Time(tag.duration()));
        L.i("tag年代:"+tag.year());
        tag.save();
        tag.close();
    }


}
