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

public class SortingHomeHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.category_list)
    RecyclerView category_list ;

    @BindView(R.id.categories_img)
    ImageView categories_img ;

    @BindView(R.id.category_title)
    TextView category_title ;

    @BindView(R.id.category_header)
    View category_header ;

    public SortingHomeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this , itemView) ;
    }
}
