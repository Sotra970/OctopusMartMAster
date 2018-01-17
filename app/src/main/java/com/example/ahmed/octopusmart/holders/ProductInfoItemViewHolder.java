package com.example.ahmed.octopusmart.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 19/12/2017.
 */

public class ProductInfoItemViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.product_info_item_field)
    public TextView field;

    @BindView(R.id.product_info_item_info)
    public TextView info;

    @BindView(R.id.field_bg)
    public LinearLayout fieldBG;

    @BindView(R.id.info_bg)
    public LinearLayout infoBG;

    public ProductInfoItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

