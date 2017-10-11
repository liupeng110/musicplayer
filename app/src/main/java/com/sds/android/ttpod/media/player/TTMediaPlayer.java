package com.sds.android.ttpod.media.player;

        import android.os.Handler;
        import android.os.Looper;
        import android.os.Message;
        import android.util.Log;
        import android.view.Surface;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;

/**
 * 717219917@qq.com  2017/10/11 11:44
 */
public class TTMediaPlayer implements IMediaPlayer {
    public static final int EDECODER_DEFAULT = 0;
    public static final int EDECODER_SOFT = 1;
    public static final int MAX_SAMPLE_NUM = 1024;
    public static final int MEDIASTATUS_PAUSED = 3;
    public static final int MEDIASTATUS_PLAYING = 2;
    public static final int MEDIASTATUS_PREPARED = 5;
    public static final int MEDIASTATUS_STARTING = 1;
    public static final int MEDIASTATUS_STOPPED = 4;
    public static final int MEDIA_AUDIOFORMAT_CHANGED = 12;
    public static final int MEDIA_BUFFERING_DONE = 17;
    public static final int MEDIA_BUFFERING_START = 16;
    public static final int MEDIA_CACHE_COMPLETED = 23;
    public static final int MEDIA_CLOSE = 5;
    public static final int MEDIA_COMPLETE = 3;
    public static final int MEDIA_CONNECT_DONE = 19;
    public static final int MEDIA_DNS_DONE = 18;
    public static final int MEDIA_EXCEPTION = 6;
    public static final int MEDIA_HTTP_HEADER_RECEIVED = 20;
    public static final int MEDIA_NOP = 0;
    public static final int MEDIA_PAUSE = 4;
    public static final int MEDIA_PLAY = 2;
    public static final int MEDIA_PREFETCH_COMPLETED = 22;
    public static final int MEDIA_PREPARE = 1;
    public static final int MEDIA_SEEK_COMPLETED = 11;
    public static final int MEDIA_START_FIRST_FRAME = 25;
    public static final int MEDIA_START_OPEN = 24;
    public static final int MEDIA_START_RECEIVE_DATA = 21;
    public static final int MEDIA_UPDATE_DURATION = 7;
    public static final int MEDIA_VIDEOFORMAT_CHANGED = 13;
    private static final int MIN_HARDWARE_VOLUME = -990;
    public static final int MIN_SAMPLE_NUM = 256;
    public static final int OPEN_BUFFER = 1;
    public static final int OPEN_CORRECT = 1;
    public static final int OPEN_DEFAULT = 0;
    public static final int OPEN_FAST = 0;
    public static final int OPEN_PRE_LOADED = 32;
    public static final int OPEN_PRE_LOADING = 16;
    private static final float PERCENT = 0.01f;
    private static final String TAG = "TTMediaPlayer";
    private static TTMediaPlayer sTTMediaPlayer;
    private EventHandler mEventHandler;
    private int mHeight;
    private OnMediaPlayerNotifyEventListener mMediaPlayerNotifyEventListener;
    private OnMvPlayerNotifyEventListener mMvPlayerNotifyEventListener;
    private int mNativePlayerPara;
    private boolean mPlayerReleased;
    private Surface mSurface;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private int mViewHeight;
    private int mViewWidth;
    private int mWidth;


    public interface OnMediaPlayerNotifyEventListener {
        void onMediaPlayerNotify(int i, int i2, int i3, Object obj);
    }

    public interface OnMvPlayerNotifyEventListener {
        void onMediaPlayerNotify(int i, int i2, int i3, Object obj);
    }

    private class EventHandler extends Handler {
        public EventHandler(TTMediaPlayer tTMediaPlayer, Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (TTMediaPlayer.this.mMvPlayerNotifyEventListener != null) {
                TTMediaPlayer.this.mMvPlayerNotifyEventListener.onMediaPlayerNotify(message.what, message.arg1, message.arg2, message.obj);
            } else if (TTMediaPlayer.this.mMediaPlayerNotifyEventListener != null) {
                TTMediaPlayer.this.mMediaPlayerNotifyEventListener.onMediaPlayerNotify(message.what, message.arg1, message.arg2, message.obj);
            }
        }
    }

    private native int nativeBufferBandPercent(int i);

    private native int nativeBufferBandWidth(int i);

    private native int nativeBufferedPercent(int i);

