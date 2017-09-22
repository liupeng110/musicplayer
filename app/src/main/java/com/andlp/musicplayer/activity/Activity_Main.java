package com.andlp.musicplayer.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.util.L;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.qihoo360.replugin.utils.FileUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@ContentView(R.layout.activity_main)
public class Activity_Main extends Activity_Base {

    @ViewInject(R.id.txt) private TextView txt;
    @ViewInject(R.id.btn1) private Button btn1;
    @ViewInject(R.id.btn2) private Button btn2;
    @ViewInject(R.id.btn3) private Button btn3;
    @ViewInject(R.id.btn4) private Button btn4;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Event(value = R.id.txt,type = View.OnClickListener.class)
    private void txt(View view) {
        L.i(tag,"单击  txt");

    }

    @Event(value = R.id.btn1,type = View.OnClickListener.class)
    private void btn1(View view) {
        L.i(tag,"单击  btn1---");
        String pluginName= "plugin.apk";
        String pluginPath = "external" + File.separator + pluginName;
        // 文件是否已经存在？直接删除重来
        String pluginFilePath = getFilesDir().getAbsolutePath() + File.separator + pluginName;
        File pluginFile = new File(pluginFilePath);
        if (pluginFile.exists()) {
            FileUtils.deleteQuietly(pluginFile);
        }
        copyAssetsFileToAppFiles(pluginPath, pluginName);
        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(pluginFilePath);
        }
        if (info != null) {
            RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent(info.getName(), "com.andlp.musicplayer.activity.Activity_Main"));
        } else {
            Toast.makeText(Activity_Main.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.btn2,type = View.OnClickListener.class)
    private void btn2(View view) {
        L.i(tag,"单击  btn2---通过包名");
        RePlugin.install("/mnt/sdcard/www/app-debug.apk");
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("com.yltx.appplugin", "com.yltx.appplugin.TestActivity"));
    }

    @Event(value = R.id.btn3,type = View.OnClickListener.class)
    private void btn3(View view) {
        L.i(tag,"单击  btn3");
//        RePlugin.install("/mnt/sdcard/www/app-debug.apk");
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("plugin1","com.yltx.appplugin.TestActivity"));

//        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("plugin1",
//                "com.yltx.appplugin.TestActivity"));
    }

    @Event(value = R.id.btn4,type = View.OnClickListener.class)
    private void btn4(View view) {
        L.i(tag,"单击  btn4");
        RePlugin.startActivity(Activity_Main.this, RePlugin.createIntent("com.yltx.appplugin", "com.yltx.appplugin.TestActivity"));
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

}
