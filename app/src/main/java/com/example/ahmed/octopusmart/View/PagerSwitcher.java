package com.example.ahmed.octopusmart.View;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ahmed on 10/11/2017.
 */

public class PagerSwitcher {

    private Context context;
    private ViewPager viewPager;

    private Timer timer;
    private int page = 0;
    private int max = 0;

    private boolean run = false;

    public PagerSwitcher(Context context, ViewPager viewPager , int max) {
        this.context = context;
        this.viewPager = viewPager;
        this.max = max ;
    }

    public void startSwitching(){
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, 3000); // delay in milliseconds
    }

    public void setPage(int page) {
        this.page = page;
        this.timer.cancel();
        startSwitching();
    }

    private class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
           if(!run){
               run = true;
               Handler uiHandler = new Handler(Looper.getMainLooper());
               uiHandler.post(
                       new Runnable() {
                           @Override
                           public void run() {
                               run = false;
                               if (page >= max) { // In my case the number of pages are 5
                                   page = 0;
                                   viewPager.setCurrentItem(page, true);
                               }
                               else {
                                   viewPager.setCurrentItem(page++, true);
                               }
                           }
                       }
               );
           }

        }
    }


}
