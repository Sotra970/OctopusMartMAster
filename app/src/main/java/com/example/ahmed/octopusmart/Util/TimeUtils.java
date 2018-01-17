package com.example.ahmed.octopusmart.Util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ahmed on 8/20/2017.
 */

public class TimeUtils {

    public final static String LANGUAGE_AR = "ar";
    public final static String LANGUAGE_EN = "en";

    public final static int LENGTH_SHORT = Calendar.SHORT;
    public final static int LENGTH_LONG = Calendar.LONG;

    public final static String FORMAT_DATE_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_DATE_WITH_TIME_FULL = "yyyy-MM-dd hh:mm aa";
    public final static String FORMAT_DATE_NO_TIME = "yyyy-MM-dd";


    public static long getTimeNow() {
        return System.currentTimeMillis();
    }


    public static String getTimeDateEn(String date) {
        DateFormat oldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // parse the date string into Date object
            Date date1 = oldDate.parse(date);

            DateFormat destDf = new SimpleDateFormat("d MMM K:m a", new Locale("en"));

            // format the date into another format
            String dd = destDf.format(date1);
            return dd;


        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeDateAr(String date) {
        DateFormat oldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // parse the date string into Date object
            Date date1 = oldDate.parse(date);

            DateFormat destDf = new SimpleDateFormat("d MMMM K:mm a", new Locale("ar"));

            // format the date into another format
            String dd = destDf.format(date1);
            return dd;


        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static Date getDate(String date, String format) {
        Log.e("date", date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static double compare2Dates(Date a, Date b) {
        return a.compareTo(b);
    }

    public static double compareNowDate(Date a) {
        return a.compareTo(Calendar.getInstance().getTime());
    }
}




