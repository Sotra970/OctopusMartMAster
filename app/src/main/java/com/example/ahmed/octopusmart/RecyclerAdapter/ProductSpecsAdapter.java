package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.holders.ProductInfoItemViewHolder;

import java.util.ArrayList;

/**
 * Created by ahmed on 20/12/2017.
 */

public class ProductSpecsAdapter extends GenericAdapter<SubFilterModel>
{

    public ProductSpecsAdapter(ArrayList<SubFilterModel> items,
                               Context context,
                               GenericItemClickCallback<SubFilterModel> adapterItemClickCallbacks)
    {
        super(items, context, adapterItemClickCallbacks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.product_info_item, parent, false);
        return new ProductInfoItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SubFilterModel subFilterModel = getItem(position);
        if(subFilterModel != null)
        {
            ProductInfoItemViewHolder viewHolder =
                    (ProductInfoItemViewHolder) holder;

            viewHolder.field.setText(subFilterModel.getName());
            viewHolder.info.setText(subFilterModel.getBody());
        }
    }
}