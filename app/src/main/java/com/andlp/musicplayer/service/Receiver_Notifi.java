package com.andlp.musicplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.andlp.musicplayer.config.Constant;
import com.andlp.musicplayer.util.L;

import xiaofei.library.hermeseventbus.HermesEventBus;


/**接收并分发通知栏按钮点击事件*/
public class Receiver_Notifi extends BroadcastReceiver {//WakefulBroadcastReceiver
    @Override  public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        L.i("Receiver收到广播："+action);
          switch (action){
              case  Constant.ci:          HermesEventBus.getDefault().post(Constant.ci); break;
              case  Constant.play:      HermesEventBus.getDefault().post(Constant.play); break;
              case Constant.left:        HermesEventBus.getDefault().post(Constant.left);break;
              case Constant.right:     HermesEventBus.getDefault().post(Constant.right);break;
              case Constant.exit:      HermesEventBus.getDefault().post(Constant.exit);break;
              case Constant.other:    HermesEventBus.getDefault().post(Constant.other);break;
          }
    }

}
