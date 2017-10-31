package com.andlp.musicplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.andlp.musicplayer.util.L;

import xiaofei.library.hermeseventbus.HermesEventBus;


/**接收并分发通知栏按钮点击事件*/
public class Receiver_Notifi extends BroadcastReceiver {//WakefulBroadcastReceiver
    @Override  public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        L.i("Receiver收到广播："+action);
          switch (action){
              case "com.andlp.action.play":     HermesEventBus.getDefault().post("play");
              case "com.andlp.action.left":      HermesEventBus.getDefault().post("left");
              case "com.andlp.action.right":    HermesEventBus.getDefault().post("right");
              case "com.andlp.action.exit":     HermesEventBus.getDefault().post("exit");
              case "com.andlp.action.other":  HermesEventBus.getDefault().post("other");
          }
    }

}
