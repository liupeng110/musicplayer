package com.andlp.musicplayer.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Anim_Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 717219917@qq.com  2017/9/25 15:10
 */
@ContentView(R.layout.fragment_main)
public class Fragment_Main extends SwipeBackFragment {
    private Toolbar mToolbar;
    @ViewInject(R.id.main_bendi) private Button main_bendi;

    public static Fragment_Main newInstance() {
        Bundle args = new Bundle();
        Fragment_Main fragment = new Fragment_Main();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean injected = false;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return  attachToSwipeBack(x.view().inject(this, inflater, container)) ;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        getSwipeBackLayout().setEnableGesture(false);//设置不可滑动
//        getSwipeBackLayout().setShadow(R.mipmap.kong0,SwipeBackLayout.EDGE_LEFT);
//        getSwipeBackLayout().setParallaxOffset(0.0f );//阴影距离
//        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL); // EDGE_LEFT(默认),EDGE_ALL
    }

    @Event(value = R.id.main_bendi ,type=View.OnClickListener.class)
    private void main_bendi(View view) {
        start(Fragment_BenDi.newInstance());
    }


}
