package com.example.ahmed.octopusmart.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Naeem on 1/19/2018.
 */

public class ProductVH extends RecyclerView.ViewHolder {

    @BindView(R.id.mobile_img)
    public ImageView image;

    @BindView(R.id.mobile_name)
    public TextView title;

    @BindView(R.id.price_before)
    public TextView old_price;

    @BindView(R.id.price)
    public TextView new_price;

    public ProductVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
