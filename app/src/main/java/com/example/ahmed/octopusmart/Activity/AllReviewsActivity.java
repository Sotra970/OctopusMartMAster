package com.example.ahmed.octopusmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Model.UserReviewItem;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.UserReviewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ahmed on 20/12/2017.
 */

public class AllReviewsActivity extends BaseActivity
        implements GenericItemClickCallback<UserReviewItem>
{

    public static final String KEY_REVIEWS = "KEY_REVIEWS";

    @BindView(R.id.activity_all_reviews_recycler)
    RecyclerView recyclerView;

    private UserReviewAdapter userReviewAdapter;

    @BindView(R.id.activity_title)
    TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);
        ButterKnife.bind(this);

        title.setText(R.string.reviews);
    }

    @OnClick(R.id.activity_back)
    void back(){
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    private void load() {
        Intent intent = getIntent();
        if(intent != null){
            ArrayList<UserReviewItem> userReviewItems =
                    intent.getParcelableArrayListExtra(KEY_REVIEWS);

            userReviewAdapter = new UserReviewAdapter(
                    userReviewItems, this, this,
                    true
            );

            com.example.ahmed.octopusmart.Decorator.DividerItemDecoration decoration =
                    new com.example.ahmed.octopusmart.Decorator.DividerItemDecoration(this);
            decoration.setActivated(true);

            recyclerView.addItemDecoration(decoration);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(userReviewAdapter);
        }
    }

    @Override
    public void onItemClicked(UserReviewItem item) {

    }
}
