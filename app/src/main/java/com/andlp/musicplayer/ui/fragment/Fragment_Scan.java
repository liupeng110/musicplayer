package com.andlp.musicplayer.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andlp.musicplayer.R;
import com.andlp.musicplayer.ui.activity.Activity_Scan;
import com.andlp.musicplayer.util.DateUtil;
import com.andlp.musicplayer.util.L;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 717219917@qq.com  2017/9/25 15:10
 */
@ContentView(R.layout.fragment_scan)
public class Fragment_Scan extends Fragment {
     @ViewInject(R.id.scan_scan) private Button button;
    private Toolbar mToolbar;



    String[] muiscInfoArray = new String[]{                 //数组
            MediaStore.Audio.Media.TITLE,                      //name
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,            //time
            MediaStore.Audio.Media.DATA};                  //path

    public static Fragment_Scan newInstance() {
        Bundle args = new Bundle();
        Fragment_Scan fragment = new Fragment_Scan();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean injected = false;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HermesEventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rec_fromActivity(String str) {
        L.e("fragment|_scan接收到消息event---->" + str);
        if(str.equals("fragment_scan")){

        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return  x.view().inject(this, inflater, container);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
//        getSwipeBackLayout().setShadow(R.mipmap.kong0,SwipeBackLayout.EDGE_LEFT);
//        getSwipeBackLayout().setParallaxOffset(0.0f );//阴影距离
//        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL); // EDGE_LEFT(默认),EDGE_ALL
    }


    @Event(value = R.id.scan_scan ,type=View.OnClickListener.class)
    private void scan(View view) {
        Intent intent = new Intent(getActivity(), Activity_Scan.class);
       startActivity(intent);
//        startScan();
    }


   boolean scanning =true;
    public void startScan() {
        L.i("scan中 onClick"  );
        x.task().post(() -> {
            try {
                Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        muiscInfoArray, null, null, null);

                if (cursor!= null && cursor.getCount() != 0){
                    L.i("scan中数量: cursor.getCount() = " + cursor.getCount());
                    final String size_cursor= cursor.getCount()+"";
                    x.task().autoPost(() -> {
                        button.setText(size_cursor);
                    });
                    while (cursor.moveToNext()) {
                        if (!scanning){
                            return;
                        }
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
                        String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
                        String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                        String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));

//                        if (filterCb.isChecked() && duration != null && Long.valueOf(duration) < 1000 * 60){//60 s
//                            L.e( "scan中 60s run: name = "+name+" duration < 1000 * 60" );
//                            continue;
//                        }

                        File file = new File(path);
                        String parentPath = file.getParentFile().getPath();
                        L.i("scan中parentPath="+parentPath);

                        name = replaseUnKnowe(name);
                        singer = replaseUnKnowe(singer);
                        album = replaseUnKnowe(album);
                        final String path_db = replaseUnKnowe(path);
                        L.i("scan中name="+name);
                        L.i("scan中singer="+singer);
                        L.i("scan中album="+album);
                        L.i("scan中path="+path);
                        L.i("scan中duration="+ DateUtil.formatMp4Time( Long.parseLong(duration)));

                    }

                     //扫描完成获取一下当前播放音乐及路径
//                    curMusicId = MyMusicUtil.getIntShared(Constant.KEY_ID);
//                    curMusicPath = dbManager.getMusicPath(curMusicId);
//
//                    // 根据a-z进行排序源数据
//                    Collections.sort(musicInfoList);
//                    dbManager.updateAllMusic(musicInfoList);

                       //扫描完成
                       //更新UI界面

                }else {
                     //更新UI界面
                }
                if (cursor != null) {
                    cursor.close();
                }
            }catch (Exception e){//扫描出错
                e.printStackTrace();
                L.e( "scan中 扫描出错run: error = ");
            }
        });

    }

    public static String replaseUnKnowe(String oldStr){
        try {
            if (oldStr != null){
                if (oldStr.equals("<unknown>")){
                    oldStr = oldStr.replaceAll("<unknown>", "未知");
                }
            }
        }catch (Exception e){ e.printStackTrace();  }
        return oldStr;
    }




    @Override public void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }
}
