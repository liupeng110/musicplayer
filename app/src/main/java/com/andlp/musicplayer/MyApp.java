package com.andlp.musicplayer;

import com.andlp.musicplayer.util.CrashUtil;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;

import org.xutils.x;

/**
 * 717219917@qq.com  2017/9/22 11:06
 */

public class MyApp extends RePluginApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashUtil.getInstance().init(this);
        x.Ext.init(this);
        x.Ext.setDebug(true);

    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig c = new RePluginConfig();
        c.setUseHostClassIfNotFound(true);
        c.setVerifySign(!BuildConfig.DEBUG);
        return c;
    }
}
