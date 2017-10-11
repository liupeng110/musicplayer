package com.sds.android.ttpod.media.audiofx;

public class TTVolume extends TTAudioEffect {
    public TTVolume() {
        super(EffectUUID.EFFECT_ID_VOLUME);
    }

    public void setBoostLimitEnabled(boolean z) {
        int[] iArr = new int[]{5};
        short[] sArr = new short[]{(short) 0, (short) 0};
        if (z) {
            sArr[0] = (short) 1;
        }
        setEnabled(z);
        TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_VOLUME, iArr, sArr);
    }

    public boolean boostLimitEnabled() {
        short[] sArr = new short[]{(short) 0, (short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_VOLUME, new int[]{5}, sArr);
        if (sArr[0] != (short) 0) {
            return true;
        }
        return false;
    }
}