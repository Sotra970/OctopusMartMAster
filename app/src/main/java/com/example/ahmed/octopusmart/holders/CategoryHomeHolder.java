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

public class CategoryHomeHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.category_list)
    public  RecyclerView category_list ;

    @BindView(R.id.categories_img)
    public ImageView categories_img ;

    @BindView(R.id.category_title)
    public TextView category_title ;

    @BindView(R.id.category_header)
    public View category_header ;

    @BindView(R.id.see_more)
    public View see_more ;

    public CategoryHomeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this , itemView) ;
    }
}
