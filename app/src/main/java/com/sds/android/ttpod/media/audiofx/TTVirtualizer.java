package com.sds.android.ttpod.media.audiofx;

public class TTVirtualizer extends TTAudioEffect {
    public TTVirtualizer() {
        super(EffectUUID.EFFECT_ID_VIRTUALIZER);
    }

    public boolean getStrengthSupported() {
        return true;
    }

    public void setStrength(short s) {
        TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_VIRTUALIZER, new int[]{1}, new short[]{s});
    }

    public short getRoundedStrength() {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_VIRTUALIZER, new int[]{1}, sArr);
        return sArr[0];
    }
}