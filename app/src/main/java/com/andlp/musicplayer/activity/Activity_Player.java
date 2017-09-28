package com.andlp.musicplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.util.L;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 717219917@qq.com  2017/9/28 15:50
 */
@ContentView(R.layout.activity_player)
public class Activity_Player extends Activity_Base{
     @ViewInject(R.id.player) Button button;
     @ViewInject(R.id.pause)  Button pause;
     @ViewInject(R.id.editText)  EditText editText;
      IjkMediaPlayer  myplayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button.setText("player");

        initIjk();

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
//      myplayer.prepareAsync();
       try { myplayer.setDataSource(path); } catch (IOException e) { e.printStackTrace(); }
           myplayer.start();
  }


    private void initIjk(){
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Throwable e) {
            e.printStackTrace();
            L.i("Player", "加载失败loadLibraries error");
        }

        myplayer = new IjkMediaPlayer();
        enableHardwareDecoding();
    }


    private void enableHardwareDecoding(){
        if (myplayer instanceof IjkMediaPlayer){
            IjkMediaPlayer player = (IjkMediaPlayer) myplayer;
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 60);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 0);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
            player.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", 1);
        }
        myplayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                myplayer.start();
            }
        });



    }



}
