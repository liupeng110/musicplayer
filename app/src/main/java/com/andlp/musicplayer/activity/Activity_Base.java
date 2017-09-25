package com.andlp.musicplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.andlp.musicplayer.util.L;

import org.xutils.x;

/**
 * 717219917@qq.com  2017/9/22 15:24
 */

public class Activity_Base extends Activity {
    public String tag =this.getClass().getSimpleName();
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(tag,"注入base的onCreate()");
        x.view().inject(this);
    }
}
