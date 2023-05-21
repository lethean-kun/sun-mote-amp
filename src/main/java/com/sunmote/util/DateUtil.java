package com.sunmote.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String fmtData(Long d) {
        Date date = new Date(d);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
