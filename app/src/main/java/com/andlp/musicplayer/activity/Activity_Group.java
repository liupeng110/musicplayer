package com.andlp.musicplayer.activity;

import android.app.AppOpsManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Anim_Fragment;
import com.andlp.musicplayer.fragment.Fragment_Main;
import com.andlp.musicplayer.fragment.Fragment_New;
import com.andlp.musicplayer.fragment.Fragment_No;
import com.andlp.musicplayer.service.Service_Play;
import com.andlp.musicplayer.util.DateUtil;
import com.andlp.musicplayer.util.NotifUtil;
import com.andlp.musicplayer.util.PkgUtil;
import com.sds.android.ttpod.media.MediaTag;
import com.andlp.musicplayer.util.L;
import com.sds.android.ttpod.media.player.TTMediaPlayer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;
import xiaofei.library.hermeseventbus.HermesEventBus;


/**
 * 717219917@qq.com  2017/9/27 14:56
 */
@ContentView(R.layout.activity_group)//不处理播放 只控制ui
public class Activity_Group extends SwipeBackActivity {

    @ViewInject(R.id.progress) ProgressBar progress;
          Fragment_No  fragment_no ;

    @Override protected void onCreate(Bundle savedInstanceState) {
        x.view().inject(this);//没用base,单独注入
        super.onCreate(savedInstanceState);
        HermesEventBus.getDefault().register(this);
        getSwipeBackLayout().setEnableGesture(false);//设置最下层不可滑动
        if (findFragment(Fragment_Main.class) == null) {
            loadRootFragment(R.id.cc, Fragment_Main.newInstance());  // 加载根Fragment
        }

        if (!isTaskRoot()) {//判断是否最底层
             L.i("最底层");
       }

        progress.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);//LTGRAY



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


//         loadMultipleRootFragment(R.id.cc2,0,Fragment_New.newInstance());

       HermesEventBus.getDefault().postSticky("service");
       start(Fragment_New.newInstance(),0);
       startService();

//       Intent intent = new Intent(this,Activity_Main.class);
//       startActivity(intent);

         startFragment();
}

private void startFragment(){
    FragmentManager fm = getFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    fragment_no =Fragment_No.newInstance();
    transaction.replace(R.id.cc2, fragment_no);
    transaction.commit();

}



   int pros=0;
    public void next(View view){
        L.i("点击next");
        progress.setProgress(pros++);
    }
    public void play(View view){
        L.i("点击play");
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setting(View view){
        L.i("点击setting");
        progress.setProgress(pros--);
        oppo();
//        getAppDetailSettingIntent(this);
//        isNotificationEnabled(this);
    }

    private void getAppDetailSettingIntent(Context context){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if(Build.VERSION.SDK_INT <= 8){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }


    private void oppo(){
        L.i("点击 BRAND--"+ Build.BRAND);
        L.i("点击 BOOTLOADER--"+ Build.BOOTLOADER);
        L.i("点击 BOARD--"+ Build.BOARD);
        L.i("点击 DEVICE--"+Build.DEVICE);
        L.i("点击 DISPLAY--"+Build.DISPLAY);
        L.i("点击 FINGERPRINT--"+Build.FINGERPRINT);

        L.i("点击 getRadioVersion--"+Build.getRadioVersion());
        L.i("点击 HARDWARE--"+Build.HARDWARE);
        L.i("点击 HOST--"+Build.HOST);
        L.i("点击 ID--"+Build.ID);

        L.i("点击 MANUFACTURER--"+Build.MANUFACTURER);
        L.i("点击 MODEL--"+Build.MODEL);
        L.i("点击 PRODUCT--"+Build.PRODUCT);
        L.i("点击 SERIAL--"+Build.SERIAL);
        L.i("点击 TAGS--"+Build.TAGS);
        L.i("点击 TYPE--"+Build.TYPE);
        L.i("点击 USER--"+Build.USER);

        L.i("点击 TIME--"+Build.TIME);
        L.i("点击 UNKNOWN--"+Build.UNKNOWN);


        NotifUtil.checkPermissions(this);



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
        if (!PkgUtil.isServiceWork(this,"com.andlp.musicplayer.service.Service_Play")){
            Intent  intent = new Intent();
            intent.setClass(Activity_Group.this, Service_Play.class);
            startService(intent);
        }
    }

    private void mediaPlay(){
//        player = TTMediaPlayer.instance(SignUtil.mda_byte(this), "/data/data/"+ "com.andlp.musicplayer" + "/lib");
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
        }  else {
            if (findFragment(Fragment_Main.class) == null) {
                super.onBackPressedSupport();
            }else {
                getSupportFragmentManager().popBackStack();
                HermesEventBus.getDefault().post("fragment_no");
            }
        }


    }

    @Override public boolean swipeBackPriority() {
         return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }
}
