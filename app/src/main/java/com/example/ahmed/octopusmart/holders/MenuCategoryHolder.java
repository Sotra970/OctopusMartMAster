package com.example.ahmed.octopusmart.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sotra on 12/19/2017.
 */

public class MenuCategoryHolder extends RecyclerView.ViewHolder{



    @BindView(R.id.menu_category_item_icon)
    public ImageView categories_img ;

    @BindView(R.id.menu_category_item_text)
    public  TextView category_title ;

    public MenuCategoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this , itemView) ;
    }
}
