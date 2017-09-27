package com.andlp.musicplayer.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


import com.andlp.musicplayer.R;
import com.andlp.musicplayer.util.L;


/**
 * 717219917@qq.com  2017/9/27 14:56
 */

public class Activity_Group extends ActivityGroup{
    LinearLayout content ;
    View myview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_group);
           content =(LinearLayout)findViewById(R.id.cc);


    }





    public void bottom(View view){
        content.removeAllViews();
        myview=getLocalActivityManager().startActivity(
                "Module1",
                new Intent(Activity_Group.this,Activity_Welcome.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
        myview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        content.addView(myview);
    }


    @Override public void onBackPressed() {
        L.i("back ");
        content.removeView(myview);


//        super.onBackPressed();
    }



}