    private native int nativeBufferedSize(int i);

    private native void nativeCongfigProxyServer(int i, String str, int i2, String str2, boolean z);

    private native int nativeDuration(int i);

    private native int nativeGetCurFreq(int i, short[] sArr, int i2);

    private native int nativeGetCurFreqAndWave(int i, short[] sArr, short[] sArr2, int i2);

    private native int nativeGetCurWave(int i, short[] sArr, int i2);

    private native int nativeGetPosition(int i);

    private native int nativeGetStatus(int i);

    private native void nativePause(int i);

    private native int nativePlay(int i);

    private native void nativeRelease(int i);

    private native void nativeResume(int i);

    private native void nativeSetActiveNetWorkType(int i, int i2);

    private native void nativeSetAudioEffectLowDelay(int i, boolean z);

    private native void nativeSetCacheFilePath(int i, String str);

    private native int nativeSetDataSourceAsync(int i, String str, int i2);

    private native int nativeSetDataSourceSync(int i, String str, int i2);

    private native void nativeSetDecoderType(int i, int i2);

    private native void nativeSetPlayRange(int i, int i2, int i3);

    private native int nativeSetPosition(int i, int i2, int i3);

    private native int nativeSetSurface(int i);

    private native void nativeSetVolume(int i, int i2, int i3);

    private native void nativeSetup(Object obj, byte[] bArr, int i, String str);

    private native int nativeSize(int i);

    private native int nativeStop(int i);

    //g,h
    // g=        byte[] a(Context context)   (packageinfo)
    // h=  return "/data/data/" + packageName + "/lib";

    private TTMediaPlayer(byte[] bArr, String str) {
        this.mPlayerReleased = false;
        this.mNativePlayerPara = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mEventHandler = new EventHandler(this, Looper.myLooper());
        nativeSetup(this, bArr, TTAudioTrack.maxOutputSamplerate(), str);
    }

    protected TTMediaPlayer() {
        this.mPlayerReleased = false;
        this.mNativePlayerPara = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mViewWidth = 0;
        this.mViewHeight = 0;
    }

    public static TTMediaPlayer instance(byte[] bArr, String str) {
            try {System.loadLibrary("osal");} catch (UnsatisfiedLinkError e) {e.printStackTrace();}
            try {System.loadLibrary("audiofx");} catch (UnsatisfiedLinkError e) {e.printStackTrace();}
            try {System.loadLibrary("resample");} catch (UnsatisfiedLinkError e) {e.printStackTrace();}
            try {System.loadLibrary("mediaplayer");} catch (UnsatisfiedLinkError e) {e.printStackTrace();}

        if (sTTMediaPlayer == null) {
            sTTMediaPlayer = new TTMediaPlayer(bArr, str);
        }
        return sTTMediaPlayer;
    }

    public void setActiveNetWorkType(int i) {
        nativeSetActiveNetWorkType(this.mNativePlayerPara, i);
    }

    public void setDecoderType(int i) {
        nativeSetDecoderType(this.mNativePlayerPara, i);
    }

    public void setCacheFilePath(String str) {
        nativeSetCacheFilePath(this.mNativePlayerPara, str);
    }

    public int play() {
        return nativePlay(this.mNativePlayerPara);
    }

    public void pause() {
        nativePause(this.mNativePlayerPara);
    }

    public void resume() {
        nativeResume(this.mNativePlayerPara);
    }

    public int setPosition(int i, int i2) {
        return nativeSetPosition(this.mNativePlayerPara, i, i2);
    }

    public void setPlayRange(int i, int i2) {
        nativeSetPlayRange(this.mNativePlayerPara, i, i2);
    }

    public int getPosition() {
        return nativeGetPosition(this.mNativePlayerPara);
    }

    public int duration() {
        return nativeDuration(this.mNativePlayerPara);
    }

    public int size() {
        return nativeSize(this.mNativePlayerPara);
    }

    public int bufferedSize() {
        return nativeBufferedSize(this.mNativePlayerPara);
    }

    public int bufferedPercent() {
        return nativeBufferedPercent(this.mNativePlayerPara);
    }

    public int getStatus() {
        return nativeGetStatus(this.mNativePlayerPara);
    }

    public float getBufferPercent() {
        return ((float) nativeBufferedPercent(this.mNativePlayerPara)) * PERCENT;
    }

