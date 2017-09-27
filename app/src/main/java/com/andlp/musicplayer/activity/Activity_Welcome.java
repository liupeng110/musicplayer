package com.andlp.musicplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.andlp.musicplayer.R;

import org.xutils.view.annotation.ContentView;

/**
 * 717219917@qq.com  2017/9/22 11:20
 */
@ContentView(R.layout.activity_welcome)
public class Activity_Welcome extends Activity_Base{

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）

        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (height * 0.8); // 高度设置为屏幕的0.8
        p.width = (int) (0.7*width ); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
    }


}
