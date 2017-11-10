package com.andlp.musicplayer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andlp.musicplayer.R;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * 717219917@qq.com  2017/9/28 15:50
 */
@ContentView(R.layout.activity_player)
public class Activity_Player extends Activity_Base{
     @ViewInject(R.id.player) Button button;
     @ViewInject(R.id.pause)  Button pause;
     @ViewInject(R.id.editText)  EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button.setText("player");


    }






    @Event(value = R.id.player,type = View.OnClickListener.class)
    private void player(View view){
           String path= editText.getText().toString();
           play(path);
//        EventBus.getDefault().post("finish_welcome");
    }


    @Event(value = R.id.pause,type = View.OnClickListener.class)
    private void pause(View view){

    }

  private void play(String path){
  }







private void test(){
    RequestParams params = new RequestParams("");
//    x.http().get(params);
}



}
