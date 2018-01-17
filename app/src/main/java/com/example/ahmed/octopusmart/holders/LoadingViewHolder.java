package com.example.ahmed.octopusmart.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sotra on 12/19/2017.
 */

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.loadmore_progress)
    public  View mProgressBar ;
    public LoadingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}