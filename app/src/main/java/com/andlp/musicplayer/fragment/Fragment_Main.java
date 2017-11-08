package com.andlp.musicplayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.config.Anim_Fragment;
import com.andlp.musicplayer.util.L;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 717219917@qq.com  2017/9/25 15:10
 */
@ContentView(R.layout.fragment_main)
public class Fragment_Main extends SwipeBackFragment {
    @ViewInject(R.id.fragment_main_toolbar) RelativeLayout fragment_main_toolbar;
    private Toolbar mToolbar;

    @ViewInject(R.id.fragment_main) private LinearLayout fragment_main;
    @ViewInject(R.id.viewPager) private ViewPager viewPager;
    @ViewInject(R.id.tab_app) private TextView tab_app;
    @ViewInject(R.id.tab_game) private TextView tab_game;
    @ViewInject(R.id.tab_ceshi) private TextView tab_ceshi;
    @ViewInject(R.id.line) private View line;

    private ArrayList<Fragment> fragments;
    private ArrayList<TextView> textViews;
    private int line_width;

    public static Fragment_Main newInstance() {
        Bundle args = new Bundle();
        Fragment_Main fragment = new Fragment_Main();
        fragment.setArguments(args);
        return fragment;
    }//构造

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
           viewpager();
           getSwipeBackLayout().setEnableGesture(false);
//        getSwipeBackLayout().setShadow(R.drawable.kong0,SwipeBackLayout.EDGE_ALL);
//        getSwipeBackLayout().setParallaxOffset(0.0f );
//        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
    }


    private void viewpager(){
        ViewPropertyAnimator.animate(tab_app).scaleX(1.3f).setDuration(0);

        fragments = new ArrayList<>();
        fragments.add( new Fragment_No() );//Fragment_New.newInstance()
        fragments.add( new Fragment_No() );
        fragments.add( new Fragment_No() );

        textViews = new ArrayList<>();
        textViews.add(tab_app);
        textViews.add(tab_game);
        textViews.add(tab_ceshi);

        line_width =  getActivity().getWindowManager().getDefaultDisplay().getWidth() / fragments.size();
        line.getLayoutParams().width = line_width;
        line.requestLayout();

        viewPager.setAdapter(new FragmentStatePagerAdapter( getActivity().getSupportFragmentManager()) {
            @Override public int getCount() {
                return fragments.size();
            }
            @Override  public Fragment getItem(int index) {
                return fragments.get(index);
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageSelected(int index) { changeState(index); }
            @Override public void onPageScrolled(int index, float arg1, int offset) {
                float tagerX = index * line_width + offset / fragments.size();
                ViewPropertyAnimator.animate(line).translationX(tagerX).setDuration(0);
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        int size=textViews.size();
        for (int curr=0;curr<size;curr++)
        {
            final int temp =curr;
            textViews.get(temp).setOnClickListener(v -> viewPager.setCurrentItem(temp));
        }

    }

    private void changeState(int index) {
        L.i("当前index:"+index);
        int size=textViews.size();
        for (int curr=0;curr<size;curr++) {
            if (curr==index){
                L.i("当前index:"+index+"---------------color");
                textViews.get(curr).setTextColor(getResources().getColor(R.color.green));
                ViewPropertyAnimator.animate(textViews.get(curr)).scaleX(1.3f).scaleY(1.3f).setDuration(200);
            }else {
                L.i("当前index:"+index+",-----------else");
                textViews.get(curr).setTextColor(getResources().getColor(R.color.gray_white));
                ViewPropertyAnimator.animate(textViews.get(curr)).scaleX(1.0f).scaleY(1.0f).setDuration(200);
            }
        }

    }


//        @Override
//    public FragmentAnimator onCreateFragmentAnimator() {
//        // 设置横向(和安卓4.x动画相同)
//        return new Anim_Fragment();
//    }


}
