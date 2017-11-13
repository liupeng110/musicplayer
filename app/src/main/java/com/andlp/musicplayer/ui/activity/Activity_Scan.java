package com.andlp.musicplayer.ui.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.ui.fragment.Fragment_BenDi;
import com.andlp.musicplayer.ui.fragment.Fragment_Main;
import com.andlp.musicplayer.util.L;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by Administrator on 2017/11/13.
 */

@ContentView(R.layout.fragment_main)
public class Activity_Scan extends SwipeBackActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        x.view().inject(this);//没用base,单独注入
        super.onCreate(savedInstanceState);
//        HermesEventBus.getDefault().register(this);
//        getSwipeBackLayout().setEnableGesture(false);//设置最下层不可滑动
        getSwipeBackLayout().setShadow(R.mipmap.kong0, SwipeBackLayout.EDGE_LEFT);
        getSwipeBackLayout().setParallaxOffset(0.0f );//阴影距离
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL); // EDGE_LEFT(默认),EDGE_ALL

//        if (findFragment(Fragment_BenDi.class) == null) {
//            loadRootFragment(R.id.cc, Fragment_Main.newInstance());  // 加载根Fragment
//        }



    }










}