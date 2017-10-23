package com.andlp.musicplayer.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;


public class SingnatureUtil {

    public static byte[] mda_byte(Context context){
        byte[] toByteArray;
        Exception e;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.andlp.musicplayer", PackageManager.GET_SIGNATURES);
            MessageDigest instance = MessageDigest.getInstance("MD5");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (Signature toByteArray2 : packageInfo.signatures) {
                L.i("签名信息："+toByteArray2.toCharsString());
                byteArrayOutputStream.write(instance.digest(toByteArray2.toByteArray()));
            }
            toByteArray = byteArrayOutputStream.toByteArray();
            L.i("签名信息--size："+toByteArray.length);
            for (byte b:toByteArray){
                L.i("签名信息--："+b);
            }

            try {
                byteArrayOutputStream.close();
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return toByteArray;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            toByteArray = null;
            e = exception;
            e.printStackTrace();
            return toByteArray;
        }
        return toByteArray;

    }
}
