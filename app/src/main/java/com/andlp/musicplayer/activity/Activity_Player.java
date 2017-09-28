package com.andlp.musicplayer.activity;

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
 * 717219917@qq.com  2017/9/28 15:50
 */
@ContentView(R.layout.activity_player)
public class Activity_Player extends Activity_Base{
     @ViewInject(R.id.player) Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button.setText("player");
    }


    @Event(value = R.id.player,type = View.OnClickListener.class)
    private void button(View view){
        EventBus.getDefault().post("finish_welcome");
    }



}
