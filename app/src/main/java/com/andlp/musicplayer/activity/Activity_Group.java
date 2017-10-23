package com.andlp.musicplayer.activity;

import android.app.ActivityGroup;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Constant;
import com.andlp.musicplayer.service.PlayService;
import com.andlp.musicplayer.util.DateUtil;
import com.andlp.musicplayer.util.PackageUtil;
import com.andlp.musicplayer.util.SingnatureUtil;
import com.sds.android.ttpod.media.MediaTag;
import com.andlp.musicplayer.util.L;
import com.sds.android.ttpod.media.player.TTMediaPlayer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import xiaofei.library.hermeseventbus.HermesEventBus;


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
        x.view().inject(this);//没用base,单独注入
        super.onCreate(savedInstanceState);
        HermesEventBus.getDefault().register(this);

        if (!isTaskRoot()) {//判断是否最底层
             L.i("最底层");
       }
    }

    @Override public boolean onKeyDown( int keyCode, KeyEvent event) {
        L.i("进入onKeyDown");
        return super.onKeyDown(keyCode, event);
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
        bottom.setText("onClick");
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
        L.e("Activity_Group接收到消息event---->" + str);
       if(str.startsWith("finish")){
           str=str.substring(7,str.length());
           switch (str){
               case "welcome":content.removeView(myview);myview=null;
           }

       }
    }


    private void startService(){//启动服务  先判定是否已开启
        if (!PackageUtil.isServiceWork(this,"com.andlp.musicplayer.service.PlayService")){
            Intent  intent = new Intent();
            intent.setClass(Activity_Group.this, PlayService.class);
            startService(intent);
        }

    }


    private void clearNotifi(){   //清空
         NotificationManager noti_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti_manager.cancel(Constant.PLAYSERVICE_ID);
    }

    private void mediaPlay(){
        player = TTMediaPlayer.instance(SingnatureUtil.mda_byte(this), "/data/data/"+ "com.andlp.musicplayer" + "/lib");
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
        L.i("tag格式化后时间:"+ DateUtil.formatMp4Time(tag.duration()));//
        L.i("tag:"+tag.track());
        L.i("tag年代:"+tag.year());
        L.i("tag:"+tag.sampleRate());
        L.i("tag:"+tag.cover());
        tag.save();
        tag.close();
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
        HermesEventBus.getDefault().unregister(this);
    }
}
