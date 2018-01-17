package com.example.ahmed.octopusmart.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 20/12/2017.
 */

public class CartItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.delete_img)
    public View delete;

    @BindView(R.id.arrow_up)
    public View add;

    @BindView(R.id.arrow_down)
    public View subtract;

    @BindView(R.id.item_price)
    public TextView price;

    @BindView(R.id.item_img)
    public ImageView imageView;

    @BindView(R.id.item_name)
    public TextView name;

    @BindView(R.id.count_number)
    public TextView count;

    public CartItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this , itemView);
    }
}
