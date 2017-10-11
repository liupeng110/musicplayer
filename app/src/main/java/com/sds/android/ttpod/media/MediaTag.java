package com.sds.android.ttpod.media;

/**
 * 717219917@qq.com  2017/10/11 10:35
 */


public class MediaTag {
    private String mFileName;//文件名
    private int mNativeContext;

    private native String album();

    private native String artist();

    private native String comment();

    private native String genre();

    public static native void initGBKMap(byte[] bArr);//不用理

    private native int open(String str);

    private native int readOnlyOpen(String str);

    private native String title();

    public native int bitRate();

    public native int channels();

    public native void close();

    public native byte[] cover();

    public native int duration();

    public native int sampleRate();

    public native void save();

    public native void setAlbum(String str);

    public native void setArtist(String str);

    public native void setComment(String str);

    public native void setGenre(String str);

    public native void setTitle(String str);

    public native void setTrack(int i);

    public native void setYear(int i);

    public native int track();

    public native int year();

    static {
        try {
            System.loadLibrary("mediatag");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static MediaTag createMediaTag(String str, boolean z) {
        MediaTag mediaTag = new MediaTag();
        if (mediaTag.openFile(str, z)) {
            return mediaTag;
        }
        mediaTag.close();
        return null;
    }

    public boolean openFile(String str, boolean z) {
        if ((z ? readOnlyOpen(str) : open(str)) != 0) {
            return false;
        }
        this.mFileName = str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));//根据路径截取文件名
        return true;
    }

    private String getTitleOrFileName() {
        String title = title();
        if (title==null||title.equals("")) {
             return mFileName;//返回文件名
        }
        return title;
    }

    public String getTitle() {
        return format(getTitleOrFileName());
    }

    public String getArtist() {
        return format(artist());
    }

    public String getAlbum() {
        return format(album());
    }

    public String getGenre() {
        return format(genre());
    }

    public String getComment() {
        return format(comment());
    }

    private String format(String str){
        return str == null ? null : str.replaceAll("([\\u0000-\\u001f\\uD7B0-\\uFEFF\\uFFF0-\\uFFFF]+)", "");
    }


}