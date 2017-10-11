package com.sds.android.ttpod.media.audiofx;

        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.media.audiofx.AudioEffect;
        import android.media.audiofx.AudioEffect.Descriptor;
        import android.os.Build.VERSION;
        import java.util.UUID;


/**
 * 717219917@qq.com  2017/10/11 14:55
 */
public class EffectDetect {
    public static final UUID EFFECT_TYPE_AEC = UUID.fromString("7b491460-8d4d-11e0-bd61-0002a5d5c51b");
    public static final UUID EFFECT_TYPE_AGC = UUID.fromString("0a8abfe0-654c-11e0-ba26-0002a5d5c51b");
    public static final UUID EFFECT_TYPE_BASS_BOOST = UUID.fromString("0634f220-ddd4-11db-a0fc-0002a5d5c51b");
    public static final UUID EFFECT_TYPE_ENV_REVERB = UUID.fromString("c2e5d5f0-94bd-4763-9cac-4e234d06839e");
    public static final UUID EFFECT_TYPE_EQUALIZER = UUID.fromString("0bed4300-ddd6-11db-8f34-0002a5d5c51b");
    public static final UUID EFFECT_TYPE_NS = UUID.fromString("58b4b260-8e06-11e0-aa8e-0002a5d5c51b");
    public static final UUID EFFECT_TYPE_NULL = UUID.fromString("ec7178ec-e5e1-4432-a3f4-4657e6795210");
    public static final UUID EFFECT_TYPE_PRESET_REVERB = UUID.fromString("47382d60-ddd8-11db-bf3a-0002a5d5c51b");
    public static final UUID EFFECT_TYPE_VIRTUALIZER = UUID.fromString("37cc2c00-dddd-11db-8577-0002a5d5c51b");
    private static final String IMPLEMENTOR_QC = "Qualcomm Technologies, Inc.";
    private static boolean mUsingAudioPlus;

    public static void detectAudioPlus(Context context) {
        boolean z = isAudioPlusSupported() && isIntentSupported(context, "android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL");
        mUsingAudioPlus = z;
    }

    public static boolean usingAudioPlus() {
        return mUsingAudioPlus;
    }

    private static boolean isAudioPlusSupported() {
        if (VERSION.SDK_INT < 9) {
            return false;
        }
        try {
            for (Descriptor descriptor : AudioEffect.queryEffects()) {
                if (descriptor != null && descriptor.implementor.contains(IMPLEMENTOR_QC)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private static boolean isIntentSupported(Context context, String str) {
        return context.getPackageManager().queryIntentActivities(new Intent(str), PackageManager.GET_INTENT_FILTERS).size() > 0;
    }
}