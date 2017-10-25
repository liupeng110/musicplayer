package com.andlp.musicplayer;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
    static List<Activity>   list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtil.getInstance().init(this);
        x.Ext.init(this);
        x.Ext.setDebug(true);
        registerActivityLifeCallback();
        HermesEventBus.getDefault().init(this);
        Fragmentation.builder() .debug(BuildConfig.DEBUG) .install();//滑动框架初始化
    }





    @Override protected RePluginConfig createConfig() {
        RePluginConfig config = new RePluginConfig();
        config.setUseHostClassIfNotFound(true);
        config.setVerifySign(!BuildConfig.DEBUG);
        return config;
    }
    private void registerActivityLifeCallback() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                x.view().inject(activity);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0沉浸状态栏
                    Window window = activity.getWindow();//透明状态栏
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.TRANSPARENT);//设置状态栏的颜色
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                    activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                }
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
