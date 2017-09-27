package com.andlp.musicplayer.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


import com.andlp.musicplayer.R;
import com.andlp.musicplayer.util.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 717219917@qq.com  2017/9/27 14:56
 */
@ContentView(R.layout.activity_group)
public class Activity_Group extends ActivityGroup{
   @ViewInject(R.id.cc) LinearLayout content ;
   @ViewInject(R.id.bottom) Button bottom ;
    View myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        x.view().inject(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }




    @Event(value = R.id.bottom,type = View.OnClickListener.class)
    private void bottom(View view){
        content.removeAllViews();
        myview=getLocalActivityManager().startActivity(
                "Module1",
                new Intent(Activity_Group.this,Activity_Welcome.class)//FLAG_ACTIVITY_CLEAR_TOP
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ))//FLAG_ACTIVITY_BROUGHT_TO_FRONT
                .getDecorView();
        myview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        content.addView(myview);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rec_chiled(String str) {
       if(str.startsWith("finish")){
           str=str.substring(7,str.length());
           switch (str){
               case "welcome":content.removeView(myview);myview=null;
           }
           L.i("group接收到消息--->"+str);

       }


    }



    @Override public void onBackPressed() {
        L.i("back ");
        content.removeView(myview);
        myview=null;
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
