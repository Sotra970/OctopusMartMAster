package com.example.ahmed.octopusmart.App;

import  android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Util.LangUtils;
import com.example.ahmed.octopusmart.Util.Spec;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Ahmed on 8/17/2017.
 */

public class Appcontroler extends MultiDexApplication {





    private static Appcontroler mInstance;
    private MyPreferenceManager pref;
    private String userId;


    public static boolean isUserSigned() {
        return mInstance.getPrefManager().getUser() != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        String lang = LangUtils.getLanguage();
        LangUtils.changeLangchangelangNoRestart(lang);
//        LangUtils.changeLangchangelangNoRestart("ar");
//        Log.e("App" , "oncreate");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DinNextRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }


    public static synchronized Appcontroler getInstance() {
        return mInstance;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    static Spec Spec ;

    public  static Spec Spec() {
        if (Spec == null) {
            Spec = new Spec(mInstance.getBaseContext());
        }

        return Spec;
    }



    public static boolean hasNetwork ()
    {
        return mInstance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public static String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
    private static ExecutorService executorService;
    public static ExecutorService getExecutorService(){
        if(executorService == null){
            int threadNum = Runtime.getRuntime().availableProcessors();
            executorService = Executors.newFixedThreadPool(threadNum);
        }
        return executorService;
    }


    public static boolean isMine(long id) {
        if (!isUserSigned())return false;
        else
        return mInstance.getUserId()== id;
    }


    static  public long getUserId() {
        if (isUserSigned()){
//            return  Appcontroler.getInstance().getPrefManager().getUser().getId() ;
            return  2 ;
        }else {
            return 2;
        }

    }

    public String getToken() {
        return "";
    }
}
