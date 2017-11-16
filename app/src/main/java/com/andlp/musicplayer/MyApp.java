package com.andlp.musicplayer;

import android.app.Activity;

import com.andlp.musicplayer.ui.LifeCycleCallback_Activity;
import com.andlp.musicplayer.util.CrashUtil;
import com.andlp.musicplayer.util.L;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.Fragmentation;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/22 11:06
 */
public class MyApp extends RePluginApplication {
   public static List<Activity>   list = new ArrayList<>();

    @Override public void onCreate() {
        super.onCreate();
        CrashUtil.getInstance().init(this);               //崩溃处理
        x.Ext.init(this);                                                  //xutils3初始化
        HermesEventBus.getDefault().init(this);    //跨进程eventbus
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).debug(BuildConfig.DEBUG).install();// NONE：隐藏
        this.registerActivityLifecycleCallbacks(new LifeCycleCallback_Activity());//统一处理activity
    }

    @Override protected RePluginConfig createConfig() {
        RePluginConfig config = new RePluginConfig();
        config.setUseHostClassIfNotFound(true);
        config.setVerifySign(!BuildConfig.DEBUG);
        return config;
    }
    public static void  exit(){
        for(Activity activity:list){
            activity.finish();
        }
//       android.os.Process.killProcess(android.os.Process.myPid());
//       System.exit(0);
    }

}
