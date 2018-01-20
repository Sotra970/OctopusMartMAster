package com.example.ahmed.octopusmart.Activity.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import com.example.ahmed.octopusmart.Fragment.LoadingFragment;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.View.Dialog.MessageActionDialog;

import butterknife.BindView;


public class LoadingDialogActivity extends FragmentSwitchActivity {


    private  final  boolean DEBUG = false ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @BindView(R.id.no_data_layout)
    View noDataLayout;
    protected void showNoDataLayout(boolean show){
        if(noDataLayout != null){
            noDataLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void hideLoadingFragment(){
      try{
          if(loadingFragment != null) {
              Log.e("loadingFragment" , "hide") ;
              loadingFragment.dismissAllowingStateLoss();
              getSupportFragmentManager()
                      .beginTransaction().remove(loadingFragment).commitAllowingStateLoss();
              loadingFragment = null ;

          }
      }catch (Exception e){
            e.printStackTrace();
      }
    }



    public void showFragment(DialogFragment fragment , String tag){
        try {
            getSupportFragmentManager()
                    .beginTransaction().remove(fragment).commitAllowingStateLoss();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in , R.anim.fade_out)
                    .add(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss();
        }
        catch (Exception e){}
    }


    public void showFragment(DialogFragment fragment , String tag , int id){
        try {
            getSupportFragmentManager()
                    .beginTransaction().remove(fragment).commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in , R.anim.fade_out)
                    .add(id, fragment)
                    .commitAllowingStateLoss();
        }
        catch (Exception e){}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingFragment();
    }

      LoadingFragment loadingFragment ;
    public  void showLoading( LoadingCases loadingCase , LoadingActionClick actionClick){
        switch (loadingCase){
            case show : {
                loadingFragment = LoadingFragment.getInstance(loadingCase , actionClick ) ;
                showFragment(loadingFragment , "loadingFragment");
                Log.e("loadingFragment" , "show") ;
                break;
            }
            case hide:{
                hideLoadingFragment();
                break;
            }

            case fail:{
                if (loadingFragment !=null){
                    try {
                        loadingFragment.showFail(actionClick);
                    }catch (Exception e){

                    }
                }else {
                    loadingFragment = LoadingFragment.getInstance(loadingCase , actionClick ) ;
                    showFragment(loadingFragment , "loadingFragment");
                }
                break;
            }
        }
    }


    public  void showLoading(  LoadingCases loadingCase , LoadingActionClick actionClick , int container_id ){
        switch (loadingCase){
            case show : {
                loadingFragment = LoadingFragment.getInstance(loadingCase , actionClick ) ;
                showFragment(loadingFragment , "loadingFragment" , container_id);
                Log.e("loadingFragment" , "show") ;
                break;
            }
            case hide:{
                hideLoadingFragment();
                break;
            }

            case fail:{
                if (loadingFragment !=null){
                    try {
                        loadingFragment.showFail(actionClick);
                    }catch (Exception e){

                    }
                }else {
                    loadingFragment = LoadingFragment.getInstance(loadingCase , actionClick ) ;
                    showFragment(loadingFragment , "loadingFragment" , container_id);
                }
                break;
            }
        }
    }

    public  interface  ActionClick{
        void onClick();
    }
    public  enum  LoadingCases{
       show , hide , fail
    }
}
