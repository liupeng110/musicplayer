package com.sds.android.ttpod.media.player;

import android.media.AudioTrack;
import android.os.Build.VERSION;
import android.os.Process;
import android.util.Log;
import java.util.Random;

public class TTAudioTrack {
    private static final int BUFFER_SIZE = 40960;
    private static final int DEFAULT_CH = 2;
    private static final int ERR_ACCESS_DENIED = -21;
    private static final int ERR_NONE = 0;
    private static final float HARDWARE_COFF = 990.0f;
    private static final String LOG_TAG = "TTAudioTrack";
    private static final int MIN_HARDWARE_VOLUME = -990;
    private static final int SAMPLERATE_48K = 48000;
    private static final int TWO_K = 2048;
    private static int mAudioSessionId;
    private AudioTrack mAudioTrack;
    private int mChannels;
    private int mMinBufferSize;
    private int mSampleRate;
    private boolean mSetPriority = false;

    public static int audioSessionId() {
        return mAudioSessionId;
    }

    public static int maxOutputSamplerate() {
        if (Math.abs(new Random().nextInt()) == 0) {
            try {
            } catch (Exception e) {
                return AudioTrack.getNativeOutputSampleRate(3);
            }
        }
        new AudioTrack(3, SAMPLERATE_48K, 2, 2, BUFFER_SIZE, 1).release();
        return SAMPLERATE_48K;
    }

    private int createAudioTrack(int i, int i2) {
        int i3 = i2 == 1 ? 4 : 12;
        this.mMinBufferSize = AudioTrack.getMinBufferSize(i, i3, 2);
        if (this.mMinBufferSize == -2 || this.mMinBufferSize == -1) {
            return ERR_ACCESS_DENIED;
        }
        int i4 = this.mMinBufferSize * 2;
        if (i4 < 2048) {
            i4 = 2048;
        }
        try {
            AudioTrack audioTrack = this.mAudioTrack;
            if (VERSION.SDK_INT < 9) {
                this.mAudioTrack = new AudioTrack(3, i, i3, 2, i4, 1);
                Log.i(LOG_TAG, "createAudioTrack-1: " + i + " - " + i2);
            } else if (mAudioSessionId != 0) {
                this.mAudioTrack = new AudioTrack(3, i, i3, 2, i4, 1, mAudioSessionId);
                Log.i(LOG_TAG, "createAudioTrack-2: " + i + " - " + i2);
            } else {
                do {
                    mAudioSessionId = Math.abs(new Random().nextInt());
                    if (mAudioSessionId == 0) {
                        mAudioSessionId = Integer.MAX_VALUE;
                    }
                    this.mAudioTrack = new AudioTrack(3, i, i3, 2, i4, 1, mAudioSessionId);
                    Log.i(LOG_TAG, "createAudioTrack-3: " + i + " - " + i2);
                } while (mAudioSessionId != this.mAudioTrack.getAudioSessionId());
            }
            if (audioTrack != null) {
                audioTrack.release();
            }
            if (this.mAudioTrack.getState() == 0) {
                return ERR_ACCESS_DENIED;
            }
            this.mSetPriority = false;
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return ERR_ACCESS_DENIED;
        }
    }

    private void writeData(byte[] bArr, int i) {
        if (!this.mSetPriority) {
            Process.setThreadPriority(-16);
            this.mSetPriority = true;
        }
        if (this.mAudioTrack != null && i > 0) {
            if (this.mAudioTrack.getPlayState() != 3) {
                this.mAudioTrack.play();
            }
            try {
                this.mAudioTrack.write(bArr, 0, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int audioOpen(int i, int i2) {
        this.mSampleRate = i;
        this.mChannels = i2;
        int createAudioTrack = createAudioTrack(i, i2);
        return createAudioTrack != 0 ? createAudioTrack : 0;
    }

    private void audioClose() {
        if (this.mAudioTrack != null) {
            try {
                audioStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void audioDestroy() {
        if (this.mAudioTrack != null) {
            try {
                this.mAudioTrack.release();
                this.mAudioTrack = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void audioStop() {
        if (this.mAudioTrack != null) {
            try {
                this.mAudioTrack.stop();
                this.mAudioTrack.flush();
                this.mSetPriority = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void audioSetVolume(int i, int i2) {
        float f = 1.0f;
        if (this.mAudioTrack != null) {
            float f2;
            if (i == 0) {
                f2 = 1.0f;
            } else if (i == MIN_HARDWARE_VOLUME) {
                f2 = 0.0f;
            } else {
                f2 = ((float) (i + 990)) / HARDWARE_COFF;
            }
            if (i2 != 0) {
                if (i2 == MIN_HARDWARE_VOLUME) {
                    f = 0.0f;
                } else {
                    f = ((float) (i2 + 990)) / HARDWARE_COFF;
                }
            }
            try {
                this.mAudioTrack.setStereoVolume(f2, f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}