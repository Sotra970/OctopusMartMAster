package com.example.ahmed.octopusmart.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.View.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 19/12/2017.
 */

public class UserReviewItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.user_review_item_date)
    public TextView date;

    @BindView(R.id.user_review_item_title)
    public TextView title;

    @BindView(R.id.user_review_item_summary)
    public ExpandableTextView summary;

    @BindView(R.id.expand_btn)
    public TextView expand;

    public UserReviewItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
