package com.sds.android.ttpod.media.audiofx;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.os.Build;

import com.sds.android.ttpod.media.audiofx.TTEqualizer.Settings;

public class SystemEffectHandle implements IEffectHandle {
    private BassBoost mBassBoost;
    private Equalizer mEqualizer;
    private PresetReverb mReverb;
    private int mSessionID;
    private Virtualizer mVirtualizer;

    public SystemEffectHandle(int i) {
        this.mSessionID = i;
    }

    public void setEqualizerEnabled(boolean z) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mEqualizer != null) {
                    this.mEqualizer.setEnabled(z);
                }
                if (this.mEqualizer != null && !z) {
                    this.mEqualizer.release();
                    this.mEqualizer = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEqualizer(Settings settings) {
        int i = 0;
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mEqualizer == null) {
                    this.mEqualizer = new Equalizer(0, this.mSessionID);
                }
                short[] bandLevels = settings.getBandLevels();
                int length = bandLevels.length;
                short s = (short) 0;
                while (i < length) {
                    short s2 = (short) (s + 1);
                    this.mEqualizer.setBandLevel(s, bandLevels[i]);
                    i++;
                    s = s2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBassBoostEnabled(boolean z) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mBassBoost != null) {
                    this.mBassBoost.setEnabled(z);
                }
                if (this.mBassBoost != null && !z) {
                    this.mBassBoost.release();
                    this.mBassBoost = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBassBoost(int i) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mBassBoost == null) {
                    this.mBassBoost = new BassBoost(0, this.mSessionID);
                }
                this.mBassBoost.setStrength((short) i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBoostLimitEnabled(boolean z) {
    }

    public void setTrebleBoostEnabled(boolean z) {
    }

    public void setTrebleBoost(int i) {
    }

    public void setVirtualizerEnabled(boolean z) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mVirtualizer != null) {
                    this.mVirtualizer.setEnabled(z);
                }
                if (this.mVirtualizer != null && !z) {
                    this.mVirtualizer.release();
                    this.mVirtualizer = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVirtualizer(int i) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mVirtualizer == null) {
                    this.mVirtualizer = new Virtualizer(0, this.mSessionID);
                }
                this.mVirtualizer.setStrength((short) i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReverbEnabled(boolean z) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mReverb != null) {
                    this.mReverb.setEnabled(z);
                }
                if (this.mReverb != null && !z) {
                    this.mReverb.release();
                    this.mReverb = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReverb(int i) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                if (this.mReverb == null) {
                    this.mReverb = new PresetReverb(0, this.mSessionID);
                }
                this.mReverb.setPreset((short) i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        releaseAudioEffect();
    }

    private void releaseAudioEffect() {
        if (this.mEqualizer != null) {
            this.mEqualizer.release();
            this.mEqualizer = null;
        }
        if (this.mBassBoost != null) {
            this.mBassBoost.release();
            this.mBassBoost = null;
        }
        if (this.mReverb != null) {
            this.mReverb.release();
            this.mReverb = null;
        }
        if (this.mVirtualizer != null) {
            this.mVirtualizer.release();
            this.mVirtualizer = null;
        }
    }

    public void reset() {
    }
}