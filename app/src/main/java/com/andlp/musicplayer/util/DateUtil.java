package com.andlp.musicplayer.util;

/**
 * Created by Administrator on 2017/10/23.
 */

public class DateUtil {


    public static String formatMp4Time(long time)
    {
        time = time/ 1000;
        String strHour = "" + (time/3600);
        String strMinute = "" + time%3600/60;
        String strSecond = "" + time%3600%60;

        strHour = strHour.length() < 2? "0" + strHour: strHour;
        strMinute = strMinute.length() < 2? "0" + strMinute: strMinute;
        strSecond = strSecond.length() < 2? "0" + strSecond: strSecond;

        String strRsult = "";

        if (!strHour.equals("00"))
        {
            strRsult += strHour + ":";
        }

        if (!strMinute.equals("00"))
        {
            strRsult += strMinute + ":";
        }

        strRsult += strSecond;

        return strRsult;
    }



}
