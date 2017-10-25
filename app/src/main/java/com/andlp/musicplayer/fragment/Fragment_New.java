package com.andlp.musicplayer.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andlp.musicplayer.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 717219917@qq.com  2017/9/25 15:10
 */
@ContentView(R.layout.fragment_new)
public class Fragment_New extends SupportFragment {

    private Toolbar mToolbar;

    public static Fragment_New newInstance() {
        Bundle args = new Bundle();
        Fragment_New fragment = new Fragment_New();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
}
