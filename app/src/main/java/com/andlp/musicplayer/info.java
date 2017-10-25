package com.andlp.musicplayer;

import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2017/10/23.
 */

public class info {
//通知栏逻辑
//1.点击播放(底部play/list_play) 发出通知 并更新通知栏状态
//2.1    点击通知栏 按钮  发出广播  NotifiReceiver接收后  分发给service处理 播放暂停等逻辑  并发消息更新ui中信息
// 不管ui是否销毁
 //2.2   点击logo唤起activity ,未注销  直接拉起.已注销 重新打开

public void test(){

    // 1.1使用匿名内部类
    new Thread(new Runnable() {
        @Override public void run() {
            System.out.println("Hello world !");
        }
    }).start();

    // 1.2使用 lambda expression
    new Thread(() -> System.out.println("Hello world !")).start();

   // 2.1使用匿名内部类
    Runnable race1 = new Runnable() {
        @Override public void run() {
            System.out.println("Hello world !");
            System.out.println("Hello world !");
        }
    };

       x.task().post(() -> {
           try {
               RequestParams requestParams = new RequestParams();
               String str= x.http().getSync(requestParams, String.class);
           }catch (Throwable t){t.printStackTrace();}
           });

// 2.2使用 lambda expression
    Runnable race2 = () -> {
        System.out.println("Hello world !");
        System.out.println("Hello world 2!");
    };

// 直接调用 run 方法(没开新线程哦!)
    race1.run();
    race2.run();
}




}
