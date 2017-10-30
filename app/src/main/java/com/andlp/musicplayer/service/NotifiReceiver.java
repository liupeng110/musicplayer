package com.andlp.musicplayer.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.andlp.musicplayer.activity.Activity_Group;
import com.andlp.musicplayer.activity.Activity_Welcome;
import com.andlp.musicplayer.util.L;


/**接收并分发通知栏按钮点击事件*/
public class NotifiReceiver extends BroadcastReceiver {//WakefulBroadcastReceiver
    @Override  public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        L.i("收到广播："+action);

//        Intent intent_Main = new Intent(Intent.ACTION_MAIN);
//        intent_Main.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent_Main.setClass(context, Activity_Welcome.class);
//        intent_Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//       context.startActivity(intent_Main);

////        if (action.equals("com.andlp.musicplayer.activity.Activity_Group")){
//            Intent activityIntent = new Intent(context.getApplicationContext(), Activity_Group.class);
//           activityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//           context.getApplicationContext().startActivity(activityIntent);
////          Intent intent1 = new Intent();
////          intent1.setComponent(new ComponentName("com.andlp.musicplayer", "com.andlp.musicplayer.activity.Activity_Group.class"));
////          context.sendBroadcast(intent1);
////        }

    }


//    private static void startService(Context context, String command) {
//        final Intent i = new Intent(context, PlayService.class);
//        i.setAction("");
//        i.putExtra("", command);
//        i.putExtra("", true);
////        startWakefulService(context, i);
//    }

}
