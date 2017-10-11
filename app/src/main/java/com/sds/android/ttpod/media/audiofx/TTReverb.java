package com.sds.android.ttpod.media.audiofx;

public class TTReverb extends TTAudioEffect {
    public static final short PRESET_LARGEHALL = (short) 5;
    public static final short PRESET_LARGEROOM = (short) 3;
    public static final short PRESET_MEDIUMHALL = (short) 4;
    public static final short PRESET_MEDIUMROOM = (short) 2;
    public static final short PRESET_NONE = (short) 0;
    public static final short PRESET_PLATE = (short) 6;
    public static final short PRESET_SMALLROOM = (short) 1;

    public TTReverb() {
        super(EffectUUID.EFFECT_ID_REVERB);
    }

    public short getPreset() {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_REVERB, new int[]{0}, sArr);
        return sArr[0];
    }

    public int setPreset(short s) {
        return TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_REVERB, new int[]{0}, new short[]{s});
    }

    public void setBoostLimitEnabled(boolean z) {
        short s = (short) 1;
        int[] iArr = new int[]{1};
        short[] sArr = new short[1];
        if (!z) {
            s = (short) 0;
        }
        sArr[0] = s;
        TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_REVERB, iArr, sArr);
    }
}