package com.github.alvarosct02.pokeguidego.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alvaro on 9/1/2016.
 */
public class UtilMethods {

    public static String getDateString(Calendar calendar, String format){
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
