package com.sds.android.ttpod.media.player;

 import android.view.SurfaceView;

/**
 * 717219917@qq.com  2017/10/11 11:43
 */
public interface IMediaPlayer {
    int bufferedBandPercent();

    int bufferedBandWidth();

    int bufferedPercent();

    int duration();

    float getBufferPercent();

    int getBufferSize();

    int getCurFreq(short[] sArr, int i);

    int getCurFreqAndWave(short[] sArr, short[] sArr2, int i);

    int getCurWave(short[] sArr, int i);

    int getFileSize();

    int getPosition();

    void pause();

    int play();

    void release();

    void resume();

    void setActiveNetWorkType(int i);

    void setAudioEffectLowDelay(boolean z);

    void setCacheFilePath(String str);

    void setChannelBalance(float f);

    void setDataSourceAsync(String str, int i) throws Exception;

    void setDecoderType(int i);

    void setPlayRange(int i, int i2);

    int setPosition(int i, int i2);

    void setProxyServerConfig(String str, int i, String str2, boolean z);

    void setView(SurfaceView surfaceView);

    void setViewSize(int i, int i2);

    void setVolume(float f, float f2);

    void stop();

    void videoSizeChanged(int i, int i2);
}