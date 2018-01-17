package com.example.ahmed.octopusmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.ProductSpecsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ahmed on 20/12/2017.
 */

public class AllSpecsActivity extends BaseActivity implements GenericItemClickCallback<SubFilterModel> {

    public static final String KEY_SPECS = "KEY_SPECS";

    @BindView(R.id.activity_all_specs_recycler)
    RecyclerView recyclerView;

    private ProductSpecsAdapter productSpecsAdapter;

    @BindView(R.id.activity_title)
    TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_specs);
        ButterKnife.bind(this);

        title.setText(R.string.specs);
    }


    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    private void load() {
        Intent intent = getIntent();
        if(intent != null){
            ArrayList<SubFilterModel> modelArrayList =
                    (ArrayList<SubFilterModel>) intent.getSerializableExtra(KEY_SPECS);

            productSpecsAdapter = new ProductSpecsAdapter(
                    modelArrayList, this, this
            );

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(productSpecsAdapter);
        }
    }

    @OnClick(R.id.activity_back)
    void back(){
        finish();
    }

    @Override
    public void onItemClicked(SubFilterModel item) {

    }
}
