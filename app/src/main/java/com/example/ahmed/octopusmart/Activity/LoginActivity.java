package com.example.ahmed.octopusmart.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.BadgeConfig.Utils;
import com.example.ahmed.octopusmart.Fragment.SignInFragment;
import com.example.ahmed.octopusmart.Fragment.SignUpFragment;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.TitlePagerAdapter;
import com.example.ahmed.octopusmart.Utils.transition.FabTransform;
import com.example.ahmed.octopusmart.View.Widget.WrapContentViewPager;
import com.example.ahmed.octopusmart.View.Widget.WrapContentWithAnimationViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 12/21/2017.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.view_pager_progress)
    WrapContentWithAnimationViewPager viewPager;

    TitlePagerAdapter titlePagerAdapter;



    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

     @BindView(R.id.main_container)
    View main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(Utils.isLollipopOrhigher()){
            FabTransform.setup(this, main_container);
        }

        SignInFragment signInFragment = SignInFragment.getInstance();
        SignUpFragment signUpFragment = SignUpFragment.getInstance();

        titlePagerAdapter = new TitlePagerAdapter(getSupportFragmentManager());
        titlePagerAdapter.addFragment(signInFragment, getString(R.string.signin));
        titlePagerAdapter.addFragment(signUpFragment, getString(R.string.signup) );


        viewPager.setAdapter(titlePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.reMeasureCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }




    public  interface  LoginListener{
        void onPostLogin();
    }



}
