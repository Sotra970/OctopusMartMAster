package com.example.ahmed.octopusmart.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Fragment.BaseFragment;
import com.example.ahmed.octopusmart.Fragment.OrderStateFragment;
import com.example.ahmed.octopusmart.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ahmed.octopusmart.Fragment.OrderStateFragment.OrderCases.history;

public class OrderCaseActivity extends BaseActivity {

    @BindView(R.id.activity_title)
    TextView activity_title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_case);
        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
        ButterKnife.bind(this);
        int title_id = _orderCase ==history  ? R.string.history_text :R.string.track_order_text ;
        activity_title.setText( getString(title_id) );
        showFragment(OrderStateFragment.getInstance(_orderCase) ,false);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
    }
}
