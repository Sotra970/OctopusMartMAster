package com.example.ahmed.octopusmart.Activity.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;

import com.example.ahmed.octopusmart.Activity.CategoryActivity;
import com.example.ahmed.octopusmart.Activity.LoginActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Fragment.FavoriteFragment;
import com.example.ahmed.octopusmart.Fragment.MenuFragment;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ahmed on 10/26/2017.
 */

public class BaseActivity extends CacheActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            hideLoadingFragment();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public  void startCategory(CatModel catModel  , View sharedView , Activity context   ){
        _catModel = catModel ;
        Intent intent= new Intent(context ,  CategoryActivity.class) ;
        startActivity(intent , sharedView , context);

    }



    public  void startCategory(View sharedView , Activity context   ){
//        Intent intent= new Intent(context ,  CategoryActivity.class) ;
//        startActivity(intent , sharedView , context);
    }

    public void startActivity(Intent intent, View sharedView, Activity context){

        ActivityOptionsCompat options ;
        if (sharedView!=null)
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context, sharedView, getString(R.string.sharedView));
        else
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context);

        ActivityCompat.startActivity(context, intent, options.toBundle());
    }


    public void  startLogin(){
        if (!Appcontroler.isUserSigned()){
            Intent intent = new Intent(this , LoginActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);


            ActivityCompat.startActivityForResult(this,intent, fav_login_code, options.toBundle() );
        }
    }




    public  static  MenuFragment menuFragment ;
    public  static FavoriteFragment _favoriteFragment ;


    static  public final  int fav_login_code = 666;
    static  public final  int menu_login_code = 566;
    static  public final  int  traking_code = 567;
    static  public final  int  history_code = 568;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("BaseActivty" , "onActivityResult");
        if (requestCode == menu_login_code || requestCode == traking_code ||  requestCode == history_code ){
            menuFragment.onActivityResult(requestCode , resultCode  , data);
        }

        if (requestCode == fav_login_code){
            _favoriteFragment.onActivityResult(requestCode , resultCode  , data);
        }
    }
}