    public int getBufferSize() {
        return nativeBufferedSize(this.mNativePlayerPara);
    }

    public int bufferedBandWidth() {
        return nativeBufferBandWidth(this.mNativePlayerPara);
    }

    public int bufferedBandPercent() {
        return nativeBufferBandPercent(this.mNativePlayerPara);
    }

    public int getFileSize() {
        return nativeSize(this.mNativePlayerPara);
    }

    public void setView(SurfaceView surfaceView) {
        if (surfaceView == null) {
            this.mSurface = null;
            this.mSurfaceView = null;
            this.mSurfaceHolder = null;
            nativeSetSurface(this.mNativePlayerPara);
            return;
        }
        this.mSurfaceView = surfaceView;
        this.mSurfaceHolder = this.mSurfaceView.getHolder();
        if (this.mSurfaceHolder != null) {
            this.mSurface = this.mSurfaceHolder.getSurface();
        } else {
            this.mSurface = null;
        }
        nativeSetSurface(this.mNativePlayerPara);
    }

    public int getCurFreqAndWave(short[] sArr, short[] sArr2, int i) {
        int i2 = -1;
        synchronized (this) {
            if (!this.mPlayerReleased) {
                i2 = nativeGetCurFreqAndWave(this.mNativePlayerPara, sArr, sArr2, i);
            }
        }
        return i2;
    }

    public int getCurFreq(short[] sArr, int i) {
        int i2 = -1;
        synchronized (this) {
            if (!this.mPlayerReleased) {
                i2 = nativeGetCurFreq(this.mNativePlayerPara, sArr, i);
            }
        }
        return i2;
    }

    public int getCurWave(short[] sArr, int i) {
        int i2 = -1;
        synchronized (this) {
            if (!this.mPlayerReleased) {
                i2 = nativeGetCurWave(this.mNativePlayerPara, sArr, i);
            }
        }
        return i2;
    }

    public void setVolume(float f, float f2) {
        nativeSetVolume(this.mNativePlayerPara, (int) ((1.0f - f) * -990.0f), (int) ((1.0f - f2) * -990.0f));
    }

    public void stop() {
        if (nativeStop(this.mNativePlayerPara) != 0) {
            throw new IllegalStateException("can not be stopped!");
        }
    }

    private int setDataSource(String str, int i, boolean z) {
        if (z) {
            return nativeSetDataSourceSync(this.mNativePlayerPara, str, i);
        }
        return nativeSetDataSourceAsync(this.mNativePlayerPara, str, i);
    }

    public int setDataSource(String str, int i) {
        return setDataSource(str, i, true);
    }

    public void setDataSourceAsync(String str, int i) {
        if (setDataSource(str, i, false) != 0) {
            throw new IllegalStateException("can not setDataSourceAsync !");
        }
    }

    public void release() {
        synchronized (this) {
            nativeRelease(this.mNativePlayerPara);
            this.mPlayerReleased = true;
        }
    }

    public void setChannelBalance(float f) {
        setVolume(1.0f - f, 1.0f + f);
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        TTMediaPlayer tTMediaPlayer = (TTMediaPlayer) obj;
        if (tTMediaPlayer != null && tTMediaPlayer.mEventHandler != null) {
            tTMediaPlayer.mEventHandler.sendMessage(tTMediaPlayer.mEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public void setOnMediaPlayerNotifyEventListener(OnMediaPlayerNotifyEventListener onMediaPlayerNotifyEventListener) {
        this.mMediaPlayerNotifyEventListener = onMediaPlayerNotifyEventListener;
    }

    public void setOnMvPlayerNotifyEventListener(OnMvPlayerNotifyEventListener onMvPlayerNotifyEventListener) {
        this.mMvPlayerNotifyEventListener = onMvPlayerNotifyEventListener;
    }

    public void setAudioEffectLowDelay(boolean z) {
        nativeSetAudioEffectLowDelay(this.mNativePlayerPara, z);
    }

    public void setViewSize(int i, int i2) {
        this.mViewWidth = i;
        this.mViewHeight = i2;
        Log.i(TAG, "setScreenSize: width" + i + "height" + i2);
    }

    public void videoSizeChanged(int i, int i2) {
    }

    public void setProxyServerConfig(String str, int i, String str2, boolean z) {
        nativeCongfigProxyServer(this.mNativePlayerPara, str, i, str2, z);
    }
}