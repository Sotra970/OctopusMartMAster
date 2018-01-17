package com.example.ahmed.octopusmart.Util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sotra on 9/9/2016.
 */
public class Spec {
    Context context ;

    public Spec(Context context) {
        this.context = context;
    }

    public  Point WindowRec(){
        Point Frec = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            try {
                //geting screen size
                 Frec = new Point();
                WindowManager windowManager =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE) ;
                windowManager.getDefaultDisplay().getSize(Frec);
            } catch (NoSuchMethodError e) {
                Log.i("error", "it can't work");
            }

        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE) ;
            windowManager.getDefaultDisplay().getMetrics(metrics);
            Frec.set(metrics.widthPixels,metrics.heightPixels);
        }


        return Frec;
    }


    public  int getToolbarhight() {
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();

        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
          return    TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
        }
        return 0;
    }
    public  int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }


}
