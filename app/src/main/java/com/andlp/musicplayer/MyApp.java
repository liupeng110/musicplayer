package com.andlp.musicplayer;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.andlp.musicplayer.util.CrashUtil;
import com.andlp.musicplayer.util.L;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 717219917@qq.com  2017/9/22 11:06
 */

public class MyApp extends RePluginApplication {
    static List<Activity>   list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtil.getInstance().init(this);
        x.Ext.init(this);
        x.Ext.setDebug(true);
        registerActivityLifeCallback();
    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig config = new RePluginConfig();
        config.setUseHostClassIfNotFound(true);
        config.setVerifySign(!BuildConfig.DEBUG);
        return config;
    }

    private void registerActivityLifeCallback() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                list.add(activity);
                L.i("app","activity:"+activity.getClass().getSimpleName());
            }
            @Override public void onActivityStarted(Activity activity) {  }
            @Override public void onActivityResumed(Activity activity) {  }
            @Override public void onActivityPaused(Activity activity) { }
            @Override public void onActivityStopped(Activity activity) { }
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {  }
            @Override public void onActivityDestroyed(Activity activity) { list.remove(activity);}
        });
    }

    public static void  exit(){
        for(Activity activity:list){
            activity.finish();
        }
//        android.os.Process.killProcess(android.os.Process.myPid());
//       System.exit(0);
    }



}
