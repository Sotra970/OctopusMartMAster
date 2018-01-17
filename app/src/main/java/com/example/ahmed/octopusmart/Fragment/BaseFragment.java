package com.example.ahmed.octopusmart.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.R;

import butterknife.BindView;


/**
 * Created by sotra on 12/9/2017.
 */

public class BaseFragment extends Fragment {



    BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity() ;
    }

    @Nullable
    @BindView(R.id.no_data_layout)
    View noDataLayout;
    protected void showNoDataLayout(boolean show){
        if(noDataLayout != null){
            noDataLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

}
