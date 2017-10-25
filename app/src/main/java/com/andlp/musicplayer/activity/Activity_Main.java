package com.andlp.musicplayer.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.fragment.Fragment_Local;
import com.andlp.musicplayer.util.L;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.qihoo360.replugin.RePlugin;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.FileOutputStream;
import java.io.InputStream;

@ContentView(R.layout.activity_main)
public class Activity_Main extends Activity_Base {

    @ViewInject(R.id.txt) private TextView txt;
    @ViewInject(R.id.btn1) private Button btn1;
    @ViewInject(R.id.btn2) private Button btn2;
    @ViewInject(R.id.btn3) private Button btn3;
    @ViewInject(R.id.btn4) private Button btn4;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);initView();

    }


    private void initView(){

        initData();
    }

    private void initData(){
//        initSlidingMenu();//导致状态栏呈深色不透明
    }

    private void initSlidingMenu(){
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
//        menu.setMenu(R.layout.menu_sliding);
    }

    @Event(value = R.id.txt,type = View.OnClickListener.class)
    private void txt(View view) {
        L.i(tag,"单击  txt");

    }

    @Event(value = R.id.btn1,type = View.OnClickListener.class)
    private void btn1(View view) {
        L.i(tag,"单击  btn1---");
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("plugin", "com.andlp.them.MainActivity"));

    }

    @Event(value = R.id.btn2,type = View.OnClickListener.class)
    private void btn2(View view) {
        L.i(tag,"单击  btn2---通过包名");
        startActivity(new Intent(Activity_Main.this,Activity_Group.class));
      }

    @Event(value = R.id.btn3,type = View.OnClickListener.class)
    private void btn3(View view) {
        L.i(tag,"单击  btn3");
         }

    @Event(value = R.id.btn4,type = View.OnClickListener.class)
    private void btn4(View view) {
        L.i(tag,"单击  btn4");
        addFragment();
      }


    /**
     * 从assets目录中复制某文件内容
     *  @param  assetFileName assets目录下的Apk源文件路径
     *  @param  newFileName 复制到/data/data/package_name/files/目录下文件名
     */
    private void copyAssetsFileToAppFiles(String assetFileName, String newFileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {
            is = this.getAssets().open(assetFileName);
            fos = this.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void plugin(){
        //1.外部安装并打开  info!=null,suc
        RePlugin.install("/mnt/sdcard/www/app-debug.apk");
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("com.andlp.them", "com.andlp.them.MainActivity"));

        //2.assets/plugin/plugin.jar
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("plugin","com.yltx.appplugin.TestActivity"));

        //3.install之后根据包名和activity打开
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("com.yltx.appplugin", "com.yltx.appplugin.TestActivity"));



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //    super.onSaveInstanceState(outState);
    }


    private void addFragment(){
//        Fragment_Local fragment_local =   Fragment_Local.newInstance();
//        FragmentTransaction tx =  getFragmentManager().beginTransaction();
//        tx.add(R.id.content, fragment_local);//将当前的事务添加到了回退栈
//        tx.addToBackStack(null);
//        tx.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);//自定义动画效果
//        tx.commit();//tx.commitAllowingStateLoss();
    }

    private void addFrameLayout(){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //设置顶部,左边布局
        params.gravity= Gravity.TOP|Gravity.LEFT;
        TextView top=new TextView(this);
        //控件字体位置位于左边
        top.setGravity(Gravity.LEFT);
        top.setText("顶部");
        //添加控件
        addContentView(top, params);

        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //设置中间位置
        params2.gravity=Gravity.CENTER;
        params2.width=720;
        params2.height=1080;
        TextView center=new TextView(this);
        //字体位于中部
        center.setGravity(Gravity.CENTER);
        center.setText("中部");
        //添加控件
        addContentView(center, params2);


        initData();

        FrameLayout.LayoutParams params3 = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //设置底部
        params3.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        Button bottom=new Button(this);
        //字体位于中部
        bottom.setGravity(Gravity.RIGHT);
        bottom.setText("底部");
        //添加控件
        addContentView(bottom, params3);


    } //测试动态添加代码 未使用



    @Override public void onBackPressed() {
        getFragmentManager().executePendingTransactions();
        L.i("back "+getFragmentManager().getBackStackEntryCount());

        if (getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }

    }


}
