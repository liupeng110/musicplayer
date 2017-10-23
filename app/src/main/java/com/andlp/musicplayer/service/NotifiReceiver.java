package com.andlp.musicplayer.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;


/**接收并分发通知栏按钮点击事件*/
public class NotifiReceiver extends WakefulBroadcastReceiver {
    @Override  public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

    }


    private static void startService(Context context, String command) {
        final Intent i = new Intent(context, PlayService.class);
        i.setAction("");
        i.putExtra("", command);
        i.putExtra("", true);
        startWakefulService(context, i);
    }

}
