package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ahmed.octopusmart.InfiniteScroll.InfiniteScrollGenericAdapter;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.ViewHolder.ProductVH;

import java.util.ArrayList;

/**
 * Created by Ahmed Naeem on 1/18/2018.
 */

public class SearchResultsAdapter extends InfiniteScrollGenericAdapter<ProductModel> {

    public SearchResultsAdapter(ArrayList<ProductModel> items,
                                Context context,
                                GenericItemClickCallback<ProductModel> adapterItemClickCallbacks)
    {
        super(items, context, adapterItemClickCallbacks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.search_product, parent, false);
        return new ProductVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ProductModel productModel = getItem(position);
        if(productModel != null){
            ProductVH productVH = (ProductVH) holder;

            productVH.title.setText(productModel.getName());

            String image = null;
            try {
                image = productModel.getImages().get(0);
            }
            catch (Exception e){}


            Glide.with(getContext())
                    .load(R.drawable.mobile)
                    .into(productVH.image);
        }
    }
}
