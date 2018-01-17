package com.example.ahmed.octopusmart.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;

/**
 * Created by ahmed on 23/12/2017.
 */

public class SettingItemViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.settings_item_title)
    public TextView title;

    @BindView(R.id.settings_item_image)
    public ImageView imageView;


    public SettingItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
