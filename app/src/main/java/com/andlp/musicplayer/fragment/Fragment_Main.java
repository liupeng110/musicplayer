package com.andlp.musicplayer.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Anim_Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 717219917@qq.com  2017/9/25 15:10
 */
@ContentView(R.layout.fragment_main)
public class Fragment_Main extends SwipeBackFragment {
    private Toolbar mToolbar;
    public static Fragment_Main newInstance() {
        Bundle args = new Bundle();
        Fragment_Main fragment = new Fragment_Main();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
//        return  attachToSwipeBack(x.view().inject(this, inflater, container)) ;
        return  x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
           getSwipeBackLayout().setEnableGesture(false);
//        getSwipeBackLayout().setShadow(R.drawable.kong0,SwipeBackLayout.EDGE_ALL);
//        getSwipeBackLayout().setParallaxOffset(0.0f );
//        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
    }

//        @Override
//    public FragmentAnimator onCreateFragmentAnimator() {
//        // 设置横向(和安卓4.x动画相同)
//        return new Anim_Fragment();
//    }


}
