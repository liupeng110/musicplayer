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
 //
 //bottom滑动布局 暂时先不做  与播放器皮肤耦合度大
    //我的最爱 --> 我的歌单下面
    //notifUtil 更新管理通知栏布局   点击事件
    //receiver接收到  调用eventbus发送给service  service处理播放等事件

    //数据库
    //1.防止接口不能用 需要保存用户 收藏歌曲
    //2.兼容各个接口 数据类型
    //3.本地保存与云端保存(  )
    //4.歌曲名(id主键)  qq/酷狗   音质     -/-/-本地路径  本地lrc路径  本地动态歌词路径
    //页面结构需要重新设计
    //滑动冲突问题
    //就像打怪升级  解决一个怪又遇见一个怪  后面还有各种怪在等着
    //扫描页 activity---fragment/view
// L.i("路径:"+getApplicationContext().getFilesDir().getParentFile().getPath());



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
               RequestParams requestParams = new RequestParams("https://www.baidu.com/");
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
