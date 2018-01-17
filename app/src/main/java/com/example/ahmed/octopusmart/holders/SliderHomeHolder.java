package com.example.ahmed.octopusmart.holders;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by sotra on 12/19/2017.
 */

public class SliderHomeHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.pager)
    public ViewPager pager ;

    @BindView(R.id.indicator)
    public  CircleIndicator indicator ;


    public SliderHomeHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this , itemView) ;
//        itemView.post(new Runnable() {
//            @Override
//            public void run() {
//                ViewGroup.LayoutParams params = itemView.getLayoutParams();
//                Log.e("SliderHomeHolder" , itemView.getMeasuredWidth() +"--" +(params.width*9)/16);
//                params.height = (itemView.getMeasuredWidth()*9)/16;
//                itemView.setLayoutParams(params);
//            }
//        });

    }
}
