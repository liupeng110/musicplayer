package com.andlp.musicplayer.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

/**
 * 717219917@qq.com  2017/9/27 15:52
 */

public class MyLayout extends LinearLayout{
   Activity mycontext;

    public MyLayout(Context context) {
        super(context);
        mycontext=(Activity) context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics metric = new DisplayMetrics();
        mycontext.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）

//        int width = measureWidth(widthMeasureSpec);
//        int height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);

    }




}
