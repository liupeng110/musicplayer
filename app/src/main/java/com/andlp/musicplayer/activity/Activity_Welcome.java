package com.andlp.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.andlp.musicplayer.R;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 717219917@qq.com  2017/9/22 11:20
 */
@ContentView(R.layout.activity_welcome)
public class Activity_Welcome extends Activity_Base{

    @ViewInject(R.id.welcome) Button button;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = R.id.welcome,type = View.OnClickListener.class)
    private void button(View view){
        EventBus.getDefault().post("finish_welcome");
         Intent intent = new Intent(this,Activity_Group.class);
        startActivity(intent);
        finish();
    }

}
