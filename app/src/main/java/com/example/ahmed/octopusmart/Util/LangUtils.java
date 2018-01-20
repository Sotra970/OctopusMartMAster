package com.example.ahmed.octopusmart.Util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.ahmed.octopusmart.Activity.SplashActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;

import java.util.Locale;

/**
 * Created by Ahmed on 10/30/2017.
 */

public class LangUtils {
    public static Locale locale = null;
    public final static String LANGUAGE_EN = "en";
    public final static String LANGUAGE_AR = "ar";
    public static final String LANG ="LANG" ;

    public static String getLanguage(){
        return PreferenceManager.getDefaultSharedPreferences(Appcontroler.getInstance()).getString(LANG, getDefaultLang());
    }
    public  static boolean isAr(){
        return  getLanguage().equals(LANGUAGE_AR) ;
    }

    public  static boolean isEn(){
        return  getLanguage().equals(LANGUAGE_EN) ;
    }

    public static String getDefaultLang(){
        return Locale.getDefault().getDisplayLanguage();
    }

    public static void changeLang(String lang) {
        Configuration config = Appcontroler.getInstance().getBaseContext().getResources().getConfiguration();

        if (!lang.isEmpty() ) {

            SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(Appcontroler.getInstance()).edit();
            ed.putString(LANG, lang);
            ed.apply();

            locale = new Locale(lang);
            Locale.setDefault(locale);

            config.setLocale(locale);
            config.setLayoutDirection(new Locale(lang));
            Log.e("Appcontrolerr",lang);
            Log.e("Appcontrolerr",config.getLayoutDirection()+"s");
            Appcontroler.getInstance().getBaseContext().getResources().updateConfiguration(config, Appcontroler.getInstance().getBaseContext().getResources().getDisplayMetrics());
            restart();
        }
    }
    public  static  void changeLangchangelangNoRestart(String lang) {
        Configuration config = Appcontroler.getInstance().getBaseContext().getResources().getConfiguration();

        if (!lang.isEmpty() ) {

            SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(Appcontroler.getInstance()).edit();
            ed.putString(LANG, lang);
            ed.commit();

            locale = new Locale(lang);
            Locale.setDefault(locale);

            config.setLocale(locale);
            config.setLayoutDirection(new Locale(lang));
            Log.e("Appcontrolerr",lang);
            Log.e("Appcontrolerr",config.getLayoutDirection()+"s");
            Appcontroler.getInstance().getBaseContext().getResources().updateConfiguration(config,  Appcontroler.getInstance().getBaseContext().getResources().getDisplayMetrics());

        }
    }

    private static void restart() {
//
        Intent intent = new Intent(Appcontroler.getInstance().getBaseContext(), SplashActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(cn);
        Appcontroler.getInstance().getBaseContext().startActivity(mainIntent);
    }







}
