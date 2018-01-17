package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Model.UserReviewItem;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Util.LangUtils;
import com.example.ahmed.octopusmart.View.ExpandableTextView;
import com.example.ahmed.octopusmart.holders.UserReviewItemViewHolder;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ahmed on 20/12/2017.
 */

public class UserReviewAdapter extends GenericAdapter<UserReviewItem>{

    private boolean enableExpand;

    public UserReviewAdapter(ArrayList<UserReviewItem> items,
                             Context context,
                             GenericItemClickCallback<UserReviewItem> adapterItemClickCallbacks,
                             boolean enableExpand)
    {
        super(items, context, adapterItemClickCallbacks);
        this.enableExpand = enableExpand;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.user_review_item, parent, false);
        return new UserReviewItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        UserReviewItem userReviewItem = getItem(position);
        if(userReviewItem != null){
            final UserReviewItemViewHolder viewHolder = (UserReviewItemViewHolder) holder;

                ((UserReviewItemViewHolder) holder).summary.collapse() ;
            Log.e("UserReviewAdapter", "name = " + userReviewItem.getUser());
            Log.e("UserReviewAdapter", "name = " + getContext().getString(R.string.user));
            Log.e("UserReviewAdapter", "date = " + userReviewItem.getDate());
            Log.e("UserReviewAdapter", "comment = " + userReviewItem.getComment());


            String date = userReviewItem.getDate();
            String name = userReviewItem.getUser() == null ?getContext().getString(R.string.user): userReviewItem.getUser().getName();
            String summary = userReviewItem.getComment();
            Log.e("UserReviewAdapter", "name = " +name);

            if(date != null && !date.isEmpty()){
                viewHolder.date.setText(date);
            }

            if(name != null && ! name.isEmpty()){
                viewHolder.title.setText(name);
            }

            if(summary !=  null && ! summary.isEmpty()){
                viewHolder.summary.setText(userReviewItem.getComment());
            }

            if(enableExpand && isExpandable(viewHolder.summary)){
                viewHolder.summary.setEnableExpand(true);
                viewHolder.expand.setVisibility(View.VISIBLE);
                viewHolder.expand.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(viewHolder.summary.isExpanded()){
                                    viewHolder.summary.collapse();
                                    viewHolder.expand.setText(R.string.more_text);
                                }
                                else{
                                    viewHolder.summary.expand();
                                    viewHolder.expand.setText(R.string.less);
                                }
                            }
                        }
                );
            }
            else{
                viewHolder.expand.setVisibility(View.GONE);
                viewHolder.summary.setEnableExpand(false);
            }
        }
    }

    private boolean isExpandable( TextView summary) {
        Log.e("UserRievies" , summary.getLineCount() +"");
        return summary.getLineCount() >2 ? true : false ;
     }
}
