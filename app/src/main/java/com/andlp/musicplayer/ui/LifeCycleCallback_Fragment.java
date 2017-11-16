package com.andlp.musicplayer.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.andlp.musicplayer.util.L;

/**
 * 717219917@qq.com  2017/11/16  16:19
 */
public class LifeCycleCallback_Fragment extends FragmentManager.FragmentLifecycleCallbacks {

    public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
        L.i("app","进入fragment+++++++++++++++++++++");
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentPreAttached();");
    }
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentAttached();");
    }
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentCreated();");
    }
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f,Bundle savedInstanceState) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentActivityCreated();");
    }
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentViewCreated();");
    }
    public void onFragmentStarted(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentStarted();");
    }
    public void onFragmentResumed(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentResumed();");
    }
    public void onFragmentPaused(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentPaused();");
    }
    public void onFragmentStopped(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentStopped();");
    }
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentSaveInstanceState ();");
    }
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentViewDestroyed();");
    }
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentDestroyed();");
    }
    public void onFragmentDetached(FragmentManager fm, Fragment f) { 
        L.i("app","进入---------- "+f.getClass().getSimpleName()+".onFragmentDetached();");
        L.i("app","进入---------- Fragment----------------------------------");
    }
}
