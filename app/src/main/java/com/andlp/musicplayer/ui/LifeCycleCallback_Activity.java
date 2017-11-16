package com.andlp.musicplayer.ui;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.andlp.musicplayer.MyApp;
import com.andlp.musicplayer.util.L;

import org.xutils.x;

/**
 * 717219917@qq.com  2017/11/16  16:19
 */
public class LifeCycleCallback_Activity implements Application.ActivityLifecycleCallbacks{

    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        L.i("app","进入activity*****************************");
        L.i("app","进入"+activity.getClass().getSimpleName()+".onCreated();");
        x.view().inject(activity);
        MyApp.list.add(activity);
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

    }
    @Override public void onActivityStarted(Activity activity) {
        L.i("app","进入"+activity.getClass().getSimpleName()+".onActivityStarted();");
    }
    @Override public void onActivityResumed(Activity activity) {
        L.i("app","进入"+activity.getClass().getSimpleName()+".onActivityResumed();");
    }
    @Override  public void onActivityPaused(Activity activity) {
        L.i("app","进入"+activity.getClass().getSimpleName()+".onActivityPaused();");
    }
    @Override public void onActivityStopped(Activity activity) {
        L.i("app","进入"+activity.getClass().getSimpleName()+".onActivityStopped();");
    }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        L.i("app","进入"+activity.getClass().getSimpleName()+".onActivitySaveInstanceState();");
    }
    @Override public void onActivityDestroyed(Activity activity) {
        MyApp.list.remove(activity);
        L.i("app","进入"+activity.getClass().getSimpleName()+".onActivityDestroyed();");
        L.i("app","进入activity///////////////////////////////////////////////");

    }

}
