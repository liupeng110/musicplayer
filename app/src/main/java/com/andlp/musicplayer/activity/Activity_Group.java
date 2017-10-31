package com.andlp.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Anim_Fragment;
import com.andlp.musicplayer.fragment.Fragment_Main;
import com.andlp.musicplayer.fragment.Fragment_New;
import com.andlp.musicplayer.service.Service_Play;
import com.andlp.musicplayer.util.DateUtil;
import com.andlp.musicplayer.util.PackageUtil;
import com.sds.android.ttpod.media.MediaTag;
import com.andlp.musicplayer.util.L;
import com.sds.android.ttpod.media.player.TTMediaPlayer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;
import xiaofei.library.hermeseventbus.HermesEventBus;


/**
 * 717219917@qq.com  2017/9/27 14:56
 */
@ContentView(R.layout.activity_group)//不处理播放 只控制ui
public class Activity_Group extends SwipeBackActivity {
//    TTMediaPlayer player;
    Fragment_Main firstFragment;
    Fragment_New fragment_new;

    @Override protected void onCreate(Bundle savedInstanceState) {
        x.view().inject(this);//没用base,单独注入
        super.onCreate(savedInstanceState);
        HermesEventBus.getDefault().register(this);
        getSwipeBackLayout().setEnableGesture(false);
        if (findFragment(Fragment_Main.class) == null) {
            loadRootFragment(R.id.cc, Fragment_Main.newInstance());  // 加载根Fragment
        }
        if (!isTaskRoot()) {//判断是否最底层
             L.i("最底层");
       }

    }


//    @Event(value = R.id.group_zuo,type = View.OnClickListener.class)
//    private void zuo(View view) {
//        player.setPosition(player.getPosition()-4500,0);
//    }
//
//    @Event(value = R.id.group_you,type = View.OnClickListener.class)
//    private void yuo(View view) {
//        player.setPosition(player.getPosition()+4500,0);
//    }



     public void img(View view){//单击开启新fragment
       start(Fragment_New.newInstance());
       startService();

//         mediaTag("/mnt/sdcard/aaa.ape");//测试mediatag
//         mediaPlay();                                    //测试播放




}

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rec_fromChiled(String str) {
        L.e("Activity_Group接收到消息event---->" + str);
       if(str.startsWith("finish")){
           str=str.substring(7,str.length());
           switch (str){
               case "finish":        break;
           }
       }
    }


    private void startService(){//启动服务  先判定是否已开启
        if (!PackageUtil.isServiceWork(this,"com.andlp.musicplayer.service.Service_Play")){
            Intent  intent = new Intent();
            intent.setClass(Activity_Group.this, Service_Play.class);
            startService(intent);
        }
    }

    private void mediaPlay(){
//        player = TTMediaPlayer.instance(SingnatureUtil.mda_byte(this), "/data/data/"+ "com.andlp.musicplayer" + "/lib");
//        player.setOnMediaPlayerNotifyEventListener(this.notifyEventListener);
//        player.setDataSource("/mnt/sdcard/aaa.ape",0);
//        player.play();
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

    @Override public void onBackPressedSupport() {
        L.i("返回键"+getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1){ //模拟home
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(startMain);
        }else {super.onBackPressedSupport();}
    }

    @Override public FragmentAnimator onCreateFragmentAnimator() {
        return new Anim_Fragment();// 设置横向(和安卓4.x动画相同)
    }
    @Override public boolean swipeBackPriority() {
         return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }
}
