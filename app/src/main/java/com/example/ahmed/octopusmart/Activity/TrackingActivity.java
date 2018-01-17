package com.example.ahmed.octopusmart.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Fragment.OrderStateFragment;
import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 12/7/2017.
 */

public class TrackingActivity extends BaseActivity {
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_toolbar_title)
    TextView titleText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        ButterKnife.bind(this);

        toolbar_action_setup(getString(R.string.order_text));

        OrderStateFragment trackingFragment = new OrderStateFragment();

        addFragment(trackingFragment);



    }




    void toolbar_action_setup(String title) {
        titleText.setText(title);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_tracking_container, fragment);
        fragmentTransaction.commit() ;
    }

}
