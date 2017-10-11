package com.sds.android.ttpod.media.audiofx;

public class TTTrebleBoost extends TTAudioEffect {
    public TTTrebleBoost() {
        super(EffectUUID.EFFECT_ID_TREBLEBOOST);
    }

    public boolean getStrengthSupported() {
        return true;
    }

    public void setStrength(short s) {
        TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_TREBLEBOOST, new int[]{1}, new short[]{s});
    }

    public short getRoundedStrength() {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_TREBLEBOOST, new int[]{1}, sArr);
        return sArr[0];
    }
}