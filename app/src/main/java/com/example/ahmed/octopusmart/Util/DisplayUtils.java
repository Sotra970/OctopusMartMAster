package com.example.ahmed.octopusmart.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Ahmed on 10/30/2017.
 */

public class DisplayUtils {

    private final static String PREFERENCE_DISPLAY = "PREFERENCE_DISPLAY";
    private final static String KEY_VIEW_MODE = "KEY_VIEW_MODE";


    public static int dpToPx(float dp, @NonNull Context context){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }

    public static int pxToDp(float px, @NonNull Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}
