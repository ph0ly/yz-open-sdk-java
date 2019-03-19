package com.youzan.open.sdk.util.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ph0ly
 * @time 2017-03-17
 */
public class TimeUtil {

    private TimeUtil() {}

    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
