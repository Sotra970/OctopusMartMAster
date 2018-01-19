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
        if (sharedView !=null)
        startActivity(intent , sharedView , context);
        else
        startActivity(intent);
    }



    public  void startCategory(View sharedView , Activity context   ){
//        Intent intent= new Intent(context ,  CategoryActivity.class) ;
//        startActivity(intent , sharedView , context);
    }

    public void startActivity(Intent intent, View sharedView, Activity context){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context, sharedView, getString(R.string.sharedView));
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }


    public  static  MenuFragment menuFragment ;


    static  public final  int  login_code = 566;
    static  public final  int  traking_code = 567;
    static  public final  int  history_code = 568;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("BaseActivty" , "onActivityResult");
        if (requestCode == login_code || requestCode == traking_code ||  requestCode == history_code ){
            menuFragment.onActivityResult(requestCode , resultCode  , data);
        }
    }
}
