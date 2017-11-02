package com.andlp.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.util.L;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/22 11:20
 */
@ContentView(R.layout.activity_welcome)
public class Activity_Welcome extends Activity_Base{

    @ViewInject(R.id.welcome) Button button;
    @ViewInject(R.id.webview) WebView webview;
    private static final String LOGIN_URL = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=384&pt_no_auth=1&style=11&appid=1006102&s_url=https%3A%2F%2Fy.qq.com%2Fportal%2Fprofile.html";

    @SuppressLint("SetJavaScriptEnabled")
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        x.task().postDelayed(() -> {
//            HermesEventBus.getDefault().post("finish_welcome0");
//            Intent intent = new Intent(this,Activity_Group.class);
//            startActivity(intent);
//            finish();
//        },3000);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyWebViewClient(){});
        webview.loadUrl(LOGIN_URL);

    }

    @Event(value = R.id.welcome,type = View.OnClickListener.class)
    private void button(View view){
        HermesEventBus.getDefault().post("finish_welcome1");
         Intent intent = new Intent(this,Activity_Group.class);
        startActivity(intent);
        finish();
    }

int a=0;
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webview.loadUrl(url);
            x.task().postDelayed(() -> {
            HermesEventBus.getDefault().post("这是webview发出的0");
        },3000);

            return true;
        }

        public void onPageFinished(WebView view, String url) {
            CookieManager cookieManager = CookieManager.getInstance();
            String CookieStr = cookieManager.getCookie(url);
            L.i(a+"网页中Cookies = " + CookieStr);
            a++;
            webview.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }



}
