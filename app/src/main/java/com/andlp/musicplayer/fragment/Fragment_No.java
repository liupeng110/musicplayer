package com.andlp.musicplayer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.util.L;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/25 15:10
 */
@ContentView(R.layout.fragment_new)
public class Fragment_No extends Fragment {

    private Toolbar mToolbar;

    public static Fragment_No newInstance() {
        Bundle args = new Bundle();
        Fragment_No fragment = new Fragment_No();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean injected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HermesEventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rec_fromActivity(String str) {
        L.e("Activity_Group接收到消息event---->" + str);
        if(str.equals("fragment_no")){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return  x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
//        getSwipeBackLayout().setShadow(R.mipmap.kong0,SwipeBackLayout.EDGE_LEFT);
//        getSwipeBackLayout().setParallaxOffset(0.0f );//阴影距离
//        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL); // EDGE_LEFT(默认),EDGE_ALL
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }
}
