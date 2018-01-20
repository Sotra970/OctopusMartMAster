package com.example.ahmed.octopusmart.Activity;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Fragment.CartFragment;
import com.example.ahmed.octopusmart.Fragment.FavoriteFragment;
import com.example.ahmed.octopusmart.Fragment.HomeFragment;
import com.example.ahmed.octopusmart.Fragment.MenuFragment;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.BadgeConfig.Utils;
import com.example.ahmed.octopusmart.RecyclerAdapter.TitlePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends BaseActivity implements MenuFragment.MenuTabsListener {

    @BindView(R.id.home_tabs_pager)
    ViewPager viewPager ;
    private int mNotificationsCount = 6;
    TitlePagerAdapter titlePagerAdapter ;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        _favoriteFragment = new FavoriteFragment();
        menu_bottom_sheet_setub();
        toolbar_action_setup();


        titlePagerAdapter = new TitlePagerAdapter(getSupportFragmentManager());
        titlePagerAdapter.addFragment(new HomeFragment()," ");
        titlePagerAdapter.addFragment(new CartFragment()," ");
        titlePagerAdapter.addFragment(_favoriteFragment," ");
        viewPager.setAdapter(titlePagerAdapter);

    }


    void toolbar_action_setup() {
         toolbar = findViewById(R.id.main_toolbar_with_notif);
        setSupportActionBar(toolbar);
        ImageButton notification_action = findViewById(R.id.notification);
        LayerDrawable icon = (LayerDrawable) notification_action.getDrawable();
        Utils.setBadgeCount(this, icon, mNotificationsCount);
    }




    /*
    Updates the count of notifications in the ActionBar.
     */
    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;


        toolbar_action_setup();
    }






    BottomSheetBehavior bottomSheetBehavior ;

    void menu_bottom_sheet_setub(){
         menuFragment = MenuFragment.getInstance(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.menu_fragment, menuFragment);
        fragmentTransaction.commit() ;
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.menu_fragment));

    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            witchBottomSheet();
        }else super.onBackPressed();
    }

    public void hideMenu() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            witchBottomSheet();
        }
    }

    void witchBottomSheet(){
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    @Override
    public void onMenuTabSelected(int tab) {
        if (tab == 3 ){
            witchBottomSheet();
        }else {
            hideMenu();
            viewPager.setCurrentItem(tab,true);
        }

        if (tab == 2 ){
            _favoriteFragment.onTabSelected();
        }
    }

    @Override
    public void onMenuTabReSelected(int tab) {
        if (tab == 3 ){
            witchBottomSheet();
        }else {
            hideMenu();
        }


    }
    @Override
    public void onMenuTabUnSelected(int tab) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("HomeActivity" , "onActivityResult");
        if (_favoriteFragment !=null)
        _favoriteFragment.onActivityResult(requestCode , resultCode , data);

    }
}