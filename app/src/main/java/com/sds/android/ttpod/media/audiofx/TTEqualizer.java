package com.sds.android.ttpod.media.audiofx;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TTEqualizer extends TTAudioEffect {

    public static class Settings {
        private static final String GENRE_CUSTOM = "自定义/custom";
        private static final int MIN_BANDS_NUMBER = 5;
        private short[] mBandLevels = null;
        private String mName = "自定义/custom";
        private short mNumBands = (short) 0;

        public Settings(String str, short s, short[] sArr) {
            this.mName = str;
            this.mNumBands = s;
            if (sArr.length < this.mNumBands) {
                throw new IllegalArgumentException("band levels array length lower ran bands number");
            }
            this.mBandLevels = sArr;
        }

        public Settings(String str) {
            String str2 = null;
            short s = (short) 0;
            if (str == null) {
                throw new IllegalArgumentException("settings: " + str);
            }
            StringTokenizer stringTokenizer = new StringTokenizer(str, "=;");
            if (stringTokenizer.countTokens() < 5) {
                throw new IllegalArgumentException("settings: " + str);
            }
            try {
                str2 = stringTokenizer.nextToken();
                if (str2.equals("Equalizer")) {
                    str2 = stringTokenizer.nextToken();
                    if (str2.equals("mName")) {
                        this.mName = stringTokenizer.nextToken();
                        str2 = stringTokenizer.nextToken();
                        if (str2.equals("mNumBands")) {
                            this.mNumBands = Short.parseShort(stringTokenizer.nextToken());
                            if (stringTokenizer.countTokens() != this.mNumBands * 2) {
                                throw new IllegalArgumentException("settings: " + str);
                            }
                            this.mBandLevels = new short[this.mNumBands];
                            while (s < this.mNumBands) {
                                str2 = stringTokenizer.nextToken();
                                if (str2.equals("band" + (s + 1) + "Level")) {
                                    this.mBandLevels[s] = Short.parseShort(stringTokenizer.nextToken());
                                    s++;
                                } else {
                                    throw new IllegalArgumentException("invalid key name: " + str2);
                                }
                            }
                            return;
                        }
                        throw new IllegalArgumentException("invalid key name: " + str2);
                    }
                    throw new IllegalArgumentException("invalid key name: " + str2);
                }
                throw new IllegalArgumentException("invalid settings for Equalizer: " + str2);
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException("invalid value for key: " + str2);
            }
        }

        public String getName() {
            return this.mName;
        }

        public void setName(String str) {
            this.mName = str;
        }

        public void setNumberOfBands(short s) {
            this.mNumBands = s;
            if (this.mBandLevels == null || this.mBandLevels.length < this.mNumBands) {
                this.mBandLevels = new short[this.mNumBands];
            }
        }

        public short getNumberOfBands() {
            return this.mNumBands;
        }

        public short[] getBandLevels() {
            return this.mBandLevels;
        }

        public static boolean isEmpty(String str) {
            return str == null || str.trim().length() == 0;
        }

        public String toString() {
            String str = "Equalizer;mName=" + (isEmpty(this.mName) ? "自定义/custom" : this.mName) + ";mNumBands=" + Short.toString(this.mNumBands);
            for (short s = (short) 0; s < this.mNumBands; s++) {
                str = str.concat(";band" + (s + 1) + "Level=" + Short.toString(this.mBandLevels[s]));
            }
            return str;
        }

        public boolean isFlat() {
            if (this.mBandLevels != null) {
                for (short s : this.mBandLevels) {
                    if (s != (short) 0) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public TTEqualizer() {
        super(EffectUUID.EFFECT_ID_EQUALIZER);
    }

    public short getNumberOfBands() {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{0}, sArr);
        return sArr[0];
    }

    public void getBandLevelRange(short[] sArr) {
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{1}, sArr);
    }

    public short getNumberOfPresets() {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{7}, sArr);
        return sArr[0];
    }

    public String getPresetName(short s) {
        int i = 0;
        short[] sArr = new short[32];
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{8, s}, sArr);
        char[] cArr = new char[sArr.length];
        while (i < sArr.length) {
            cArr[i] = (char) sArr[i];
            i++;
        }
        return new String(cArr);
    }

    public int setPreset(short s) {
        return TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{6}, new short[]{s});
    }

    public int setBandLevel(short s, short s2) {
        return TTAudioEffect.setEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{2, s}, new short[]{s2});
    }

    public short getBandLevel(short s) {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{2, s}, sArr);
        return sArr[0];
    }

    public int getCenterFreq(short s) {
        short[] sArr = new short[]{(short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{3, s}, sArr);
        return sArr[0];
    }

    public void getBandFreqRange(short s, int[] iArr) {
        short[] sArr = new short[]{(short) 0, (short) 0};
        TTAudioEffect.getEffectParams(EffectUUID.EFFECT_ID_EQUALIZER, new int[]{4, s}, sArr);
        iArr[0] = sArr[0];
        iArr[1] = sArr[1];
    }
}