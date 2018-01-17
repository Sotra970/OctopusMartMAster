package com.example.ahmed.octopusmart.Util;

import android.view.View;

/**
 * Created by ahmed on 18/11/2017.
 */

public class SceenUtils {
    public  int x , y ;
    public  View v;

    public SceenUtils() {
    }

    public SceenUtils(View v) {
        this.v = v;
        if(v != null){
            int[] co = new int[2];
            v.getLocationOnScreen(co);
            x =  co[0];
            y =  co[1];
        }
    }

    public static int getX(View v){
        if(v != null){
            int[] co = new int[2];
            v.getLocationOnScreen(co);
            return co[0];
        }

        return 0;
    }

    public static int getY(View v){
        if(v != null){
            int[] co = new int[2];
            v.getLocationOnScreen(co);
            return co[1];
        }

        return 0;
    }


}
