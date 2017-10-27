package com.andlp.musicplayer.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.andlp.musicplayer.R;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

//自定义 打开 退出 入栈 出栈fragment动画 (activity也可用)
public class Anim_Fragment extends FragmentAnimator implements Parcelable {

    public Anim_Fragment() {
        enter =  R.anim.fragment_enter;
        exit =  R.anim.fragment_exit;
        popEnter =  R.anim.fragment_pop_enter;
        popExit = R.anim.fragment_pop_exit;
    }
    protected Anim_Fragment(Parcel in) {
        super(in);
    }
    @Override  public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
    @Override public int describeContents() {
        return 0;
    }
    public static final Creator<Anim_Fragment> CREATOR = new Creator<Anim_Fragment>() {
        @Override
        public Anim_Fragment createFromParcel(Parcel in) {
            return new Anim_Fragment(in);
        }

        @Override
        public Anim_Fragment[] newArray(int size) {
            return new Anim_Fragment[size];
        }
    };}
