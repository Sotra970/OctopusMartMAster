package com.example.ahmed.octopusmart.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 23/12/2017.
 */

public class SettingItemViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.settings_item_title)
    public TextView title;


    public SettingItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
