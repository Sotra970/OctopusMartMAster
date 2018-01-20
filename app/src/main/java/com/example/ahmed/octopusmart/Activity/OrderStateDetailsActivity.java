package com.example.ahmed.octopusmart.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Fragment.OrderStateDetailsFragment;
import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderStateDetailsActivity extends BaseActivity {

    @BindView(R.id.activity_title)
    TextView activity_title ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_state_details);
        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
        ButterKnife.bind(this);
        activity_title.setText(getString(R.string.order_num)+_orderModel.getId());
        showFragment(OrderStateDetailsFragment.getInstance(),true,R.anim.fade_in , R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
    }

}
