package com.example.ahmed.octopusmart.holders;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by sotra on 12/19/2017.
 */

public class PagerHomeHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.pager)
    public ViewPager pager ;


    public PagerHomeHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this , itemView) ;

    }
}
